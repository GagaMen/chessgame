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
            "showStart" -> client.changeState(ViewState.START)
            "showMatch" -> client.changeState(ViewState.MATCH)
            "startMatch" -> startMatch(arg)
            "addDraw" -> addDraw(arg)
            "increaseHalfMoves" -> increaseHalfMoves(arg)
            "resetHalfMoves" -> resetHalfMoves(arg)
            "setEnPassant" -> setEnPassant(arg)
            "resetEnPassant" -> resetEnPassant(arg)
            "castling" -> castling(arg)
            "disableKingSide" -> disableKingSide(arg)
            "disableQueenSide" -> disableQueenSide(arg)
            "convertPiece" -> convertPiece(arg)
        }
    }

    private fun startMatch(arg: Any?) {
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

    private fun addDraw(arg: Any?) {
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

    private fun increaseHalfMoves(arg: Any?) {
        when (arg) {
            is Match -> arg.halfMoves++
        }
    }

    private fun resetHalfMoves(arg: Any?) {
        when (arg) {
            is Match -> arg.halfMoves = 0
        }
    }

    private fun setEnPassant(arg: Any?) {
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

    private fun resetEnPassant(arg: Any?) {
        when (arg) {
            is Match -> {
                arg.enPassantField = null
            }
        }
    }

    private fun castling(arg: Any?) {
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

    private fun disableKingSide(arg: Any?) {
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

    private fun disableQueenSide(arg: Any?) {
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

    private fun convertPiece(arg: Any?) {
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