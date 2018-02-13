package htwdd.chessgame.client.model

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
        when (pieceColor) {
            PieceColor.WHITE -> {
                activePieces[Pair(1, 1)] = Piece(PieceType.ROOK, Field(1, 1))
                activePieces[Pair(1, 2)] = Piece(PieceType.KNIGHT, Field(1, 2))
                activePieces[Pair(1, 3)] = Piece(PieceType.BISHOP, Field(1, 3))
                activePieces[Pair(1, 4)] = Piece(PieceType.QUEEN, Field(1, 4))
                activePieces[Pair(1, 5)] = Piece(PieceType.KING, Field(1, 5))
                activePieces[Pair(1, 6)] = Piece(PieceType.BISHOP, Field(1, 6))
                activePieces[Pair(1, 7)] = Piece(PieceType.KNIGHT, Field(1, 7))
                activePieces[Pair(1, 8)] = Piece(PieceType.ROOK, Field(1, 8))

                for (i in 1..8) {
                    activePieces[Pair(2, i)] = Piece(PieceType.PAWN, Field(2, i))
                }
            }
            PieceColor.BLACK -> {
                activePieces[Pair(8, 1)] = Piece(PieceType.ROOK, Field(8, 1))
                activePieces[Pair(8, 2)] = Piece(PieceType.KNIGHT, Field(8, 2))
                activePieces[Pair(8, 3)] = Piece(PieceType.BISHOP, Field(8, 3))
                activePieces[Pair(8, 4)] = Piece(PieceType.QUEEN, Field(8, 4))
                activePieces[Pair(8, 5)] = Piece(PieceType.KING, Field(8, 5))
                activePieces[Pair(8, 6)] = Piece(PieceType.BISHOP, Field(8, 6))
                activePieces[Pair(8, 7)] = Piece(PieceType.KNIGHT, Field(8, 7))
                activePieces[Pair(8, 8)] = Piece(PieceType.ROOK, Field(8, 8))

                for (i in 1..8) {
                    activePieces[Pair(7, i)] = Piece(PieceType.PAWN, Field(7, i))
                }
            }
        }
    }
}