package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.PieceColor
import htwdd.chessgame.server.model.PieceColor.BLACK
import htwdd.chessgame.server.model.PieceColor.WHITE
import htwdd.chessgame.server.model.PieceType
import htwdd.chessgame.server.util.FENUtility.Companion.drawDao

/**
 * Utility class to handle a String as Forsyth-Edwards-Notation (FEN)
 *
 * @author Felix Dimmel
 *
 * @property drawDao Database access object for draws
 *
 * @since 1.0.0
 */
class FENUtility {
    /**
     * Static FENUtility object
     */
    companion object {
        private val drawDao = DatabaseUtility.drawDao

        /**
         * Calculate the FEN String based on the match properties
         *
         * @author Felix Dimmel
         *
         * @param match Match for which the FEN should be calculated
         *
         * @since 1.0.0
         */
        fun calc(match: Match) {
            val sb = StringBuilder()
            val whitePieceSet = match.pieceSets[WHITE] ?: throw NullPointerException()
            val blackPieceSet = match.pieceSets[BLACK] ?: throw NullPointerException()

            // piece position
            for (i in 8 downTo 1) {
                var emptyCol = 0
                for (j in 1..8) {
                    var pieceType: PieceType? = null
                    var pieceColor: PieceColor? = null

                    when {
                        whitePieceSet.activePieces.containsKey(Pair(i, j)) -> {
                            pieceType = whitePieceSet.activePieces[Pair(i, j)]?.type ?: throw NullPointerException()
                            pieceColor = WHITE
                        }
                        blackPieceSet.activePieces.containsKey(Pair(i, j)) -> {
                            pieceType = blackPieceSet.activePieces[Pair(i, j)]?.type ?: throw NullPointerException()
                            pieceColor = BLACK
                        }
                        else -> emptyCol++
                    }

                    if (pieceType == null || pieceColor == null) continue

                    if (emptyCol > 0) {
                        sb.append(emptyCol)
                        emptyCol = 0
                    }
                    sb.append(pieceType.getMatchCode(pieceColor))
                }
                if (emptyCol > 0) sb.append(emptyCol)
                if (i != 1) sb.append("/")
            }

            // train right
            sb.append(" ${match.currentColor.getCode()}")

            // castling right
            if (!match.kingsideCastling[WHITE]!! &&
                    !match.queensideCastling[WHITE]!! &&
                    !match.kingsideCastling[BLACK]!! &&
                    !match.queensideCastling[BLACK]!!) {
                sb.append(" -")
            } else {
                sb.append(" ")
                if (match.kingsideCastling[WHITE]!!) sb.append("K")
                if (match.queensideCastling[WHITE]!!) sb.append("Q")
                if (match.kingsideCastling[BLACK]!!) sb.append("k")
                if (match.queensideCastling[BLACK]!!) sb.append("q")
            }

            // en passant
            if (match.enPassantField != null && match.enPassantField?.row != 0 && match.enPassantField?.column != 0) {
                sb.append(" ${(match.enPassantField?.column?.plus(96))?.toChar()}${match.enPassantField?.row}")
            } else {
                sb.append(" -")
            }

            // halfMoves
            sb.append(" ${match.halfMoves}")

            // next train number
            val history = drawDao!!.queryForEq("match_id", match.id)
            sb.append(" ${history.size + 1}")

            match.matchCode = sb.toString()
        }

        /**
         * Prepares a FEN String as URL parameter
         *
         * Replace:
         * - "/" -> "%2F"
         * - " " -> "+"
         *
         * @author Felix Dimmel
         *
         * @param fen String  in the FEN
         *
         * @return Prepared FEN string as URL parameter with replaced characters
         *
         * @since 1.0.0
         */
        fun prepareFENForURLParam(fen: String): String {
            return fen.replace("/", "%2F").replace(" ", "+")
        }
    }
}