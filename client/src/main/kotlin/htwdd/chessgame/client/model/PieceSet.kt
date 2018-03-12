package htwdd.chessgame.client.model

import kotlinx.serialization.Serializable

@Serializable
class PieceSet(var activePieces: HashMap<Pair<Int, Int>, Piece> = HashMap(),
               var capturedPieces: HashSet<Piece> = HashSet(),
               val pieceColor: PieceColor,
               val initialize: Boolean = false) {

    init {
        if (initialize) {
            setStartPieces()
        }
    }

    private fun setStartPieces() {
        var row1: Int? = null
        var row2: Int? = null

        when (pieceColor) {
            PieceColor.WHITE -> {
                row1 = 1
                row2 = 2
            }
            PieceColor.BLACK -> {
                row1 = 8
                row2 = 7
            }
        }

        activePieces[Pair(row1, 1)] = Piece(PieceType.ROOK, Field(row1, 1))
        activePieces[Pair(row1, 2)] = Piece(PieceType.KNIGHT, Field(row1, 2))
        activePieces[Pair(row1, 3)] = Piece(PieceType.BISHOP, Field(row1, 3))
        activePieces[Pair(row1, 4)] = Piece(PieceType.QUEEN, Field(row1, 4))
        activePieces[Pair(row1, 5)] = Piece(PieceType.KING, Field(row1, 5))
        activePieces[Pair(row1, 6)] = Piece(PieceType.BISHOP, Field(row1, 6))
        activePieces[Pair(row1, 7)] = Piece(PieceType.KNIGHT, Field(row1, 7))
        activePieces[Pair(row1, 8)] = Piece(PieceType.ROOK, Field(row1, 8))

        for (i in 1..8) {
            activePieces[Pair(row2, i)] = Piece(PieceType.PAWN, Field(row2, i))
        }
    }
}