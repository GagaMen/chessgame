package htwdd.chessgame.client.model

enum class PieceType {
    PAWN {
        override fun getMatchCode(pieceColor: PieceColor): String {
            return when (pieceColor) {
                PieceColor.WHITE -> "P"
                PieceColor.BLACK -> "p"
            }
        }

        override fun getDrawCode(): String = ""
    },
    KING {
        override fun getMatchCode(pieceColor: PieceColor): String {
            return when (pieceColor) {
                PieceColor.WHITE -> "K"
                PieceColor.BLACK -> "k"
            }
        }

        override fun getDrawCode(): String = "K"
    },
    QUEEN {
        override fun getMatchCode(pieceColor: PieceColor): String {
            return when (pieceColor) {
                PieceColor.WHITE -> "Q"
                PieceColor.BLACK -> "q"
            }
        }

        override fun getDrawCode(): String = "Q"
    },
    BISHOP {
        override fun getMatchCode(pieceColor: PieceColor): String {
            return when (pieceColor) {
                PieceColor.WHITE -> "B"
                PieceColor.BLACK -> "b"
            }
        }

        override fun getDrawCode(): String = "B"
    },
    KNIGHT {
        override fun getMatchCode(pieceColor: PieceColor): String {
            return when (pieceColor) {
                PieceColor.WHITE -> "N"
                PieceColor.BLACK -> "n"
            }
        }

        override fun getDrawCode(): String = "N"
    },
    ROOK {
        override fun getMatchCode(pieceColor: PieceColor): String {
            return when (pieceColor) {
                PieceColor.WHITE -> "R"
                PieceColor.BLACK -> "r"
            }
        }

        override fun getDrawCode(): String = "R"
    };

    abstract fun getMatchCode(pieceColor: PieceColor): String
    abstract fun getDrawCode(): String
}