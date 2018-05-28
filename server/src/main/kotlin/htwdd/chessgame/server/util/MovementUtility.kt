package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match

/**
 * Abstract class for piece movement
 */
abstract class MovementUtility : Movement {
    /**
     * Filters movement fields
     * Remove fields which cause a check
     *
     * @param movementFields Hash set which was filled with movement fields (without fields where own figures stand e.g.)
     * @param row Row value of piece which should be moved
     * @param col Column value of piece which should be moved
     * @param match Match which contains the piece
     */
    fun getFilteredMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val currentPieces = match.pieceSets[match.currentColor]?.activePieces
                ?: throw NullPointerException()
        val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces
                ?: throw NullPointerException()
        val copyOfCurrentPieces = HashMap(currentPieces)
        val copyOfOpposingPieces = HashMap(opposingPieces)
        val field = Pair(row, col)

        getMovementFields(movementFields, row, col, match)

        movementFields.forEach {
            val piece = currentPieces[field] ?: return@forEach

            currentPieces[it] = piece
            currentPieces.remove(field)

            if (opposingPieces.containsKey(it)) opposingPieces.remove(it)
            if (CheckUtility.calcThreatedFields(match)) movementFields.remove(it)

            currentPieces.clear()
            currentPieces.putAll(copyOfCurrentPieces)
            opposingPieces.putAll(copyOfOpposingPieces)
        }
    }
}