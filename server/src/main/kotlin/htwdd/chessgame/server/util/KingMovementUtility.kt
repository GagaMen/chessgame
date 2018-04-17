package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.PieceColor

class KingMovementUtility : MovementUtility() {
    override fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val currentPieces = match.pieceSets[match.currentColor]?.activePieces ?: return

        if (row + 1 != 9 && !currentPieces.containsKey(Pair(row + 1, col))) {
            movementFields.add(Pair(row + 1, col))
        }
        if (row + 1 != 9 && col + 1 != 9 && !currentPieces.containsKey(Pair(row + 1, col + 1))) {
            movementFields.add(Pair(row + 1, col + 1))
        }
        if (col + 1 != 9 && !currentPieces.containsKey(Pair(row, col + 1))) {
            movementFields.add(Pair(row, col + 1))
        }
        if (row - 1 != 0 && col + 1 != 9 && !currentPieces.containsKey(Pair(row - 1, col + 1))) {
            movementFields.add(Pair(row - 1, col + 1))
        }
        if (row - 1 != 0 && !currentPieces.containsKey(Pair(row - 1, col))) {
            movementFields.add(Pair(row - 1, col))
        }
        if (row - 1 != 0 && col - 1 != 0 && !currentPieces.containsKey(Pair(row - 1, col - 1))) {
            movementFields.add(Pair(row - 1, col - 1))
        }
        if (col - 1 != 0 && !currentPieces.containsKey(Pair(row, col - 1))) {
            movementFields.add(Pair(row, col - 1))
        }
        if (row + 1 != 9 && col - 1 != 0 && !currentPieces.containsKey(Pair(row + 1, col - 1))) {
            movementFields.add(Pair(row + 1, col - 1))
        }

        when (match.currentColor) {
            PieceColor.WHITE -> {
                if (match.kingsideCastling[PieceColor.WHITE]!! &&
                        !currentPieces.containsKey(Pair(row, col + 1)) &&
                        !currentPieces.containsKey(Pair(row, col + 2))) {
                    movementFields.add(Pair(row, col + 2))
                }
                if (match.queensideCastling[PieceColor.WHITE]!! &&
                        !currentPieces.containsKey(Pair(row, col - 1)) &&
                        !currentPieces.containsKey(Pair(row, col - 2)) &&
                        !currentPieces.containsKey(Pair(row, col - 3))) {
                    movementFields.add(Pair(row, col - 2))
                }
            }
            PieceColor.BLACK -> {
                if (match.kingsideCastling[PieceColor.BLACK]!! &&
                        !currentPieces.containsKey(Pair(row, col + 1)) &&
                        !currentPieces.containsKey(Pair(row, col + 2))) {
                    movementFields.add(Pair(row, col + 2))
                }
                if (match.queensideCastling[PieceColor.BLACK]!! &&
                        !currentPieces.containsKey(Pair(row, col - 1)) &&
                        !currentPieces.containsKey(Pair(row, col - 2)) &&
                        !currentPieces.containsKey(Pair(row, col - 3))) {
                    movementFields.add(Pair(row, col - 2))
                }
            }
        }
    }

    override fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: return

        if (row + 1 != 9 && !opposingPieces.containsKey(Pair(row + 1, col))) {
            threatedFields.add(Pair(row + 1, col))
        }
        if (row + 1 != 9 && col + 1 != 9 && !opposingPieces.containsKey(Pair(row + 1, col + 1))) {
            threatedFields.add(Pair(row + 1, col + 1))
        }
        if (col + 1 != 9 && !opposingPieces.containsKey(Pair(row, col + 1))) {
            threatedFields.add(Pair(row, col + 1))
        }
        if (row - 1 != 0 && col + 1 != 9 && !opposingPieces.containsKey(Pair(row - 1, col + 1))) {
            threatedFields.add(Pair(row - 1, col + 1))
        }
        if (row - 1 != 0 && !opposingPieces.containsKey(Pair(row - 1, col))) {
            threatedFields.add(Pair(row - 1, col))
        }
        if (row - 1 != 0 && col - 1 != 0 && !opposingPieces.containsKey(Pair(row - 1, col - 1))) {
            threatedFields.add(Pair(row - 1, col - 1))
        }
        if (col - 1 != 0 && !opposingPieces.containsKey(Pair(row, col - 1))) {
            threatedFields.add(Pair(row, col - 1))
        }
        if (row + 1 != 9 && col - 1 != 0 && !opposingPieces.containsKey(Pair(row + 1, col - 1))) {
            threatedFields.add(Pair(row + 1, col - 1))
        }
    }
}