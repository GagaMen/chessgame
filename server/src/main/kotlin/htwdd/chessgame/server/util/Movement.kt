package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match

/**
 * Interface for piece movement
 *
 * @author Felix Dimmel
 *
 * @since 1.0.0
 */
interface Movement {
    /**
     * Calculate all possible movement fields for a special piece
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
    fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>,
                          row: Int,
                          col: Int,
                          match: Match)

    /**
     * Calculate all possible threated fields for a special piece
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
    fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>,
                          row: Int,
                          col: Int,
                          match: Match)
}