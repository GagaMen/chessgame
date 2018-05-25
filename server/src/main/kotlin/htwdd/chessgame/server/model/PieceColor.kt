package htwdd.chessgame.server.model

/**
 * @author Felix Dimmel
 *
 * @property WHITE White color
 * @property BLACK Black color
 *
 * @since 1.0.0
 */
enum class PieceColor {
    WHITE {

        override fun getCode(): String = "w"
        override fun getOpposite(): PieceColor = BLACK
    },
    BLACK {
        override fun getCode(): String = "b"
        override fun getOpposite(): PieceColor = WHITE
    };

    /**
     * @return the color as code for FEN oder SAN
     *
     * @since 1.0.0
     */
    abstract fun getCode(): String

    /**
     * @return the opposite color
     *
     * @since 1.0.0
     */
    abstract fun getOpposite(): PieceColor
}