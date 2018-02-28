package htwdd.chessgame.server.model

enum class PieceColor {
    WHITE {

        override fun getCode(): String = "w"
        override fun getOpposite(): PieceColor = BLACK
    },
    BLACK {
        override fun getCode(): String = "b"
        override fun getOpposite(): PieceColor = WHITE
    };

    abstract fun getCode(): String
    abstract fun getOpposite(): PieceColor
}