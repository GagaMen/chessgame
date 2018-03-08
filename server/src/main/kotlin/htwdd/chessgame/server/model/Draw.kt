package htwdd.chessgame.server.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "Draw")
data class Draw(@DatabaseField(generatedId = true) val id: Int = 0,
                @DatabaseField var color: PieceColor? = null,
                @DatabaseField var pieceType: PieceType? = null,
                @DatabaseField(foreign = true, foreignAutoRefresh = true) var start: Field? = null,
                @DatabaseField(foreign = true, foreignAutoRefresh = true) var end: Field? = null,
                @DatabaseField(foreign = true) var match: Match? = null,
                var throwPiece: Boolean = false,
                var throwEnPassant: Boolean = false,
                var kingsideCastling: Boolean = false,
                var queensideCastling: Boolean = false,
                @DatabaseField var drawCode: String = "") {

    fun setValuesByDrawCode() {
        if (drawCode.contains("x")) throwPiece = true
        if (drawCode.contains("e.p.")) throwEnPassant = true
        if (drawCode.contains("O-O")) kingsideCastling = true
        if (drawCode.contains("O-O-O")) queensideCastling = true
    }
}