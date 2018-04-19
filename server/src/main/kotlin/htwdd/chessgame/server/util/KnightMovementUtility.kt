package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match

class KnightMovementUtility : MovementUtility() {
    override fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val currentPieces = match.pieceSets[match.currentColor]?.activePieces ?: throw NullPointerException()

        if (row + 2 < 9 && col + 1 < 9 && !currentPieces.containsKey(Pair(row + 2, col + 1))) {
            movementFields.add(Pair(row + 2, col + 1))
        }
        if (row + 1 < 9 && col + 2 < 9 && !currentPieces.containsKey(Pair(row + 1, col + 2))) {
            movementFields.add(Pair(row + 1, col + 2))
        }
        if (row - 1 > 0 && col + 2 < 9 && !currentPieces.containsKey(Pair(row - 1, col + 2))) {
            movementFields.add(Pair(row - 1, col + 2))
        }
        if (row - 2 > 0 && col + 1 < 9 && !currentPieces.containsKey(Pair(row - 2, col + 1))) {
            movementFields.add(Pair(row - 2, col + 1))
        }
        if (row - 2 > 0 && col - 1 > 0 && !currentPieces.containsKey(Pair(row - 2, col - 1))) {
            movementFields.add(Pair(row - 2, col - 1))
        }
        if (row - 1 > 0 && col - 2 > 0 && !currentPieces.containsKey(Pair(row - 1, col - 2))) {
            movementFields.add(Pair(row - 1, col - 2))
        }
        if (row + 1 < 9 && col - 2 > 0 && !currentPieces.containsKey(Pair(row + 1, col - 2))) {
            movementFields.add(Pair(row + 1, col - 2))
        }
        if (row + 2 < 9 && col - 1 > 0 && !currentPieces.containsKey(Pair(row + 2, col - 1))) {
            movementFields.add(Pair(row + 2, col - 1))
        }
    }

    override fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: throw NullPointerException()

        if (row + 2 < 9 && col + 1 < 9 && !opposingPieces.containsKey(Pair(row + 2, col + 1))) {
            threatedFields.add(Pair(row + 2, col + 1))
        }
        if (row + 1 < 9 && col + 2 < 9 && !opposingPieces.containsKey(Pair(row + 1, col + 2))) {
            threatedFields.add(Pair(row + 1, col + 2))
        }
        if (row - 1 > 0 && col + 2 < 9 && !opposingPieces.containsKey(Pair(row - 1, col + 2))) {
            threatedFields.add(Pair(row - 1, col + 2))
        }
        if (row - 2 > 0 && col + 1 < 9 && !opposingPieces.containsKey(Pair(row - 2, col + 1))) {
            threatedFields.add(Pair(row - 2, col + 1))
        }
        if (row - 2 > 0 && col - 1 > 0 && !opposingPieces.containsKey(Pair(row - 2, col - 1))) {
            threatedFields.add(Pair(row - 2, col - 1))
        }
        if (row - 1 > 0 && col - 2 > 0 && !opposingPieces.containsKey(Pair(row - 1, col - 2))) {
            threatedFields.add(Pair(row - 1, col - 2))
        }
        if (row + 1 < 9 && col - 2 > 0 && !opposingPieces.containsKey(Pair(row + 1, col - 2))) {
            threatedFields.add(Pair(row + 1, col - 2))
        }
        if (row + 2 < 9 && col - 1 > 0 && !opposingPieces.containsKey(Pair(row + 2, col - 1))) {
            threatedFields.add(Pair(row + 2, col - 1))
        }
    }
}