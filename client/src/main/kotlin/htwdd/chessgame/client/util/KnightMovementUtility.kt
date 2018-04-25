package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match

class KnightMovementUtility : MovementUtility() {
    override fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val currentPieces = match.pieceSets[match.currentColor]?.activePieces ?: return

        if (row + 2 < 9 && col + 1 < 9 && !currentPieces.containsKey(Pair(row + 2, col + 1).toString())) {
            movementFields.add(Pair(row + 2, col + 1))
        }
        if (row + 1 < 9 && col + 2 < 9 && !currentPieces.containsKey(Pair(row + 1, col + 2).toString())) {
            movementFields.add(Pair(row + 1, col + 2))
        }
        if (row - 1 > 0 && col + 2 < 9 && !currentPieces.containsKey(Pair(row - 1, col + 2).toString())) {
            movementFields.add(Pair(row - 1, col + 2))
        }
        if (row - 2 > 0 && col + 1 < 9 && !currentPieces.containsKey(Pair(row - 2, col + 1).toString())) {
            movementFields.add(Pair(row - 2, col + 1))
        }
        if (row - 2 > 0 && col - 1 > 0 && !currentPieces.containsKey(Pair(row - 2, col - 1).toString())) {
            movementFields.add(Pair(row - 2, col - 1))
        }
        if (row - 1 > 0 && col - 2 > 0 && !currentPieces.containsKey(Pair(row - 1, col - 2).toString())) {
            movementFields.add(Pair(row - 1, col - 2))
        }
        if (row + 1 < 9 && col - 2 > 0 && !currentPieces.containsKey(Pair(row + 1, col - 2).toString())) {
            movementFields.add(Pair(row + 1, col - 2))
        }
        if (row + 2 < 9 && col - 1 > 0 && !currentPieces.containsKey(Pair(row + 2, col - 1).toString())) {
            movementFields.add(Pair(row + 2, col - 1))
        }
    }

    override fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: return

        if (row + 2 < 9 && col + 1 < 9 && !opposingPieces.containsKey(Pair(row + 2, col + 1).toString())) {
            threatedFields.add(Pair(row + 2, col + 1))
        }
        if (row + 1 < 9 && col + 2 < 9 && !opposingPieces.containsKey(Pair(row + 1, col + 2).toString())) {
            threatedFields.add(Pair(row + 1, col + 2))
        }
        if (row - 1 > 0 && col + 2 < 9 && !opposingPieces.containsKey(Pair(row - 1, col + 2).toString())) {
            threatedFields.add(Pair(row - 1, col + 2))
        }
        if (row - 2 > 0 && col + 1 < 9 && !opposingPieces.containsKey(Pair(row - 2, col + 1).toString())) {
            threatedFields.add(Pair(row - 2, col + 1))
        }
        if (row - 2 > 0 && col - 1 > 0 && !opposingPieces.containsKey(Pair(row - 2, col - 1).toString())) {
            threatedFields.add(Pair(row - 2, col - 1))
        }
        if (row - 1 > 0 && col - 2 > 0 && !opposingPieces.containsKey(Pair(row - 1, col - 2).toString())) {
            threatedFields.add(Pair(row - 1, col - 2))
        }
        if (row + 1 < 9 && col - 2 > 0 && !opposingPieces.containsKey(Pair(row + 1, col - 2).toString())) {
            threatedFields.add(Pair(row + 1, col - 2))
        }
        if (row + 2 < 9 && col - 1 > 0 && !opposingPieces.containsKey(Pair(row + 2, col - 1).toString())) {
            threatedFields.add(Pair(row + 2, col - 1))
        }
    }
}