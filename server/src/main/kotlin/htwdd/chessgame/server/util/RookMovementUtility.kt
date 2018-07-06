package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match

/**
 * Movement utility class for the rook piece
 *
 * @author Felix Dimmel
 *
 * @since 1.0.0
 */
class RookMovementUtility : MovementUtility() {
    /**
     * Calculate all possible movement fields for a rook piece
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

    /**
     * Calculate all possible threated fields for a rook piece
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