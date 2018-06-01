package htwdd.chessgame.client.controller

import htwdd.chessgame.client.model.*
import htwdd.chessgame.client.model.PieceType.PAWN
import htwdd.chessgame.client.util.PollingUtility
import htwdd.chessgame.client.util.RequestUtility.Companion.get
import htwdd.chessgame.client.util.RequestUtility.Companion.post
import htwdd.chessgame.client.util.SANUtility
import htwdd.chessgame.client.view.GameView
import kotlinx.coroutines.experimental.await
import kotlinx.coroutines.experimental.launch
import kotlinx.html.BUTTON
import kotlinx.serialization.json.JSON
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.get
import org.w3c.xhr.XMLHttpRequest
import kotlin.browser.document

class GameController(client: Client) : Controller(client) {
    private var gameView = GameView(this)
    private val pollingUtility = PollingUtility()

    init {
        client.addObserver(gameView)
    }

    override fun actionPerformed(e: Any, arg: Any?) {
        when (e) {
            "showStartAction" -> showStartAction()
            "showMatchAction" -> showMatchAction()
            "startMatchAction" -> startMatchAction(arg)
            "addDrawAction" -> addDrawAction(arg)
            "increaseHalfMovesAction" -> increaseHalfMovesAction(arg)
            "resetHalfMovesAction" -> resetHalfMovesAction(arg)
            "setEnPassantFieldAction" -> setEnPassantFieldAction(arg)
            "resetEnPassantFieldAction" -> resetEnPassantFieldAction(arg)
            "disableCastlingAction" -> disableCastlingAction(arg)
            "disableKingSideCastlingAction" -> disableKingSideCastlingAction(arg)
            "disableQueenSideCastlingAction" -> disableQueenSideCastlingAction(arg)
            "convertPieceAction" -> convertPieceAction(arg)
            "stopPolling" -> stopPolling()
        }
    }

    private fun showStartAction() {
        pollingUtility.stop()
        client.changeState(ViewState.START)
    }

    private fun showMatchAction() {
        pollingUtility.stop()
        client.changeState(ViewState.MATCH)
    }

    private fun startMatchAction(arg: Any?) {
        when (arg) {
            is BUTTON -> {
                val matchId = arg.attributes["data-id"]?.toIntOrNull() ?: return

                if (!client.matches.containsKey(matchId)) {
                    return
                }

                val match = client.matches[matchId]

                launch {
                    get("${client.config.serverRootUrl}/matches/$matchId/pieceSets") {
                        if (it.target is XMLHttpRequest) {
                            val pieceSetHashMap = JSON.parse<PieceSetHashMap>((it.target as XMLHttpRequest).responseText)
                            match?.pieceSets = pieceSetHashMap.pieceSets
                        }
                    }.await()

                    // necessary if the ai player is white and it is a fresh match
                    if (
                            match!!.matchCode == "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1" &&
                            match.players[match.currentColor]?.id == 1
                    ) {
                        post("${client.config.serverRootUrl}/draws/ai", Pair("matchId", match.id)) {
                            if (it.target is XMLHttpRequest) {
                                val draw = JSON.parse<Draw>((it.target as XMLHttpRequest).responseText)
                                increaseHalfMovesAction(match)
                                addDrawAction(Pair(match, draw))
                            }
                        }.await()
                    }

                    get("${client.config.serverRootUrl}/matches/$matchId/draws") {
                        if (it.target is XMLHttpRequest) {
                            val drawList = JSON.parse<DrawList>((it.target as XMLHttpRequest).responseText)
                            match.history = drawList.draws
                        }
                    }.await()

                    match.addObserver(gameView)
                    client.changeState(ViewState.GAME, match)

                    if (!match.checkmate) {
                        pollingUtility.start(client.config.pollingDelayTime) {
                            get("${client.config.serverRootUrl}/matches/$matchId/draws") {
                                if (it.target is XMLHttpRequest) {
                                    val drawList = JSON.parse<DrawList>((it.target as XMLHttpRequest).responseText)
                                    if (match.history.size != drawList.draws.size) {
                                        match.history.forEach { draw ->
                                            drawList.draws.removeAll { it.id == draw.id }
                                        }
                                        drawList.draws.forEach { draw ->
                                            match.addDraw(draw, true)
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    private fun addDrawAction(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                val match = arg.first as? Match ?: return
                val draw = arg.second as? Draw ?: return

                match.addDraw(draw)
            }
        }
    }

    private fun increaseHalfMovesAction(arg: Any?) {
        when (arg) {
            is Match -> arg.halfMoves++
        }
    }

    private fun resetHalfMovesAction(arg: Any?) {
        when (arg) {
            is Match -> arg.halfMoves = 0
        }
    }

    private fun setEnPassantFieldAction(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                val match = arg.first as? Match ?: return
                val enPassantField = arg.second as? Field ?: return

                match.enPassantField = enPassantField
            }
        }
    }

    private fun resetEnPassantFieldAction(arg: Any?) {
        when (arg) {
            is Match -> arg.enPassantField = null
        }
    }

    private fun disableCastlingAction(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                val match = arg.first as? Match ?: return
                val pieceColor = arg.second as? PieceColor ?: return

                match.kingsideCastling[pieceColor] = false
                match.queensideCastling[pieceColor] = false
            }
        }
    }

    private fun disableKingSideCastlingAction(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                val match = arg.first as? Match ?: return
                val pieceColor = arg.second as? PieceColor ?: return

                match.kingsideCastling[pieceColor] = false
            }
        }
    }

    private fun disableQueenSideCastlingAction(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                val match = arg.first as? Match ?: return
                val pieceColor = arg.second as? PieceColor ?: return

                match.queensideCastling[pieceColor] = false
            }
        }
    }

    private fun convertPieceAction(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                val match = arg.first as? Match ?: return
                val pieceType = arg.second as? PieceType ?: return
                val popup = document.getElementsByClassName("board--popup")[0] as? HTMLDivElement ?: return
                val newRow = popup.attributes["data-new-row"]?.nodeValue?.toIntOrNull() ?: return
                val newCol = popup.attributes["data-new-col"]?.nodeValue?.toIntOrNull() ?: return
                val oldRow = popup.attributes["data-old-row"]?.nodeValue?.toIntOrNull() ?: return
                val oldCol = popup.attributes["data-old-col"]?.nodeValue?.toIntOrNull() ?: return
                val throwPiece = popup.attributes["data-throw-piece"]?.nodeValue?.toBoolean() ?: return

                val drawCode = SANUtility.calc(PAWN,
                        Field(oldRow, oldCol),
                        Field(newRow, newCol),
                        match,
                        throwPiece,
                        conversion = pieceType) ?: return

                post("${client.config.serverRootUrl}/draws",
                        Pair("matchId", match.id),
                        Pair("startRow", oldRow),
                        Pair("startColumn", oldCol),
                        Pair("drawCode", drawCode)) {
                    if (it.target is XMLHttpRequest) {
                        val draw = JSON.parse<Draw>((it.target as XMLHttpRequest).responseText)
                        increaseHalfMovesAction(match)
                        addDrawAction(Pair(match, draw))
                    }
                }
            }
        }
    }

    private fun stopPolling() {
        pollingUtility.stop()
    }
}