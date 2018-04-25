package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceColor

class KingMovementUtility : MovementUtility() {
    override fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val currentPieces = match.pieceSets[match.currentColor]?.activePieces ?: return

        if (row + 1 != 9 && !currentPieces.containsKey(Pair(row + 1, col).toString())) {
            movementFields.add(Pair(row + 1, col))
        }
        if (row + 1 != 9 && col + 1 != 9 && !currentPieces.containsKey(Pair(row + 1, col + 1).toString())) {
            movementFields.add(Pair(row + 1, col + 1))
        }
        if (col + 1 != 9 && !currentPieces.containsKey(Pair(row, col + 1).toString())) {
            movementFields.add(Pair(row, col + 1))
        }
        if (row - 1 != 0 && col + 1 != 9 && !currentPieces.containsKey(Pair(row - 1, col + 1).toString())) {
            movementFields.add(Pair(row - 1, col + 1))
        }
        if (row - 1 != 0 && !currentPieces.containsKey(Pair(row - 1, col).toString())) {
            movementFields.add(Pair(row - 1, col))
        }
        if (row - 1 != 0 && col - 1 != 0 && !currentPieces.containsKey(Pair(row - 1, col - 1).toString())) {
            movementFields.add(Pair(row - 1, col - 1))
        }
        if (col - 1 != 0 && !currentPieces.containsKey(Pair(row, col - 1).toString())) {
            movementFields.add(Pair(row, col - 1))
        }
        if (row + 1 != 9 && col - 1 != 0 && !currentPieces.containsKey(Pair(row + 1, col - 1).toString())) {
            movementFields.add(Pair(row + 1, col - 1))
        }

        when (match.currentColor) {
            PieceColor.WHITE -> {
                if (match.whiteCastlingKingSide &&
                        !currentPieces.containsKey(Pair(row, col + 1).toString()) &&
                        !currentPieces.containsKey(Pair(row, col + 2).toString())) {
                    movementFields.add(Pair(row, col + 2))
                }
                if (match.whiteCastlingQueenSide &&
                        !currentPieces.containsKey(Pair(row, col - 1).toString()) &&
                        !currentPieces.containsKey(Pair(row, col - 2).toString()) &&
                        !currentPieces.containsKey(Pair(row, col - 3).toString())) {
                    movementFields.add(Pair(row, col - 2))
                }
            }
            PieceColor.BLACK -> {
                if (match.blackCastlingKingSide &&
                        !currentPieces.containsKey(Pair(row, col + 1).toString()) &&
                        !currentPieces.containsKey(Pair(row, col + 2).toString())) {
                    movementFields.add(Pair(row, col + 2))
                }
                if (match.blackCastlingQueenSide &&
                        !currentPieces.containsKey(Pair(row, col - 1).toString()) &&
                        !currentPieces.containsKey(Pair(row, col - 2).toString()) &&
                        !currentPieces.containsKey(Pair(row, col - 3).toString())) {
                    movementFields.add(Pair(row, col - 2))
                }
            }
        }
    }

    override fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: return

        if (row + 1 != 9 && !opposingPieces.containsKey(Pair(row + 1, col).toString())) {
            threatedFields.add(Pair(row + 1, col))
        }
        if (row + 1 != 9 && col + 1 != 9 && !opposingPieces.containsKey(Pair(row + 1, col + 1).toString())) {
            threatedFields.add(Pair(row + 1, col + 1))
        }
        if (col + 1 != 9 && !opposingPieces.containsKey(Pair(row, col + 1).toString())) {
            threatedFields.add(Pair(row, col + 1))
        }
        if (row - 1 != 0 && col + 1 != 9 && !opposingPieces.containsKey(Pair(row - 1, col + 1).toString())) {
            threatedFields.add(Pair(row - 1, col + 1))
        }
        if (row - 1 != 0 && !opposingPieces.containsKey(Pair(row - 1, col).toString())) {
            threatedFields.add(Pair(row - 1, col))
        }
        if (row - 1 != 0 && col - 1 != 0 && !opposingPieces.containsKey(Pair(row - 1, col - 1).toString())) {
            threatedFields.add(Pair(row - 1, col - 1))
        }
        if (col - 1 != 0 && !opposingPieces.containsKey(Pair(row, col - 1).toString())) {
            threatedFields.add(Pair(row, col - 1))
        }
        if (row + 1 != 9 && col - 1 != 0 && !opposingPieces.containsKey(Pair(row + 1, col - 1).toString())) {
            threatedFields.add(Pair(row + 1, col - 1))
        }
    }
}