package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceColor

class PawnMovementUtility : MovementUtility() {
    override fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val currentPieces = match.pieceSets[match.currentColor]?.activePieces ?: return
        val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: return

        when (match.currentColor) {
            PieceColor.WHITE -> {
                if (
                        !currentPieces.containsKey(Pair(row + 1, col).toString()) &&
                        !opposingPieces.containsKey(Pair(row + 1, col).toString())
                ) {
                    movementFields.add(Pair(row + 1, col))
                    if (
                            row == 2 &&
                            !currentPieces.containsKey(Pair(row + 2, col).toString()) &&
                            !opposingPieces.containsKey(Pair(row + 2, col).toString())
                    ) {
                        movementFields.add(Pair(row + 2, col))
                    }
                }
                if (opposingPieces.containsKey(Pair(row + 1, col + 1).toString())) {
                    movementFields.add(Pair(row + 1, col + 1))
                }
                if (opposingPieces.containsKey(Pair(row + 1, col - 1).toString())) {
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
            PieceColor.BLACK -> {
                if (
                        !currentPieces.containsKey(Pair(row - 1, col).toString()) &&
                        !opposingPieces.containsKey(Pair(row - 1, col).toString())
                ) {
                    movementFields.add(Pair(row - 1, col))
                    if (
                            row == 7 &&
                            !currentPieces.containsKey(Pair(row - 2, col).toString()) &&
                            !opposingPieces.containsKey(Pair(row - 2, col).toString())
                    ) {
                        movementFields.add(Pair(row - 2, col))
                    }
                }
                if (opposingPieces.containsKey(Pair(row - 1, col + 1).toString())) {
                    movementFields.add(Pair(row - 1, col + 1))
                }
                if (opposingPieces.containsKey(Pair(row - 1, col - 1).toString())) {
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

    override fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val opposingPieces = match.pieceSets[match.currentColor]?.activePieces ?: return

        when (match.currentColor.getOpposite()) {
            PieceColor.WHITE -> {
                if (opposingPieces.containsKey(Pair(row + 1, col + 1).toString())) {
                    threatedFields.add(Pair(row + 1, col + 1))
                }
                if (opposingPieces.containsKey(Pair(row + 1, col - 1).toString())) {
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
            PieceColor.BLACK -> {
                if (opposingPieces.containsKey(Pair(row - 1, col + 1).toString())) {
                    threatedFields.add(Pair(row - 1, col + 1))
                }
                if (opposingPieces.containsKey(Pair(row - 1, col - 1).toString())) {
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