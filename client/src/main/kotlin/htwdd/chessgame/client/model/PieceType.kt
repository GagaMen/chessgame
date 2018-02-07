package htwdd.chessgame.client.model

enum class PieceType {
    PAWN {
        override fun getCode(pieceColor: PieceColor): String {
            return when (pieceColor) {
                PieceColor.WHITE -> "P"
                PieceColor.BLACK -> "p"
            }
        }
    },
    KING {
        override fun getCode(pieceColor: PieceColor): String {
            return when (pieceColor) {
                PieceColor.WHITE -> "K"
                PieceColor.BLACK -> "k"
            }
        }
    },
    QUEEN {
        override fun getCode(pieceColor: PieceColor): String {
            return when (pieceColor) {
                PieceColor.WHITE -> "Q"
                PieceColor.BLACK -> "q"
            }
        }
    },
    BISHOP {
        override fun getCode(pieceColor: PieceColor): String {
            return when (pieceColor) {
                PieceColor.WHITE -> "B"
                PieceColor.BLACK -> "b"
            }
        }
    },
    KNIGHT {
        override fun getCode(pieceColor: PieceColor): String {
            return when (pieceColor) {
                PieceColor.WHITE -> "N"
                PieceColor.BLACK -> "n"
            }
        }
    },
    ROOK {
        override fun getCode(pieceColor: PieceColor): String {
            return when (pieceColor) {
                PieceColor.WHITE -> "R"
                PieceColor.BLACK -> "r"
            }
        }
    };

    abstract fun getCode(pieceColor: PieceColor): String
}