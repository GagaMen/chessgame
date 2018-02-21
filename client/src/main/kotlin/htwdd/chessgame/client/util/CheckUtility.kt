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
    }
}