package htwdd.chessgame.server.model

import com.j256.ormlite.field.DataType.SERIALIZABLE
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import htwdd.chessgame.server.model.PieceColor.BLACK
import htwdd.chessgame.server.model.PieceColor.WHITE
import htwdd.chessgame.server.model.PieceType.*
import htwdd.chessgame.server.util.DatabaseUtility.Companion.fieldDao
import htwdd.chessgame.server.util.FENUtility
import java.sql.SQLException
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

/**
 * Represent a chess match
 *
 * @author Felix Dimmel
 *
 * @property id The ID of this match. (Autogenerated by SQLite database)
 * @property playerWhite The ID of the player with the color white.
 * @property playerBlack The ID of the player with the color black.
 * @property pieceSets Hash map of the pieceSets of the two players. Key: Color of player; Value: PieceSet
 * @property currentColor Color of player who is on the move.
 * @property history Mutable list of played draws.
 * @property kingsideCastling Hash map of possibility for kingside castling of each player.
 * @property queensideCastling Hash map of possibility for queenside castling of each player.
 * @property enPassantField Contains the field to throw en passant or null.
 * @property halfMoves Number of halfmoves since the last movement of a pawn or the last thrown piece.
 * @property check Hash map of check value for each player. True if player is in check otherwise false.
 * @property checkmate True if one player is checkmate
 * @property matchCode All match information as Forsyth-Edwards-Notation (FEN)
 *
 * @since 1.0.0
 */
@DatabaseTable(tableName = "Match")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class Match(
        @DatabaseField(generatedId = true)
        @XmlElement
        val id: Int = 0,
        @DatabaseField(dataType = SERIALIZABLE, canBeNull = false)
        @XmlElement
        val players: HashMap<PieceColor, Player> = HashMap(),
        @DatabaseField(foreign = true, canBeNull = false)
        @XmlElement
        private val playerWhite: Player? = null,
        @DatabaseField(foreign = true, canBeNull = false)
        @XmlElement
        private val playerBlack: Player? = null,
        @DatabaseField(dataType = SERIALIZABLE, canBeNull = false)
        @XmlElement
        var pieceSets: HashMap<PieceColor, PieceSet> = hashMapOf(WHITE to PieceSet(), BLACK to PieceSet()),
        @DatabaseField(canBeNull = false)
        @XmlElement
        var currentColor: PieceColor = WHITE,
        @XmlElement
        val history: MutableList<Draw> = mutableListOf(),
        @DatabaseField(dataType = SERIALIZABLE, canBeNull = false)
        @XmlElement
        var kingsideCastling: HashMap<PieceColor, Boolean> = hashMapOf(WHITE to true, BLACK to true),
        @DatabaseField(dataType = SERIALIZABLE, canBeNull = false)
        @XmlElement
        var queensideCastling: HashMap<PieceColor, Boolean> = hashMapOf(WHITE to true, BLACK to true),
        @DatabaseField(foreign = true, foreignAutoRefresh = true)
        @XmlElement
        var enPassantField: Field? = null,
        @DatabaseField(canBeNull = false)
        @XmlElement
        var halfMoves: Int = 0,
        @DatabaseField(dataType = SERIALIZABLE, canBeNull = false)
        @XmlElement
        var check: HashMap<PieceColor, Boolean> = hashMapOf(WHITE to false, BLACK to false),
        @DatabaseField(canBeNull = false)
        @XmlElement
        var checkmate: Boolean = false,
        @DatabaseField(canBeNull = false)
        @XmlElement
        var matchCode: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
) {
    /**
     * Sets all match properties by match code (FEN)
     *
     * @author Felix Dimmel
     *
     * @see [https://en.wikipedia.org/wiki/Forsyth-Edwards_Notation]
     *
     * @since 1.0.0
     */
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

    /**
     * Add piece to pieceSet of given color
     *
     * @author Felix Dimmel
     *
     * @param pieceColor Color to choose the right pieceSet
     * @param pieceType Type of piece
     * @param field Position of piece
     *
     * @since 1.0.0
     */
    private fun setPiece(pieceColor: PieceColor, pieceType: PieceType, field: Field) {
        val pieces = pieceSets[pieceColor]?.activePieces
        pieces!![field.asPair()] = Piece(pieceType, field)
    }

    /**
     * Update match properties by given draw
     *
     * @author Felix Dimmel
     *
     * @param draw Draw to change the match properties
     *
     * @since 1.0.0
     */
    fun updateByDraw(draw: Draw) {
        updatePieceSets(draw)

        if (draw.throwPiece || draw.pieceType == PAWN) {
            halfMoves = 0
        } else {
            halfMoves++
        }

        if (draw.kingsideCastling || draw.queensideCastling || draw.pieceType == KING) {
            kingsideCastling[currentColor] = false
            queensideCastling[currentColor] = false
        }

        if (kingsideCastling[currentColor]!! && draw.pieceType == ROOK && draw.startField?.column == 8) {
            kingsideCastling[currentColor] = false
        }

        if (queensideCastling[currentColor]!! && draw.pieceType == ROOK && draw.startField?.column == 1) {
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
                    if (fieldDao!!.create(enPassantField) != 1) throw SQLException("Can't create en passant field!")
                } else {
                    enPassantField?.row = row
                    enPassantField?.column = draw.endField?.column!!
                    if (fieldDao!!.update(enPassantField) != 1) throw SQLException("Can't update en passant field!")
                }
            }
        } else if (enPassantField != null) {
            enPassantField!!.row = 0
            enPassantField!!.column = 0
            if (fieldDao!!.update(enPassantField) != 1) throw SQLException("Can't update en passant field!")
        }

        check[currentColor] = draw.check
        checkmate = draw.checkmate

        switchColor()

        FENUtility.calc(this)
    }

    /**
     * Change the current color to the opposite
     *
     * @author Felix Dimmel
     *
     * @since 1.0.0
     */
    private fun switchColor() {
        currentColor = currentColor.getOpposite()
    }

    /**
     * Update pieceSets after add an draw
     *
     * @author Felix Dimmel
     *
     * @param draw Draw to update the pieceSets
     *
     * @since 1.0.0
     */
    private fun updatePieceSets(draw: Draw) {
        if (draw.kingsideCastling || draw.queensideCastling) {
            updatePieceSetsByCastling(draw)
        } else {
            updatePieceSetsDefault(draw)
        }
    }

    /**
     * Update pieceSet of current color if the draw is a castling
     *
     * @author Felix Dimmel
     *
     * @param draw Draw to update the pieceSet
     *
     * @since 1.0.0
     */
    private fun updatePieceSetsByCastling(draw: Draw) {
        val activePieces = pieceSets[currentColor]?.activePieces
                ?: throw NullPointerException("The HashMap of active pieces for Player $currentColor is null!")

        val row = when (currentColor) {
            WHITE -> 1
            BLACK -> 8
        }
        val column = when {
            draw.kingsideCastling -> 8
            draw.queensideCastling -> 1
            else -> 0 // at no time possible
        }
        val king = activePieces[Pair(row, 5)] ?: throw NullPointerException("Castling: Can't get king piece!")
        val rook = activePieces[Pair(row, column)] ?: throw NullPointerException("Castling: Can't get rook piece!")
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
    }

    /**
     * Update pieceSet of current color if the draw isn't a castling
     *
     * @author Felix Dimmel
     *
     * @param draw Draw to update the pieceSet
     *
     * @since 1.0.0
     */
    private fun updatePieceSetsDefault(draw: Draw) {
        val activePieces = pieceSets[currentColor]?.activePieces
                ?: throw NullPointerException("The HashMap of active pieces for Player $currentColor is null!")
        val opposingActivePieces = pieceSets[currentColor.getOpposite()]?.activePieces
                ?: throw NullPointerException("The HashMap of active pieces for Player ${currentColor.getOpposite()} is null!")
        val capturedPieces = pieceSets[currentColor]?.capturedPieces
                ?: throw NullPointerException("The HashSet of captured pieces is null!")

        val piece = activePieces[draw.startField?.asPair()] ?: throw NullPointerException("Can't get piece!")
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

            val capturedPiece = opposingActivePieces[capturedPiecePosition]
                    ?: throw NullPointerException("Can't get captured piece!")
            opposingActivePieces.remove(capturedPiecePosition)

            capturedPieces.add(capturedPiece)
        }
    }
}