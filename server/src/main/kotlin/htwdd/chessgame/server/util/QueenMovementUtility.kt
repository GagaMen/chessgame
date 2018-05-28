package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match

/**
 * Movement utility class for the queen piece
 * Combined the rules of bishop and rook
 *
 * @property bishop utility object to get movement and threated field of a bishop piece
 * @property root utility object to get movement and threated field of a rook piece
 */
class QueenMovementUtility : MovementUtility() {
    private val bishop = BishopMovementUtility()
    private val rook = RookMovementUtility()

    /**
     * Calculate all possible movement fields for a queen piece
     *
     * @param movementFields Hash set which was filled with movement fields
     * @param row Row value of piece which should be moved
     * @param col Column value of piece which should be moved
     * @param match Match which contains the piece
     */
    override fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        bishop.getMovementFields(movementFields, row, col, match)
        rook.getMovementFields(movementFields, row, col, match)
    }

    /**
     * Calculate all possible threated fields for a queen piece
     *
     * @param threatedFields Hash set which was filled with threated fields
     * @param row Row value of piece from which the threat emanate
     * @param col Column value of piece from which the threat emanate
     * @param match Match which contains the piece
     */
    override fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        bishop.getThreadedFields(threatedFields, row, col, match)
        rook.getThreadedFields(threatedFields, row, col, match)
    }
}