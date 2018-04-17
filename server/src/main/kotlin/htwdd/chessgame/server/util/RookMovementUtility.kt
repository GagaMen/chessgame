package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match

class RookMovementUtility : MovementUtility() {
    override fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val currentPieces = match.pieceSets[match.currentColor]?.activePieces ?: return
        val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: return

        if (col != 1) {
            for (i in (col - 1 downTo 1)) {
                if (currentPieces.containsKey(Pair(row, i))) break
                movementFields.add(Pair(row, i))
                if (opposingPieces.containsKey(Pair(row, i))) break
            }
        }
        if (col != 8) {
            for (i in (col + 1..8)) {
                if (currentPieces.containsKey(Pair(row, i))) break
                movementFields.add(Pair(row, i))
                if (opposingPieces.containsKey(Pair(row, i))) break
            }
        }
        if (row != 1) {
            for (i in (row - 1 downTo 1)) {
                if (currentPieces.containsKey(Pair(i, col))) break
                movementFields.add(Pair(i, col))
                if (opposingPieces.containsKey(Pair(i, col))) break
            }
        }
        if (row != 8) {
            for (i in (row + 1..8)) {
                if (currentPieces.containsKey(Pair(i, col))) break
                movementFields.add(Pair(i, col))
                if (opposingPieces.containsKey(Pair(i, col))) break
            }
        }
    }

    override fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val opposingPieces = match.pieceSets[match.currentColor]?.activePieces ?: return
        val currentPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: return

        if (col != 1) {
            for (i in (col - 1 downTo 1)) {
                if (currentPieces.containsKey(Pair(row, i))) break
                threatedFields.add(Pair(row, i))
                if (opposingPieces.containsKey(Pair(row, i))) break
            }
        }
        if (col != 8) {
            for (i in (col + 1..8)) {
                if (currentPieces.containsKey(Pair(row, i))) break
                threatedFields.add(Pair(row, i))
                if (opposingPieces.containsKey(Pair(row, i))) break
            }
        }
        if (row != 1) {
            for (i in (row - 1 downTo 1)) {
                if (currentPieces.containsKey(Pair(i, col))) break
                threatedFields.add(Pair(i, col))
                if (opposingPieces.containsKey(Pair(i, col))) break
            }
        }
        if (row != 8) {
            for (i in (row + 1..8)) {
                if (currentPieces.containsKey(Pair(i, col))) break
                threatedFields.add(Pair(i, col))
                if (opposingPieces.containsKey(Pair(i, col))) break
            }
        }
    }
}