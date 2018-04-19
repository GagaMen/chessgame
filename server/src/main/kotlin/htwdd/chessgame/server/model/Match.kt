package htwdd.chessgame.server.model

import com.j256.ormlite.field.DataType.*
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import htwdd.chessgame.server.model.PieceColor.*
import htwdd.chessgame.server.model.PieceType.*
import htwdd.chessgame.server.util.DatabaseUtility.Companion.fieldDao
import htwdd.chessgame.server.util.FENUtility

@DatabaseTable(tableName = "Match")
data class Match(
        @DatabaseField(generatedId = true)
        val id: Int = 0,
        @DatabaseField(dataType = SERIALIZABLE, canBeNull = false)
        val players: HashMap<PieceColor, Player> = HashMap(),
        @DatabaseField(foreign = true, canBeNull = false)
        private val playerWhite: Player? = null,
        @DatabaseField(foreign = true, canBeNull = false)
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
        @DatabaseField(foreign = true, foreignAutoRefresh = true)
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

    fun updateByDraw(draw: Draw) {
        updatePieceSets(draw)

        if (draw.throwPiece || draw.pieceType == PAWN) {
            halfMoves = 0
        } else {
            halfMoves++
        }

        if (draw.kingsideCastling || draw.queensideCastling) {
            kingsideCastling[currentColor] = false
            queensideCastling[currentColor] = false
        }

        if (draw.pieceType == PAWN && (
                        (currentColor == WHITE && draw.endField?.row == 4) ||
                                (currentColor == BLACK && draw.endField?.row == 5))
        ) {
            pieceSets[currentColor]?.activePieces?.forEach {
                if (it.value.type != PAWN) return@forEach
                if (it.key.second != draw.endField?.column) return@forEach

                val row = when (currentColor) {
                    WHITE -> 3
                    BLACK -> 6
                }

                if (enPassantField == null) {
                    enPassantField = Field(row = row, column = draw.endField?.column!!)
                    fieldDao!!.create(enPassantField) // todo maybe throw an error
                } else {
                    enPassantField?.row = row
                    enPassantField?.column = draw.endField?.column!!
                    fieldDao!!.update(enPassantField)
                }
            }
        } else if (enPassantField != null) {
            enPassantField!!.row = 0
            enPassantField!!.column = 0
        }

        check[currentColor] = draw.check
        checkmate = draw.checkmate

        switchColor()

        FENUtility.calc(this)
    }

    private fun switchColor() {
        currentColor = currentColor.getOpposite()
    }

    private fun updatePieceSets(draw: Draw): Boolean {
        val activePieces = pieceSets[currentColor]?.activePieces ?: return false
        val opposingActivePieces = pieceSets[currentColor.getOpposite()]?.activePieces ?: return false
        val capturedPieces = pieceSets[currentColor]?.capturedPieces ?: return false

        if (draw.kingsideCastling || draw.queensideCastling) {
            val row = when (currentColor) {
                WHITE -> 1
                BLACK -> 8
            }
            val column = when {
                draw.kingsideCastling -> 8
                draw.queensideCastling -> 1
                else -> 0 // at no time possible
            }
            val king = activePieces[Pair(row, 5)] ?: return false
            val rook = activePieces[Pair(row, column)] ?: return false
            activePieces.remove(Pair(row, 5))
            activePieces.remove(Pair(row, column))

            val kingColumn = when {
                draw.kingsideCastling -> 7
                draw.queensideCastling -> 3
                else -> 0 // at no time possible
            }
            val rookColumn = when {
                draw.kingsideCastling -> 6
                draw.queensideCastling -> 4
                else -> 0 // at no time possible
            }

            king.position.row = row
            king.position.column = kingColumn
            rook.position.row = row
            rook.position.column = rookColumn

            activePieces[Pair(row, kingColumn)] = king
            activePieces[Pair(row, rookColumn)] = rook
        } else {
            val piece = activePieces[draw.startField?.asPair()] ?: return false
            activePieces.remove(draw.startField?.asPair())

            piece.position.row = draw.endField?.row!!
            piece.position.column = draw.endField?.column!!

            activePieces[draw.endField?.asPair()!!] = piece

            if (draw.throwPiece) {
                var capturedPiecePosition = draw.endField?.asPair()!!
                if (draw.throwEnPassant) {
                    capturedPiecePosition = when (currentColor) {
                        WHITE -> Pair(5, draw.endField?.column!!)
                        BLACK -> Pair(4, draw.endField?.column!!)
                    }
                }

                val capturedPiece = opposingActivePieces[capturedPiecePosition] ?: return false
                opposingActivePieces.remove(capturedPiecePosition)

                capturedPieces.add(capturedPiece)
            }
        }

        return true
    }
}