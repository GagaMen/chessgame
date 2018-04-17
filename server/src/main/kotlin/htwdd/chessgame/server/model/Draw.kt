package htwdd.chessgame.server.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "Draw")
data class Draw(
        @DatabaseField(generatedId = true)
        val id: Int = 0,
        @DatabaseField(canBeNull = false)
        var color: PieceColor? = null,
        @DatabaseField(canBeNull = false)
        var pieceType: PieceType? = null,
        @DatabaseField(foreign = true, foreignAutoRefresh = true, unique = true)
        var startField: Field? = null,
        @DatabaseField(foreign = true, foreignAutoRefresh = true, unique = true, canBeNull = false)
        var endField: Field? = null,
        @DatabaseField(foreign = true, canBeNull = false)
        var match: Match? = null,
        @DatabaseField
        var conversion: PieceType? = null,
        @DatabaseField
        var throwPiece: Boolean = false,
        @DatabaseField
        var throwEnPassant: Boolean = false,
        @DatabaseField
        var kingsideCastling: Boolean = false,
        @DatabaseField
        var queensideCastling: Boolean = false,
        @DatabaseField
        var check: Boolean = false,
        @DatabaseField
        var checkmate: Boolean = false,
        @DatabaseField(canBeNull = false)
        var drawCode: String = "") {

    fun setValuesByDrawCode(): Boolean {
        val regex = "([KQBNR])?([a-h]|[1-8])?(x)?([a-h])([1-8])([QBRN])?(e\\.p\\.)?(\\+{1,2}|#)?".toRegex()
        val matchResult = regex.find(drawCode) ?: return false

        when {
            drawCode.contains("^O-O-O") -> {
                pieceType = PieceType.KING
                queensideCastling = true
                return true
            }
            drawCode.contains("^O-O") -> {
                pieceType = PieceType.KING
                kingsideCastling = true
                return true
            }
            else -> pieceType = when (matchResult.groups[1]?.value) {
                "K" -> PieceType.KING
                "Q" -> PieceType.QUEEN
                "B" -> PieceType.BISHOP
                "N" -> PieceType.KNIGHT
                "R" -> PieceType.ROOK
                else -> PieceType.PAWN
            }
        }

        if (matchResult.groups[3] != null) throwPiece = true

        if (matchResult.groups[4] == null || matchResult.groups[5] == null) return false
        val column = matchResult.groups[4]?.value?.toCharArray()?.get(0)?.toInt() ?: return false
        val row = matchResult.groups[5]?.value?.toInt() ?: return false
        endField = Field(row = row, column = column % 96)

        if (matchResult.groups[6] != null) {
            when (matchResult.groups[6]?.value) {
                "Q" -> conversion = PieceType.QUEEN
                "B" -> conversion = PieceType.BISHOP
                "R" -> conversion = PieceType.ROOK
                "N" -> conversion = PieceType.KNIGHT
            }
        }

        if (matchResult.groups[7] != null) throwEnPassant = true

        if (matchResult.groups[8] != null) {
            val checkCode = matchResult.groups[10]?.value ?: return false
            if (checkCode == "+") check = true
            if (checkCode == "++" || checkCode == "#") checkmate = true
        }

        return true
    }
}