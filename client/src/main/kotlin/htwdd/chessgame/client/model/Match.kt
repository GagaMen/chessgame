package htwdd.chessgame.client.model

import htwdd.chessgame.client.util.CheckUtility
import htwdd.chessgame.client.util.FENUtility
import htwdd.chessgame.client.util.Observable
import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable
import kotlin.browser.window

@Serializable
data class Match(var id: Int = 0,
                 var players: HashMap<PieceColor, Player?> = HashMap(),
                 var pieceSets: HashMap<PieceColor, PieceSet> = HashMap(),
                 var currentColor: PieceColor = PieceColor.WHITE,
                 @Optional var history: MutableList<Draw> = mutableListOf(),
                 var kingsideCastling: HashMap<PieceColor, Boolean> = hashMapOf(PieceColor.WHITE to true, PieceColor.BLACK to true),
                 var queensideCastling: HashMap<PieceColor, Boolean> = hashMapOf(PieceColor.WHITE to true, PieceColor.BLACK to true),
                 var enPassantField: Field? = null,
                 var halfMoves: Int = 0,
                 var check: HashMap<PieceColor, Boolean> = hashMapOf(PieceColor.WHITE to false, PieceColor.BLACK to false),
                 var checkmate: Boolean = false,
                 var matchCode: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1") : Observable() {

    init {
        initObservable()
        pieceSets[PieceColor.WHITE] = PieceSet()
        pieceSets[PieceColor.BLACK] = PieceSet()
    }

    fun addDraw(draw: Draw, updateGameBoard: Boolean = false) {
        history.add(draw)
        updatePieceSet(draw)
        switchColor()
        FENUtility.calc(this)
        updateCheck()
        setChanged()
        if (updateGameBoard) notifyObservers("updateGameBoardAndProperties")
        else notifyObservers("updateGameProperties")
    }

    private fun switchColor() {
        currentColor = currentColor.getOpposite()
    }

    private fun updatePieceSet(draw: Draw) {
        val startPosition = draw.startField.asPair()
        val endPosition = draw.endField.asPair()
        var enPassantPosition: Pair<Int, Int>? = null
        val pieceSet = pieceSets[draw.color] ?: return
        val piece = pieceSet.activePieces[startPosition.toString()] ?: return

        piece.position = draw.endField
        pieceSet.activePieces.remove(startPosition.toString())
        pieceSet.activePieces[endPosition.toString()] = piece

        val opposingPieceSet = pieceSets[draw.color.getOpposite()] ?: return

        if (enPassantField != null &&
                enPassantField?.row == endPosition.first &&
                enPassantField?.column == endPosition.second) {
            enPassantPosition = when (currentColor) {
                PieceColor.WHITE -> Pair(5, endPosition.second)
                PieceColor.BLACK -> Pair(4, endPosition.second)
            }
        }

        if (opposingPieceSet.activePieces.containsKey(endPosition.toString())) {
            val capturedPiece = opposingPieceSet.activePieces[endPosition.toString()]
            if (capturedPiece != null) pieceSet.capturedPieces.add(capturedPiece)
            opposingPieceSet.activePieces.remove(endPosition.toString())
        }

        if (opposingPieceSet.activePieces.containsKey(enPassantPosition.toString())) {
            val capturedPiece = opposingPieceSet.activePieces[enPassantPosition.toString()]
            if (capturedPiece != null) pieceSet.capturedPieces.add(capturedPiece)
            opposingPieceSet.activePieces.remove(enPassantPosition.toString())
            enPassantField = null
        }
    }

    private fun updateCheck() {
        if (CheckUtility.calcThreatedFields(this)) {
            if (CheckUtility.checkmate(this)) {
                checkmate = true
                println("$currentColor checkmate!")
                window.alert("$currentColor checkmate!")
            } else {
                check[currentColor] = true
            }
        }

        check[currentColor.getOpposite()] = false
    }
}