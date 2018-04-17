package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.PieceType

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
            val currentPieces = match.pieceSets[match.currentColor]?.activePieces ?: return false
            val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: return false

            threatedFields.clear()

            opposingPieces.forEach {
                when (it.value.type) {
                    PieceType.BISHOP -> {
                        bishop.getThreadedFields(threatedFields, it.key.first, it.key.second, match)
                    }
                    PieceType.PAWN -> {
                        pawn.getThreadedFields(threatedFields, it.key.first, it.key.second, match)
                    }
                    PieceType.KING -> {
                        king.getThreadedFields(threatedFields, it.key.first, it.key.second, match)
                    }
                    PieceType.QUEEN -> {
                        queen.getThreadedFields(threatedFields, it.key.first, it.key.second, match)
                    }
                    PieceType.KNIGHT -> {
                        knight.getThreadedFields(threatedFields, it.key.first, it.key.second, match)
                    }
                    PieceType.ROOK -> {
                        rook.getThreadedFields(threatedFields, it.key.first, it.key.second, match)
                    }
                }
            }

            threatedFields.forEach {
                if (currentPieces.containsKey(Pair(it.first, it.second))) {
                    val piece = currentPieces[Pair(it.first, it.second)] ?: return@forEach
                    if (piece.type == PieceType.KING) return true
                }
            }

            return false
        }

        fun checkmate(match: Match): Boolean {
            val currentPieces = match.pieceSets[match.currentColor]?.activePieces ?: return false
            val movementFields = HashSet<Pair<Int, Int>>()

            currentPieces.forEach {
                val tmpMovementFields = HashSet<Pair<Int, Int>>()
                when (it.value.type) {
                    PieceType.BISHOP -> {
                        bishop.getFilteredMovementFields(tmpMovementFields, it.key.first, it.key.second, match)
                    }
                    PieceType.KING -> {
                        king.getFilteredMovementFields(tmpMovementFields, it.key.first, it.key.second, match)
                    }
                    PieceType.KNIGHT -> {
                        knight.getFilteredMovementFields(tmpMovementFields, it.key.first, it.key.second, match)
                    }
                    PieceType.PAWN -> {
                        pawn.getFilteredMovementFields(tmpMovementFields, it.key.first, it.key.second, match)
                    }
                    PieceType.QUEEN -> {
                        queen.getFilteredMovementFields(tmpMovementFields, it.key.first, it.key.second, match)
                    }
                    PieceType.ROOK -> {
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