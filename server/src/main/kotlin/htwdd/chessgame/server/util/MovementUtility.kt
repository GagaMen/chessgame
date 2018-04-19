package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match

abstract class MovementUtility : Movement {
    fun getFilteredMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val currentPieces = match.pieceSets[match.currentColor]?.activePieces ?: throw NullPointerException()
        val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: throw NullPointerException()
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