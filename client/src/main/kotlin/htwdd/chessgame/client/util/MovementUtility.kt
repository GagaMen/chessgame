package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match

abstract class MovementUtility : Movement {
    fun getFilteredMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val currentPieces = match.pieceSets[match.currentColor]?.activePieces ?: return
        val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: return
        val copyOfCurrentPieces = HashMap(currentPieces)
        val copyOfOpposingPieces = HashMap(opposingPieces)
        val field = Pair(row, col)

        getMovementFields(movementFields, row, col, match)

        movementFields.forEach {
            val piece = currentPieces[field.toString()] ?: return@forEach

            currentPieces[it.toString()] = piece
            currentPieces.remove(field.toString())

            if (opposingPieces.containsKey(it.toString())) opposingPieces.remove(it.toString())
            if (CheckUtility.calcThreatedFields(match)) movementFields.remove(it)

            currentPieces.clear()
            currentPieces.putAll(copyOfCurrentPieces)
            opposingPieces.putAll(copyOfOpposingPieces)
        }
    }
}