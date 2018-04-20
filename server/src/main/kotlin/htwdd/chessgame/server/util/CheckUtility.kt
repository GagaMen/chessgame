package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.PieceType.*

class CheckUtility {
    companion object {
        private var threatedFields = HashSet<Pair<Int, Int>>()
        private val bishop = BishopMovementUtility()
        private val king = KingMovementUtility()
        private val knight = KnightMovementUtility()
        private val pawn = PawnMovementUtility()
        private val queen = QueenMovementUtility()
        private val rook = RookMovementUtility()

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