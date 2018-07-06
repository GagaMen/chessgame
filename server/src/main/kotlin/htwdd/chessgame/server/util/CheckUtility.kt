package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.PieceType.*
import htwdd.chessgame.server.util.CheckUtility.Companion.bishop
import htwdd.chessgame.server.util.CheckUtility.Companion.king
import htwdd.chessgame.server.util.CheckUtility.Companion.knight
import htwdd.chessgame.server.util.CheckUtility.Companion.pawn
import htwdd.chessgame.server.util.CheckUtility.Companion.queen
import htwdd.chessgame.server.util.CheckUtility.Companion.rook
import htwdd.chessgame.server.util.CheckUtility.Companion.threatedFields

/**
 * Utility class handle threated field based on the piece type
 *
 * @author Felix Dimmel
 *
 * @property threatedFields Container for all threated field of a color
 * @property bishop utility object to get movement and threated field of a bishop piece
 * @property king utility object to get movement and threated field of a king piece
 * @property knight utility object to get movement and threated field of a knight piece
 * @property pawn utility object to get movement and threated field of a pawn piece
 * @property queen utility object to get movement and threated field of a queen piece
 * @property rook utility object to get movement and threated field of a rook piece
 *
 * @since 1.0.0
 */
class CheckUtility {
    /**
     * Static CheckUtility object
     */
    companion object {
        private var threatedFields = HashSet<Pair<Int, Int>>()
        private val bishop = BishopMovementUtility()
        private val king = KingMovementUtility()
        private val knight = KnightMovementUtility()
        private val pawn = PawnMovementUtility()
        private val queen = QueenMovementUtility()
        private val rook = RookMovementUtility()

        /**
         * Calculate threated fields based on the piece type
         *
         * @author Felix Dimmel
         *
         * @param match Match reference
         *
         * @return True if the opponent is in check otherwise false
         *
         * @since 1.0.0
         */
        fun calcThreatedFields(match: Match): Boolean {
            val currentPieces = match.pieceSets[match.currentColor]?.activePieces
                    ?: throw NullPointerException()
            val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces
                    ?: throw NullPointerException()

            threatedFields.clear()

            opposingPieces.forEach {
                when (it.value.type) {
                    BISHOP -> {
                        bishop.getThreadedFields(threatedFields, it.key.first, it.key.second, match)
                    }
                    PAWN -> {
                        pawn.getThreadedFields(threatedFields, it.key.first, it.key.second, match)
                    }
                    KING -> {
                        king.getThreadedFields(threatedFields, it.key.first, it.key.second, match)
                    }
                    QUEEN -> {
                        queen.getThreadedFields(threatedFields, it.key.first, it.key.second, match)
                    }
                    KNIGHT -> {
                        knight.getThreadedFields(threatedFields, it.key.first, it.key.second, match)
                    }
                    ROOK -> {
                        rook.getThreadedFields(threatedFields, it.key.first, it.key.second, match)
                    }
                }
            }

            threatedFields.forEach {
                if (currentPieces.containsKey(Pair(it.first, it.second))) {
                    val piece = currentPieces[Pair(it.first, it.second)] ?: return@forEach
                    if (piece.type == KING) return true
                }
            }

            return false
        }

        /**
         * Calcualte possible movement fields based on the piece type
         *
         * @author Felix Dimmel
         *
         * @param match Match reference
         *
         * @return True if no movement field was found otherwise false
         *
         * @since 1.0.0
         */
        fun checkmate(match: Match): Boolean {
            val currentPieces = match.pieceSets[match.currentColor]?.activePieces
                    ?: throw NullPointerException()
            val movementFields = HashSet<Pair<Int, Int>>()

            currentPieces.forEach {
                val tmpMovementFields = HashSet<Pair<Int, Int>>()
                when (it.value.type) {
                    BISHOP -> {
                        bishop.getFilteredMovementFields(tmpMovementFields, it.key.first, it.key.second, match)
                    }
                    KING -> {
                        king.getFilteredMovementFields(tmpMovementFields, it.key.first, it.key.second, match)
                    }
                    KNIGHT -> {
                        knight.getFilteredMovementFields(tmpMovementFields, it.key.first, it.key.second, match)
                    }
                    PAWN -> {
                        pawn.getFilteredMovementFields(tmpMovementFields, it.key.first, it.key.second, match)
                    }
                    QUEEN -> {
                        queen.getFilteredMovementFields(tmpMovementFields, it.key.first, it.key.second, match)
                    }
                    ROOK -> {
                        rook.getFilteredMovementFields(tmpMovementFields, it.key.first, it.key.second, match)
                    }
                }
                movementFields.addAll(tmpMovementFields)
            }

            if (movementFields.size == 0) return true
            return false
        }
    }
}