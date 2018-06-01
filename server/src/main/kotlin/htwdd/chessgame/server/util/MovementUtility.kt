package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match

/**
 * Abstract class for piece movement
 *
 * @author Felix Dimmel
 *
 * @since 1.0.0
 */
abstract class MovementUtility : Movement {
    /**
     * Filters movement fields
     * Remove fields which cause a check
     *
     * @author Felix Dimmel
     *
     * @param movementFields Hash set which was filled with movement fields (without fields where own figures stand e.g.)
     * @param row Row value of piece which should be moved
     * @param col Column value of piece which should be moved
     * @param match Match which contains the piece
     *
     * @since 1.0.0
     */
    fun getFilteredMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val unfilteredMovementFields = HashSet<Pair<Int, Int>>()
        getMovementFields(unfilteredMovementFields, row, col, match)
        movementFields.addAll(unfilteredMovementFields.filter { filterMovementFields(Pair(row, col), it, match) })
    }

    /**
     * Filter method for collection of movement fields
     *
     * @param position Position of moved piece
     * @param field Movement field of the given piece
     * @param match Match reference
     *
     * @return True if the field don't produce a check otherwise false
     */
    private fun filterMovementFields(position: Pair<Int, Int>, field: Pair<Int, Int>, match: Match): Boolean {
        val currentPieces = match.pieceSets[match.currentColor]?.activePieces
                ?: throw NullPointerException()
        val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces
                ?: throw NullPointerException()
        val copyOfCurrentPieces = HashMap(currentPieces)
        val copyOfOpposingPieces = HashMap(opposingPieces)
        val piece = currentPieces[position] ?: throw Exception("Can't find a piece at this position!")
        var result = true

        currentPieces[field] = piece
        currentPieces.remove(position)

        if (opposingPieces.containsKey(field)) opposingPieces.remove(field)
        if (CheckUtility.calcThreatedFields(match)) result = false

        currentPieces.clear()
        currentPieces.putAll(copyOfCurrentPieces)
        opposingPieces.clear()
        opposingPieces.putAll(copyOfOpposingPieces)

        return result
    }
}