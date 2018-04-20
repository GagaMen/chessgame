package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.PieceColor.BLACK
import htwdd.chessgame.server.model.PieceColor.WHITE

class KingMovementUtility : MovementUtility() {
    override fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val currentPieces = match.pieceSets[match.currentColor]?.activePieces
                ?: throw NullPointerException()

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
            WHITE -> {
                if (match.kingsideCastling[WHITE]!! &&
                        !currentPieces.containsKey(Pair(row, col + 1)) &&
                        !currentPieces.containsKey(Pair(row, col + 2))
                ) {
                    movementFields.add(Pair(row, col + 2))
                }
                if (match.queensideCastling[WHITE]!! &&
                        !currentPieces.containsKey(Pair(row, col - 1)) &&
                        !currentPieces.containsKey(Pair(row, col - 2)) &&
                        !currentPieces.containsKey(Pair(row, col - 3))
                ) {
                    movementFields.add(Pair(row, col - 2))
                }
            }
            BLACK -> {
                if (match.kingsideCastling[BLACK]!! &&
                        !currentPieces.containsKey(Pair(row, col + 1)) &&
                        !currentPieces.containsKey(Pair(row, col + 2))
                ) {
                    movementFields.add(Pair(row, col + 2))
                }
                if (match.queensideCastling[BLACK]!! &&
                        !currentPieces.containsKey(Pair(row, col - 1)) &&
                        !currentPieces.containsKey(Pair(row, col - 2)) &&
                        !currentPieces.containsKey(Pair(row, col - 3))
                ) {
                    movementFields.add(Pair(row, col - 2))
                }
            }
        }
    }

    override fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces
                ?: throw NullPointerException()

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