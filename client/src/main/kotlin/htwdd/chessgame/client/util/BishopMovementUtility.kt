package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match

class BishopMovementUtility : MovementUtility() {
    override fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val currentPieces = match.pieceSets[match.currentColor]?.activePieces ?: return
        val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: return

        if (row != 1) {
            if (col != 1) {
                var tmpCol = col
                for (i in row - 1 downTo 1) {
                    tmpCol--
                    if (tmpCol == 0 || currentPieces.containsKey(Pair(i, tmpCol).toString())) break
                    movementFields.add(Pair(i, tmpCol))
                    if (opposingPieces.containsKey(Pair(i, tmpCol).toString())) break
                }
            }
            if (col != 8) {
                var tmpCol = col
                for (i in row - 1 downTo 1) {
                    tmpCol++
                    if (tmpCol == 9 || currentPieces.containsKey(Pair(i, tmpCol).toString())) break
                    movementFields.add(Pair(i, tmpCol))
                    if (opposingPieces.containsKey(Pair(i, tmpCol).toString())) break
                }
            }
        }
        if (row != 8) {
            if (col != 1) {
                var tmpCol = col
                for (i in row + 1..8) {
                    tmpCol--
                    if (tmpCol == 0 || currentPieces.containsKey(Pair(i, tmpCol).toString())) break
                    movementFields.add(Pair(i, tmpCol))
                    if (opposingPieces.containsKey(Pair(i, tmpCol).toString())) break
                }
            }
            if (col != 8) {
                var tmpCol = col
                for (i in row + 1..8) {
                    tmpCol++
                    if (tmpCol == 9 || currentPieces.containsKey(Pair(i, tmpCol).toString())) break
                    movementFields.add(Pair(i, tmpCol))
                    if (opposingPieces.containsKey(Pair(i, tmpCol).toString())) break
                }
            }
        }
    }

    override fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val opposingPieces = match.pieceSets[match.currentColor]?.activePieces ?: return
        val currentPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: return

        if (row != 1) {
            if (col != 1) {
                var tmpCol = col
                for (i in row - 1 downTo 1) {
                    tmpCol--
                    if (tmpCol == 0 || currentPieces.containsKey(Pair(i, tmpCol).toString())) break
                    threatedFields.add(Pair(i, tmpCol))
                    if (opposingPieces.containsKey(Pair(i, tmpCol).toString())) break
                }
            }
            if (col != 8) {
                var tmpCol = col
                for (i in row - 1 downTo 1) {
                    tmpCol++
                    if (tmpCol == 9 || currentPieces.containsKey(Pair(i, tmpCol).toString())) break
                    threatedFields.add(Pair(i, tmpCol))
                    if (opposingPieces.containsKey(Pair(i, tmpCol).toString())) break
                }
            }
        }
        if (row != 8) {
            if (col != 1) {
                var tmpCol = col
                for (i in row + 1..8) {
                    tmpCol--
                    if (tmpCol == 0 || currentPieces.containsKey(Pair(i, tmpCol).toString())) break
                    threatedFields.add(Pair(i, tmpCol))
                    if (opposingPieces.containsKey(Pair(i, tmpCol).toString())) break
                }
            }
            if (col != 8) {
                var tmpCol = col
                for (i in row + 1..8) {
                    tmpCol++
                    if (tmpCol == 9 || currentPieces.containsKey(Pair(i, tmpCol).toString())) break
                    threatedFields.add(Pair(i, tmpCol))
                    if (opposingPieces.containsKey(Pair(i, tmpCol).toString())) break
                }
            }
        }
    }
}