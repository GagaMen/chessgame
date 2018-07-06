package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.PieceColor.BLACK
import htwdd.chessgame.server.model.PieceColor.WHITE

/**
 * Movement utility class for the pawn piece
 *
 * @author Felix Dimmel
 *
 * @since 1.0.0
 */
class PawnMovementUtility : MovementUtility() {
    /**
     * Calculate all possible movement fields for a pawn piece
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

        when (match.currentColor) {
            WHITE -> {
                if (
                        !currentPieces.containsKey(Pair(row + 1, col)) &&
                        !opposingPieces.containsKey(Pair(row + 1, col))
                ) {
                    movementFields.add(Pair(row + 1, col))
                    if (
                            row == 2 &&
                            !currentPieces.containsKey(Pair(row + 2, col)) &&
                            !opposingPieces.containsKey(Pair(row + 2, col))
                    ) {
                        movementFields.add(Pair(row + 2, col))
                    }
                }
                if (opposingPieces.containsKey(Pair(row + 1, col + 1))) {
                    movementFields.add(Pair(row + 1, col + 1))
                }
                if (opposingPieces.containsKey(Pair(row + 1, col - 1))) {
                    movementFields.add(Pair(row + 1, col - 1))
                }
                if (match.enPassantField != null && match.enPassantField?.row == row + 1) {
                    if (match.enPassantField?.column == col + 1) {
                        movementFields.add(Pair(row + 1, col + 1))
                    }
                    if (match.enPassantField?.column == col - 1) {
                        movementFields.add(Pair(row + 1, col - 1))
                    }
                }
            }
            BLACK -> {
                if (
                        !currentPieces.containsKey(Pair(row - 1, col)) &&
                        !opposingPieces.containsKey(Pair(row - 1, col))
                ) {
                    movementFields.add(Pair(row - 1, col))
                    if (
                            row == 7 &&
                            !currentPieces.containsKey(Pair(row - 2, col)) &&
                            !opposingPieces.containsKey(Pair(row - 2, col))
                    ) {
                        movementFields.add(Pair(row - 2, col))
                    }
                }
                if (opposingPieces.containsKey(Pair(row - 1, col + 1))) {
                    movementFields.add(Pair(row - 1, col + 1))
                }
                if (opposingPieces.containsKey(Pair(row - 1, col - 1))) {
                    movementFields.add(Pair(row - 1, col - 1))
                }
                if (match.enPassantField != null && match.enPassantField?.row == row - 1) {
                    if (match.enPassantField?.column == col + 1) {
                        movementFields.add(Pair(row - 1, col + 1))
                    }
                    if (match.enPassantField?.column == col - 1) {
                        movementFields.add(Pair(row - 1, col - 1))
                    }
                }
            }
        }
    }

    /**
     * Calculate all possible threated fields for a pawn piece
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

        when (match.currentColor.getOpposite()) {
            WHITE -> {
                if (opposingPieces.containsKey(Pair(row + 1, col + 1))) {
                    threatedFields.add(Pair(row + 1, col + 1))
                }
                if (opposingPieces.containsKey(Pair(row + 1, col - 1))) {
                    threatedFields.add(Pair(row + 1, col - 1))
                }
                if (match.enPassantField != null && match.enPassantField?.row == row + 1) {
                    if (match.enPassantField?.column == col + 1) {
                        threatedFields.add(Pair(row + 1, col + 1))
                    }
                    if (match.enPassantField?.column == col - 1) {
                        threatedFields.add(Pair(row + 1, col - 1))
                    }
                }
            }
            BLACK -> {
                if (opposingPieces.containsKey(Pair(row - 1, col + 1))) {
                    threatedFields.add(Pair(row - 1, col + 1))
                }
                if (opposingPieces.containsKey(Pair(row - 1, col - 1))) {
                    threatedFields.add(Pair(row - 1, col - 1))
                }
                if (match.enPassantField != null && match.enPassantField?.row == row - 1) {
                    if (match.enPassantField?.column == col + 1) {
                        threatedFields.add(Pair(row - 1, col + 1))
                    }
                    if (match.enPassantField?.column == col - 1) {
                        threatedFields.add(Pair(row - 1, col - 1))
                    }
                }
            }
        }
    }
}