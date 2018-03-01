package htwdd.chessgame.server.model

import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.Ignore
import ninja.sakib.pultusorm.annotations.PrimaryKey

data class Match(var playerWhite: Int = -1,
                 var playerBlack: Int = -1,
                 @Ignore var currentColor: PieceColor = PieceColor.WHITE,
                 var history: String = "",
                 @Ignore var historyList: MutableList<Draw> = mutableListOf(),
                 @Ignore var whiteCastlingKingSide: Boolean = true,
                 @Ignore var whiteCastlingQueenSide: Boolean = true,
                 @Ignore var blackCastlingKingSide: Boolean = true,
                 @Ignore var blackCastlingQueenSide: Boolean = true,
                 @Ignore var enPassantField: Field? = null,
                 @Ignore var halfMoves: Int = 0,
                 @Ignore var check: HashMap<PieceColor, Boolean> = hashMapOf(PieceColor.WHITE to false, PieceColor.BLACK to false),
                 @Ignore var checkmate: Boolean = false,
                 var matchCode: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1") {
    @PrimaryKey
    @AutoIncrement
    val id: Int = 0
    @Ignore
    private var pieceSets: HashMap<PieceColor, PieceSet> = HashMap()

    init {
        pieceSets[PieceColor.WHITE] = PieceSet(pieceColor = PieceColor.WHITE)
        pieceSets[PieceColor.BLACK] = PieceSet(pieceColor = PieceColor.BLACK)
        setValuesByMatchCode()
    }

    fun setValuesByMatchCode() {
        val separate = matchCode.split("/", " ")

        for (i in separate.indices) {
            when {
                i < 8 -> setPieceSets(i + 1, separate[i])
                i == 8 -> setColor(separate[i])
                i == 9 -> setCastling(separate[i])
                i == 10 -> setEnPassant(separate[i])
                i == 11 -> setHalfMoves(separate[i])
            }
        }
    }

    private fun setPieceSets(row: Int, str: String) {
        val separation = str.split("")
        var column = 1

        separation.forEach { char ->
            when (char) {
                "" -> return@forEach
                "p" -> {
                    setPiece(PieceColor.WHITE, PieceType.PAWN, Field(row, column))
                    column++
                }
                "k" -> {
                    setPiece(PieceColor.WHITE, PieceType.KING, Field(row, column))
                    column++
                }
                "q" -> {
                    setPiece(PieceColor.WHITE, PieceType.QUEEN, Field(row, column))
                    column++
                }
                "b" -> {
                    setPiece(PieceColor.WHITE, PieceType.BISHOP, Field(row, column))
                    column++
                }
                "n" -> {
                    setPiece(PieceColor.WHITE, PieceType.KNIGHT, Field(row, column))
                    column++
                }
                "r" -> {
                    setPiece(PieceColor.WHITE, PieceType.ROOK, Field(row, column))
                    column++
                }
                "P" -> {
                    setPiece(PieceColor.BLACK, PieceType.PAWN, Field(row, column))
                    column++
                }
                "K" -> {
                    setPiece(PieceColor.BLACK, PieceType.KING, Field(row, column))
                    column++
                }
                "Q" -> {
                    setPiece(PieceColor.BLACK, PieceType.QUEEN, Field(row, column))
                    column++
                }
                "B" -> {
                    setPiece(PieceColor.BLACK, PieceType.BISHOP, Field(row, column))
                    column++
                }
                "N" -> {
                    setPiece(PieceColor.BLACK, PieceType.KNIGHT, Field(row, column))
                    column++
                }
                "R" -> {
                    setPiece(PieceColor.BLACK, PieceType.ROOK, Field(row, column))
                    column++
                }
                else -> {
                    val number = char.toIntOrNull() ?: return@forEach
                    column += number
                }
            }
        }
    }

    private fun setPiece(pieceColor: PieceColor, pieceType: PieceType, field: Field) {
        val pieces = pieceSets[pieceColor]?.activePieces
        pieces!![field.getAsPair()] = Piece(pieceType, field)
    }

    private fun setColor(str: String) {
        when (str) {
            "w" -> currentColor = PieceColor.WHITE
            "b" -> currentColor = PieceColor.BLACK
        }
    }

    private fun setCastling(str: String) {
        val separation = str.split("")

        separation.forEach { char ->
            when (char) {
                "K" -> whiteCastlingKingSide = true
                "Q" -> whiteCastlingQueenSide = true
                "k" -> blackCastlingKingSide = true
                "q" -> blackCastlingQueenSide = true
            }
        }
    }

    private fun setEnPassant(str: String) {
        enPassantField = when (str) {
            "-" -> null
            else -> {
                val separation = str.split("")
                val column = separation[1].toCharArray()[0].toInt() % 96
                val row = separation[2].toInt()
                Field(row, column)
            }
        }
    }

    private fun setHalfMoves(str: String) {
        val count = str.toIntOrNull() ?: return
        halfMoves = count
    }
}