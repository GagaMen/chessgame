package htwdd.chessgame.client.controller

import htwdd.chessgame.client.model.*
import htwdd.chessgame.client.view.GameView
import kotlinx.html.BUTTON
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
            "castlingAction" -> castlingAction(arg)
            "disableKingSideCastlingAction" -> disableKingSideCastlingAction(arg)
            "disableQueenSideCastlingAction" -> disableQueenSideCastlingAction(arg)
            "convertPieceAction" -> convertPieceAction(arg)
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
                val matchId = arg.attributes["data-id"]?.toInt()
                if (matchId != null && client.matches.containsKey(matchId)) {
                    val match = client.matches[matchId]
                    match?.addObserver(gameView)
                    client.changeState(ViewState.GAME, match)
                } else {
                    //todo error
                }
            }
        }
    }

    private fun addDrawAction(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                val match = arg.first
                val draw = arg.second

                if (match is Match && draw is Draw) {
                    match.addDraw(draw)
                }
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
                val match = arg.first
                val enPassantField = arg.second

                if (match is Match && enPassantField is Field) {
                    match.enPassantField = enPassantField
                }
            }
        }
    }

    private fun resetEnPassantFieldAction(arg: Any?) {
        when (arg) {
            is Match -> {
                arg.enPassantField = null
            }
        }
    }

    private fun castlingAction(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                val match = arg.first
                val pieceColor = arg.second

                if (match != null && match is Match) {
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
    }

    private fun disableKingSideCastlingAction(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                val match = arg.first
                val pieceColor = arg.second

                if (match != null && match is Match) {
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
    }

    private fun disableQueenSideCastlingAction(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                val match = arg.first
                val pieceColor = arg.second

                if (match != null && match is Match) {
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
    }

    private fun convertPieceAction(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                val match = arg.first
                val pieceType = arg.second
                val popup = document.getElementsByClassName("board--popup")[0]

                if (match != null &&
                        pieceType != null &&
                        popup != null &&
                        match is Match &&
                        pieceType is PieceType) {
                    val row = popup.attributes["data-row"]?.nodeValue?.toIntOrNull()
                    val col = popup.attributes["data-col"]?.nodeValue?.toIntOrNull()

                    if (row != null && col != null) {
                        val pieceColor = when (match.currentColor) {
                            PieceColor.WHITE -> PieceColor.BLACK
                            PieceColor.BLACK -> PieceColor.WHITE
                        }
                        val pieceSet = match.pieceSets[pieceColor]?.activePieces
                        if (pieceSet != null && pieceSet.containsKey(Pair(row, col))) {
                            pieceSet[Pair(row, col)]?.type = pieceType
                        }
                    }
                }
            }
        }
    }
}