package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match

/**
 * Movement utility class for the knight piece
 *
 * @author Felix Dimmel
 *
 * @since 1.0.0
 */
class KnightMovementUtility : MovementUtility() {
    /**
     * Calculate all possible movement fields for a knight piece
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

    /**
     * Calculate all possible threated fields for a knight piece
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
        val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces
                ?: throw NullPointerException()

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