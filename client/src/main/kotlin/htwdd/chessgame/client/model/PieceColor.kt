package htwdd.chessgame.client.model

enum class PieceColor {
    WHITE {
        override fun getCode(): String = "w"
    },
    BLACK {
        override fun getCode(): String = "b"
    };

    abstract fun getCode(): String
}