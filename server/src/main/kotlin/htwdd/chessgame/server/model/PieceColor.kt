package htwdd.chessgame.server.model

/**
 * Represent a color of piece or player
 *
 * @author Felix Dimmel
 *
 * @property WHITE White color
 * @property BLACK Black color
 *
 * @since 1.0.0
 */
enum class PieceColor {
    /**
     * Color white
     */
    WHITE {

        override fun getCode(): String = "w"
        override fun getOpposite(): PieceColor = BLACK
    },
    /**
     * Color black
     */
    BLACK {
        override fun getCode(): String = "b"
        override fun getOpposite(): PieceColor = WHITE
    };

    /**
     * @author Felix Dimmel
     *
     * @return the color as code for FEN oder SAN
     *
     * @since 1.0.0
     */
    abstract fun getCode(): String

    /**
     * @author Felix Dimmel
     *
     * @return the opposite color
     *
     * @since 1.0.0
     */
    abstract fun getOpposite(): PieceColor
}