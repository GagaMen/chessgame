package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match

interface Movement {
    fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>,
                          row: Int,
                          col: Int,
                          match: Match)

    fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>,
                          row: Int,
                          col: Int,
                          match: Match)
}