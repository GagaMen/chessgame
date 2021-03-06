package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match

class RookMovementUtility : MovementUtility() {
    override fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val currentPieces = match.pieceSets[match.currentColor]?.activePieces ?: return
        val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: return

        if (col != 1) {
            for (i in (col - 1 downTo 1)) {
                if (currentPieces.containsKey(Pair(row, i).toString())) break
                movementFields.add(Pair(row, i))
                if (opposingPieces.containsKey(Pair(row, i).toString())) break
            }
        }
        if (col != 8) {
            for (i in (col + 1..8)) {
                if (currentPieces.containsKey(Pair(row, i).toString())) break
                movementFields.add(Pair(row, i))
                if (opposingPieces.containsKey(Pair(row, i).toString())) break
            }
        }
        if (row != 1) {
            for (i in (row - 1 downTo 1)) {
                if (currentPieces.containsKey(Pair(i, col).toString())) break
                movementFields.add(Pair(i, col))
                if (opposingPieces.containsKey(Pair(i, col).toString())) break
            }
        }
        if (row != 8) {
            for (i in (row + 1..8)) {
                if (currentPieces.containsKey(Pair(i, col).toString())) break
                movementFields.add(Pair(i, col))
                if (opposingPieces.containsKey(Pair(i, col).toString())) break
            }
        }
    }

    override fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val opposingPieces = match.pieceSets[match.currentColor]?.activePieces ?: return
        val currentPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: return

        if (col != 1) {
            for (i in (col - 1 downTo 1)) {
                if (currentPieces.containsKey(Pair(row, i).toString())) break
                threatedFields.add(Pair(row, i))
                if (opposingPieces.containsKey(Pair(row, i).toString())) break
            }
        }
        if (col != 8) {
            for (i in (col + 1..8)) {
                if (currentPieces.containsKey(Pair(row, i).toString())) break
                threatedFields.add(Pair(row, i))
                if (opposingPieces.containsKey(Pair(row, i).toString())) break
            }
        }
        if (row != 1) {
            for (i in (row - 1 downTo 1)) {
                if (currentPieces.containsKey(Pair(i, col).toString())) break
                threatedFields.add(Pair(i, col))
                if (opposingPieces.containsKey(Pair(i, col).toString())) break
            }
        }
        if (row != 8) {
            for (i in (row + 1..8)) {
                if (currentPieces.containsKey(Pair(i, col).toString())) break
                threatedFields.add(Pair(i, col))
                if (opposingPieces.containsKey(Pair(i, col).toString())) break
            }
        }
    }
}