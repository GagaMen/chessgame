package htwdd.chessgame.server.model

/**
 * @author Felix Dimmel
 *
 * @property PAWN Type Pawn
 * @property KING Type King
 * @property QUEEN Type Queen
 * @property BISHOP Type Bishop
 * @property KNIGHT Type Knight
 * @property ROOK Type Rook
 *
 * @since 1.0.0
 */
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

    /**
     * @author Felix Dimmel
     *
     * @param pieceColor Color for choosing uppercase or lowercase letter
     *
     * @return Type of piece as code for FEN (WHITE: uppercase, BLACK: lowercase)
     *
     * @since 1.0.0
     */
    abstract fun getMatchCode(pieceColor: PieceColor): String

    /**
     * @author Felix Dimmel
     *
     * @return Type of piece as code for SAN
     *
     * @since 1.0.0
     */
    abstract fun getDrawCode(): String
}