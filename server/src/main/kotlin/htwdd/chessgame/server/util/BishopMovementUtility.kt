package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match

/**
 * Movement utility class for the bishop piece
 *
 * @author Felix Dimmel
 *
 * @since 1.0.0
 */
class BishopMovementUtility : MovementUtility() {
    /**
     * Calculate all possible movement fields for a bishop piece
     *
     * @author Felix Dimmel
     *
     * @param movementFields Hash set which was filled with movement fields
     * @param row Row value of piece which should be moved
     * @param col Column value of piece which should be moved
     * @param match Match which contains the piece
     *
     * @since 1.0.0
     */
    override fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val currentPieces = match.pieceSets[match.currentColor]?.activePieces
                ?: throw NullPointerException()
        val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces
                ?: throw NullPointerException()

        if (row != 1) {
            if (col != 1) {
                var tmpCol = col
                for (i in row - 1 downTo 1) {
                    tmpCol--
                    if (tmpCol == 0 || currentPieces.containsKey(Pair(i, tmpCol))) break
                    movementFields.add(Pair(i, tmpCol))
                    if (opposingPieces.containsKey(Pair(i, tmpCol))) break
                }
            }
            if (col != 8) {
                var tmpCol = col
                for (i in row - 1 downTo 1) {
                    tmpCol++
                    if (tmpCol == 9 || currentPieces.containsKey(Pair(i, tmpCol))) break
                    movementFields.add(Pair(i, tmpCol))
                    if (opposingPieces.containsKey(Pair(i, tmpCol))) break
                }
            }
        }
        if (row != 8) {
            if (col != 1) {
                var tmpCol = col
                for (i in row + 1..8) {
                    tmpCol--
                    if (tmpCol == 0 || currentPieces.containsKey(Pair(i, tmpCol))) break
                    movementFields.add(Pair(i, tmpCol))
                    if (opposingPieces.containsKey(Pair(i, tmpCol))) break
                }
            }
            if (col != 8) {
                var tmpCol = col
                for (i in row + 1..8) {
                    tmpCol++
                    if (tmpCol == 9 || currentPieces.containsKey(Pair(i, tmpCol))) break
                    movementFields.add(Pair(i, tmpCol))
                    if (opposingPieces.containsKey(Pair(i, tmpCol))) break
                }
            }
        }
    }

    /**
     * Calculate all possible threated fields for a bishop piece
     *
     * @author Felix Dimmel
     *
     * @param threatedFields Hash set which was filled with threated fields
     * @param row Row value of piece from which the threat emanate
     * @param col Column value of piece from which the threat emanate
     * @param match Match which contains the piece
     *
     * @since 1.0.0
     */
    override fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val opposingPieces = match.pieceSets[match.currentColor]?.activePieces
                ?: throw NullPointerException()
        val currentPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces
                ?: throw NullPointerException()

        if (row != 1) {
            if (col != 1) {
                var tmpCol = col
                for (i in row - 1 downTo 1) {
                    tmpCol--
                    if (tmpCol == 0 || currentPieces.containsKey(Pair(i, tmpCol))) break
                    threatedFields.add(Pair(i, tmpCol))
                    if (opposingPieces.containsKey(Pair(i, tmpCol))) break
                }
            }
            if (col != 8) {
                var tmpCol = col
                for (i in row - 1 downTo 1) {
                    tmpCol++
                    if (tmpCol == 9 || currentPieces.containsKey(Pair(i, tmpCol))) break
                    threatedFields.add(Pair(i, tmpCol))
                    if (opposingPieces.containsKey(Pair(i, tmpCol))) break
                }
            }
        }
        if (row != 8) {
            if (col != 1) {
                var tmpCol = col
                for (i in row + 1..8) {
                    tmpCol--
                    if (tmpCol == 0 || currentPieces.containsKey(Pair(i, tmpCol))) break
                    threatedFields.add(Pair(i, tmpCol))
                    if (opposingPieces.containsKey(Pair(i, tmpCol))) break
                }
            }
            if (col != 8) {
                var tmpCol = col
                for (i in row + 1..8) {
                    tmpCol++
                    if (tmpCol == 9 || currentPieces.containsKey(Pair(i, tmpCol))) break
                    threatedFields.add(Pair(i, tmpCol))
                    if (opposingPieces.containsKey(Pair(i, tmpCol))) break
                }
            }
        }
    }
}