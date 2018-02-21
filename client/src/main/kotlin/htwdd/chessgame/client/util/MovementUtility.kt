package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match

interface MovementUtility {
    fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>,
                          row: Int,
                          col: Int,
                          match: Match)

    fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>,
                          row: Int,
                          col: Int,
                          match: Match)
}