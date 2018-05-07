package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceType

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
                val key = it.key.toPair() ?: throw NullPointerException()
                when (it.value.type) {
                    PieceType.BISHOP -> {
                        bishop.getThreadedFields(threatedFields, key.first, key.second, match)
                    }
                    PieceType.PAWN -> {
                        pawn.getThreadedFields(threatedFields, key.first, key.second, match)
                    }
                    PieceType.KING -> {
                        king.getThreadedFields(threatedFields, key.first, key.second, match)
                    }
                    PieceType.QUEEN -> {
                        queen.getThreadedFields(threatedFields, key.first, key.second, match)
                    }
                    PieceType.KNIGHT -> {
                        knight.getThreadedFields(threatedFields, key.first, key.second, match)
                    }
                    PieceType.ROOK -> {
                        rook.getThreadedFields(threatedFields, key.first, key.second, match)
                    }
                }
            }

            threatedFields.forEach {
                if (currentPieces.containsKey(Pair(it.first, it.second).toString())) {
                    val piece = currentPieces[Pair(it.first, it.second).toString()] ?: return@forEach
                    if (piece.type == PieceType.KING) return true
                }
            }

            return false
        }

        fun checkmate(match: Match): Boolean {
            val currentPieces = match.pieceSets[match.currentColor]?.activePieces ?: return false
            val movementFields = HashSet<Pair<Int, Int>>()

            currentPieces.forEach {
                val key = it.key.toPair() ?: throw NullPointerException()
                val tmpMovementFields = HashSet<Pair<Int, Int>>()
                when (it.value.type) {
                    PieceType.BISHOP -> {
                        bishop.getFilteredMovementFields(tmpMovementFields, key.first, key.second, match)
                    }
                    PieceType.KING -> {
                        king.getFilteredMovementFields(tmpMovementFields, key.first, key.second, match)
                    }
                    PieceType.KNIGHT -> {
                        knight.getFilteredMovementFields(tmpMovementFields, key.first, key.second, match)
                    }
                    PieceType.PAWN -> {
                        pawn.getFilteredMovementFields(tmpMovementFields, key.first, key.second, match)
                    }
                    PieceType.QUEEN -> {
                        queen.getFilteredMovementFields(tmpMovementFields, key.first, key.second, match)
                    }
                    PieceType.ROOK -> {
                        rook.getFilteredMovementFields(tmpMovementFields, key.first, key.second, match)
                    }
                }
                movementFields.addAll(tmpMovementFields)
            }

            if (movementFields.size == 0) return true
            return false
        }
    }
}