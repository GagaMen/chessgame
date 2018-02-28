package htwdd.chessgame.server.model

import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.PrimaryKey

data class Draw(var color: String = "",
                var pieceType: String = "",
                var startRow: Int = -1,
                var startColumn: Int = -1,
                var endRow: Int = -1,
                var endColumn: Int = -1,
                var throwPiece: Boolean = false,
                var throwEnPassant: Boolean = false,
                var kingsideCastling: Boolean = false,
                var queensideCastling: Boolean = false,
                var drawCode: String = "") {
    @PrimaryKey
    @AutoIncrement
    var id: Int = 0
}