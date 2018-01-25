package htwdd.chessgame.client.model

class PieceSet(var activePieces: HashSet<Piece> = HashSet(),
               var capturedPieces: HashSet<Piece> = HashSet(),
               val pieceColor: PieceColor,
               val initialize: Boolean = false) {

    init {
        if (initialize) {
            setStartPieces()
        }
    }

    fun setStartPieces() {
        when (pieceColor) {
            PieceColor.WHITE -> {
                activePieces.add(Piece(PieceType.ROOK, Field(1, 1)))
                activePieces.add(Piece(PieceType.KNIGHT, Field(1, 2)))
                activePieces.add(Piece(PieceType.BISCHOP, Field(1, 3)))
                activePieces.add(Piece(PieceType.QUEEN, Field(1, 4)))
                activePieces.add(Piece(PieceType.KING, Field(1, 5)))
                activePieces.add(Piece(PieceType.BISCHOP, Field(1, 6)))
                activePieces.add(Piece(PieceType.KNIGHT, Field(1, 7)))
                activePieces.add(Piece(PieceType.ROOK, Field(1, 8)))

                for (i in 1..8) {
                    activePieces.add(Piece(PieceType.PAWN, Field(2, i)))
                }
            }
            PieceColor.BLACK -> {
                activePieces.add(Piece(PieceType.ROOK, Field(8, 1)))
                activePieces.add(Piece(PieceType.KNIGHT, Field(8, 2)))
                activePieces.add(Piece(PieceType.BISCHOP, Field(8, 3)))
                activePieces.add(Piece(PieceType.QUEEN, Field(8, 4)))
                activePieces.add(Piece(PieceType.KING, Field(8, 5)))
                activePieces.add(Piece(PieceType.BISCHOP, Field(8, 6)))
                activePieces.add(Piece(PieceType.KNIGHT, Field(8, 7)))
                activePieces.add(Piece(PieceType.ROOK, Field(8, 8)))

                for (i in 1..8) {
                    activePieces.add(Piece(PieceType.PAWN, Field(7, i)))
                }
            }
        }
    }
}