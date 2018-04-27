package htwdd.chessgame.server.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import htwdd.chessgame.server.model.PieceType.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@DatabaseTable(tableName = "Draw")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class Draw(
        @DatabaseField(generatedId = true)
        @XmlElement
        val id: Int = 0,
        @DatabaseField(canBeNull = false)
        @XmlElement
        var color: PieceColor? = null,
        @DatabaseField(canBeNull = false)
        @XmlElement
        var pieceType: PieceType? = null,
        @DatabaseField(foreign = true, foreignAutoRefresh = true, unique = true)
        @XmlElement
        var startField: Field? = null,
        @DatabaseField(foreign = true, foreignAutoRefresh = true, unique = true, canBeNull = false)
        @XmlElement
        var endField: Field? = null,
        @DatabaseField(foreign = true, canBeNull = false)
        @XmlElement
        var match: Match? = null,
        @DatabaseField
        @XmlElement
        var conversion: PieceType? = null,
        @DatabaseField
        @XmlElement
        var throwPiece: Boolean = false,
        @DatabaseField
        @XmlElement
        var throwEnPassant: Boolean = false,
        @DatabaseField
        @XmlElement
        var kingsideCastling: Boolean = false,
        @DatabaseField
        @XmlElement
        var queensideCastling: Boolean = false,
        @DatabaseField
        @XmlElement
        var check: Boolean = false,
        @DatabaseField
        @XmlElement
        var checkmate: Boolean = false,
        @DatabaseField(canBeNull = false)
        @XmlElement
        var drawCode: String = ""
) {
    fun setValuesByDrawCode() {
        val regex = "([KQBNR])?([a-h]|[1-8])?(x)?([a-h])([1-8])([QBRN])?(e\\.p\\.)?(\\+{1,2}|#)?".toRegex()
        val matchResult: MatchResult

        when {
            drawCode.contains("^O-O-O".toRegex()) -> {
                pieceType = KING
                queensideCastling = true
                return
            }
            drawCode.contains("^O-O".toRegex()) -> {
                pieceType = KING
                kingsideCastling = true
                return
            }
            else -> {
                matchResult = regex.find(drawCode) ?: throw NullPointerException("Can't parse drawCode with regex!")
                pieceType = when (matchResult.groups[1]?.value) {
                    "K" -> KING
                    "Q" -> QUEEN
                    "B" -> BISHOP
                    "N" -> KNIGHT
                    "R" -> ROOK
                    else -> PAWN
                }
            }
        }

        if (matchResult.groups[3] != null) throwPiece = true

        if (matchResult.groups[4] == null || matchResult.groups[5] == null) {
            throw IllegalArgumentException("The draw isn't valid!")
        }

        val column = matchResult.groups[4]?.value?.toCharArray()?.get(0)?.toInt()
                ?: throw NullPointerException("Can't convert column of end position from drawCode to Int!")
        val row = matchResult.groups[5]?.value?.toInt()
                ?: throw NullPointerException("Can't convert row of end position from drawCode to Int!")
        endField = Field(row = row, column = column % 96)

        if (matchResult.groups[6] != null) {
            when (matchResult.groups[6]?.value) {
                "Q" -> conversion = QUEEN
                "B" -> conversion = BISHOP
                "R" -> conversion = ROOK
                "N" -> conversion = KNIGHT
            }
        }

        if (matchResult.groups[7] != null) throwEnPassant = true

        if (matchResult.groups[8] != null) {
            val checkCode = matchResult.groups[8]?.value ?: ""
            if (checkCode == "+") check = true
            if (checkCode == "++" || checkCode == "#") checkmate = true
        }
    }
}