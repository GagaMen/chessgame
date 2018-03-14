package htwdd.chessgame.server.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "Draw")
data class Draw(@DatabaseField(generatedId = true) val id: Int = 0,
                @DatabaseField(canBeNull = false) var color: PieceColor? = null,
                @DatabaseField(canBeNull = false) var pieceType: PieceType? = null,
                @DatabaseField(foreign = true, foreignAutoRefresh = true, unique = true, canBeNull = false) var start: Field? = null,
                @DatabaseField(foreign = true, foreignAutoRefresh = true, unique = true, canBeNull = false) var end: Field? = null,
                @DatabaseField(foreign = true, canBeNull = false) var match: Match? = null,
                var throwPiece: Boolean = false,
                var throwEnPassant: Boolean = false,
                var kingsideCastling: Boolean = false,
                var queensideCastling: Boolean = false,
                @DatabaseField(canBeNull = false) var drawCode: String = "") {

    fun setValuesByDrawCode() {
        if (drawCode.contains("x")) throwPiece = true
        if (drawCode.contains("e.p.")) throwEnPassant = true
        if (drawCode.contains("O-O")) kingsideCastling = true
        if (drawCode.contains("O-O-O")) queensideCastling = true
    }
}