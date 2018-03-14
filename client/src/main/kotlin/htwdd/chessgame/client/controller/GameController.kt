package htwdd.chessgame.client.controller

import htwdd.chessgame.client.model.*
import htwdd.chessgame.client.util.RequestUtility.Companion.patch
import htwdd.chessgame.client.view.GameView
import kotlinx.html.BUTTON
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.get
import kotlin.browser.document

class GameController(private val client: Client) : Controller {

    private var gameView = GameView(this)

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
            "updateMatchAction" -> updateMatchAction(arg)
        }
    }

    private fun showStartAction() {
        client.changeState(ViewState.START)
    }

    private fun showMatchAction() {
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
                match?.addObserver(gameView)
                client.changeState(ViewState.GAME, match)
            }
        }
    }

    private fun addDrawAction(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                val match = arg.first as? Match ?: return
                val draw = arg.second as? Draw ?: return

                match.addDraw(draw)
                updateMatchAction(match)
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

                when (pieceColor) {
                    PieceColor.WHITE -> {
                        match.whiteCastlingKingSide = false
                        match.whiteCastlingQueenSide = false
                    }
                    PieceColor.BLACK -> {
                        match.blackCastlingKingSide = false
                        match.blackCastlingQueenSide = false
                    }
                }
            }
        }
    }

    private fun disableKingSideCastlingAction(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                val match = arg.first as? Match ?: return
                val pieceColor = arg.second as? PieceColor ?: return

                when (pieceColor) {
                    PieceColor.WHITE -> {
                        match.whiteCastlingKingSide = false
                    }
                    PieceColor.BLACK -> {
                        match.blackCastlingKingSide = false
                    }
                }
            }
        }
    }

    private fun disableQueenSideCastlingAction(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                val match = arg.first as? Match ?: return
                val pieceColor = arg.second as? PieceColor ?: return

                when (pieceColor) {
                    PieceColor.WHITE -> {
                        match.whiteCastlingQueenSide = false
                    }
                    PieceColor.BLACK -> {
                        match.blackCastlingQueenSide = false
                    }
                }
            }
        }
    }

    private fun convertPieceAction(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                val match = arg.first as? Match ?: return
                val pieceType = arg.second as? PieceType ?: return
                val popup = document.getElementsByClassName("board--popup")[0] as? HTMLDivElement ?: return
                val row = popup.attributes["data-row"]?.nodeValue?.toIntOrNull() ?: return
                val col = popup.attributes["data-col"]?.nodeValue?.toIntOrNull() ?: return

                val pieceColor = match.currentColor.getOpposite()

                val pieceSet = match.pieceSets[pieceColor]?.activePieces ?: return

                if (!pieceSet.containsKey(Pair(row, col))) {
                    // don't contains key
                    return
                }

                pieceSet[Pair(row, col)]?.type = pieceType

                val draw = match.history[match.history.lastIndex]
                draw.drawCode = "${draw.drawCode}${pieceType.getDrawCode()}"

                match.history.removeAt(match.history.lastIndex)
                match.history.add(draw)
            }
        }
    }

    private fun updateMatchAction(arg: Any?) {
        when (arg) {
            is Match -> {
                patch("http://localhost:8080/match/${arg.id}",
                        Pair("checkWhite", arg.check[PieceColor.WHITE]!!),
                        Pair("checkBlack", arg.check[PieceColor.BLACK]!!),
                        Pair("checkmate", arg.checkmate),
                        Pair("matchCode", arg.matchCode))
            }
        }
    }
}