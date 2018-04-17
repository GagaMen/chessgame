package htwdd.chessgame.server.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DataType.*
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import htwdd.chessgame.server.model.PieceColor.BLACK
import htwdd.chessgame.server.model.PieceColor.WHITE
import htwdd.chessgame.server.model.PieceType.*

@DatabaseTable(tableName = "Match")
data class Match(
        @DatabaseField(generatedId = true)
        val id: Int = 0,
        @DatabaseField(dataType = SERIALIZABLE, canBeNull = false)
        val players: HashMap<PieceColor, Player> = HashMap(),
        @DatabaseField(foreign = true, canBeNull = false, unique = true)
        private val playerWhite: Player? = null,
        @DatabaseField(foreign = true, canBeNull = false, unique = true)
        private val playerBlack: Player? = null,
//        @JsonIgnore
        var pieceSets: HashMap<PieceColor, PieceSet> = hashMapOf(WHITE to PieceSet(pieceColor = WHITE), BLACK to PieceSet(pieceColor = BLACK)), //todo make this maybe with extra class for parsing
        @DatabaseField(canBeNull = false)
        var currentColor: PieceColor = WHITE,
        val history: MutableList<Draw> = mutableListOf(),
        @DatabaseField(dataType = SERIALIZABLE, canBeNull = false)
        var kingsideCastling: HashMap<PieceColor, Boolean> = hashMapOf(WHITE to true, BLACK to true),
        @DatabaseField(dataType = SERIALIZABLE, canBeNull = false)
        var queensideCastling: HashMap<PieceColor, Boolean> = hashMapOf(WHITE to true, BLACK to true),
        var enPassantField: Field? = null,
        @DatabaseField(canBeNull = false)
        var halfMoves: Int = 0,
        @DatabaseField(dataType = SERIALIZABLE, canBeNull = false)
        var check: HashMap<PieceColor, Boolean> = hashMapOf(WHITE to false, BLACK to false),
        @DatabaseField(canBeNull = false)
        var checkmate: Boolean = false,
        @DatabaseField(canBeNull = false)
        var matchCode: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1") {

    fun setPieceSetsByMatchCode() {
        val separate = matchCode.split(" ")
        val rows = separate[0].split("/")

        for (i in rows.indices) {
            val row = 8 - i
            var column = 1

            rows[i].split("").forEach { char ->
                when (char) {
                    "" -> return@forEach
                    "p" -> {
                        setPiece(BLACK, PAWN, Field(row = row, column = column))
                        column++
                    }
                    "k" -> {
                        setPiece(BLACK, KING, Field(row = row, column = column))
                        column++
                    }
                    "q" -> {
                        setPiece(BLACK, QUEEN, Field(row = row, column = column))
                        column++
                    }
                    "b" -> {
                        setPiece(BLACK, BISHOP, Field(row = row, column = column))
                        column++
                    }
                    "n" -> {
                        setPiece(BLACK, KNIGHT, Field(row = row, column = column))
                        column++
                    }
                    "r" -> {
                        setPiece(BLACK, ROOK, Field(row = row, column = column))
                        column++
                    }
                    "P" -> {
                        setPiece(WHITE, PAWN, Field(row = row, column = column))
                        column++
                    }
                    "K" -> {
                        setPiece(WHITE, KING, Field(row = row, column = column))
                        column++
                    }
                    "Q" -> {
                        setPiece(WHITE, QUEEN, Field(row = row, column = column))
                        column++
                    }
                    "B" -> {
                        setPiece(WHITE, BISHOP, Field(row = row, column = column))
                        column++
                    }
                    "N" -> {
                        setPiece(WHITE, KNIGHT, Field(row = row, column = column))
                        column++
                    }
                    "R" -> {
                        setPiece(WHITE, ROOK, Field(row = row, column = column))
                        column++
                    }
                    else -> {
                        val number = char.toIntOrNull() ?: return@forEach
                        column += number
                    }
                }
            }
        }
    }

    private fun setPiece(pieceColor: PieceColor, pieceType: PieceType, field: Field) {
        val pieces = pieceSets[pieceColor]?.activePieces
        pieces!![field.asPair()] = Piece(pieceType, field)
    }

    fun switchColor() {
        currentColor = currentColor.getOpposite()
    }
}