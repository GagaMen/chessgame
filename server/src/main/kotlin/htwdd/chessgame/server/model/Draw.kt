package htwdd.chessgame.server.model

import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.Ignore
import ninja.sakib.pultusorm.annotations.PrimaryKey

data class Draw(var colorString: String = "",
                @Ignore var color: PieceColor? = null,
                var pieceTypeString: String = "",
                @Ignore var pieceType: PieceType? = null,
                var startRow: Int = -1,
                var startColumn: Int = -1,
                @Ignore var start: Field? = null,
                var endRow: Int = -1,
                var endColumn: Int = -1,
                @Ignore var end: Field? = null,
                var matchID: Int = -1,
                @Ignore var throwPiece: Boolean = false,
                @Ignore var throwEnPassant: Boolean = false,
                @Ignore var kingsideCastling: Boolean = false,
                @Ignore var queensideCastling: Boolean = false,
                var drawCode: String = "") {
    @PrimaryKey
    @AutoIncrement
    var id: Int = 0

    fun setValuesByDrawCode() {
        if (drawCode.contains("x")) throwPiece = true
        if (drawCode.contains("e.p.")) throwEnPassant = true
        if (drawCode.contains("O-O")) kingsideCastling = true
        if (drawCode.contains("O-O-O")) queensideCastling = true

        color = PieceColor.valueOf(colorString)
        pieceType = PieceType.valueOf(pieceTypeString)

        start = Field(startRow, startColumn)
        end = Field(endRow, endColumn)
    }
}