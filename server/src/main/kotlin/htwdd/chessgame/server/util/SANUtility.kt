package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Draw
import htwdd.chessgame.server.model.Field
import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.PieceColor.BLACK
import htwdd.chessgame.server.model.PieceColor.WHITE
import htwdd.chessgame.server.model.PieceType.*

/**
 * Utility class to handle a String as standard algebraic notation (SAN)
 */
class SANUtility {
    companion object {
        /**
         * Validate a given draw based on his SAN code
         *
         * @param draw The draw to be added
         * @param match The match reference of the draw
         *
         * @return True if the draw is valid otherwise false
         */
        fun validateSAN(draw: Draw, match: Match): Boolean {
            val movementFields = HashSet<Pair<Int, Int>>()
            val movementUtility = when (draw.pieceType) {
                PAWN -> PawnMovementUtility()
                KING -> KingMovementUtility()
                QUEEN -> QueenMovementUtility()
                BISHOP -> BishopMovementUtility()
                KNIGHT -> KnightMovementUtility()
                ROOK -> RookMovementUtility()
                else -> return false
            }

            // validate castling
            if (draw.pieceType == KING && (draw.kingsideCastling || draw.queensideCastling)) {
                return validateCastling(draw, match)
            }

            val possibleStartFields = calcPossibleStartFields(draw, match)

            possibleStartFields.forEach {
                movementFields.clear()
                movementUtility.getFilteredMovementFields(movementFields, it.first, it.second, match)
                if (movementFields.contains(draw.endField?.asPair())) {
                    if (
                            draw.startField == null ||
                            draw.startField?.row != it.first ||
                            draw.startField?.column != it.second
                    ) {
                        draw.startField = Field(row = it.first, column = it.second)
                    }
                    return true
                }
            }

            return false
        }

        /**
         * Validate a given draw based on his SAN code if the draw is a castling
         *
         * @param draw The draw to be added
         * @param match The match reference of the draw
         *
         * @return True if the castling is valid otherwise false
         */
        private fun validateCastling(draw: Draw, match: Match): Boolean {
            val activePieces = match.pieceSets[match.currentColor]?.activePieces
                    ?: throw NullPointerException("The HashMap of active pieces for Player ${match.currentColor} is null!")
            val kingPosition = when (match.currentColor) {
                WHITE -> Pair(1, 5)
                BLACK -> Pair(8, 5)
            }

            draw.startField = Field(row = kingPosition.first, column = kingPosition.second)

            if (!activePieces.containsKey(kingPosition)) return false
            else {
                val piece = activePieces[kingPosition] ?: throw NullPointerException("Can't get piece!")
                if (piece.type != KING) return false
            }

            val movementFields = HashSet<Pair<Int, Int>>()
            val kingMovementUtility = KingMovementUtility()

            kingMovementUtility.getFilteredMovementFields(movementFields, kingPosition.first, kingPosition.second, match)

            if (draw.kingsideCastling && !movementFields.contains(Pair(kingPosition.first, 7))) return false
            if (draw.queensideCastling && !movementFields.contains(Pair(kingPosition.first, 3))) return false

            if (draw.kingsideCastling) draw.endField = Field(row = kingPosition.first, column = 7)
            if (draw.queensideCastling) draw.endField = Field(row = kingPosition.first, column = 3)

            return true
        }

        /**
         * Calculate all possible start field based on the given piece type
         * Necessary if no start field was handed
         *
         * @param draw The draw to be added
         * @param match The match reference of the draw
         *
         * @return A hashset of all possible start fields
         */
        private fun calcPossibleStartFields(draw: Draw, match: Match): HashSet<Pair<Int, Int>> {
            var possibleStartFields = HashSet<Pair<Int, Int>>()
            val regex = "([KQBNR])?([a-h]|[1-8])?(x)?([a-h])([1-8])([QBRN])?(e\\.p\\.)?(\\+{1,2}|#)?".toRegex()
            val matchResult = regex.find(draw.drawCode) ?: return possibleStartFields

            val startInfo = matchResult.groups[2]?.value ?: ""

            if (draw.startField != null) {
                possibleStartFields.add(draw.startField!!.asPair())
            } else {
                match.pieceSets[match.currentColor]!!.activePieces.forEach {
                    if (it.value.type != draw.pieceType) return@forEach
                    possibleStartFields.add(it.key)
                }
            }

            if (startInfo == "") return possibleStartFields

            possibleStartFields = if (startInfo.toIntOrNull() == null) {
                val column = startInfo.toCharArray()[0].toInt() % 96
                possibleStartFields.filter { it.second == column }.toHashSet()
            } else {
                val row = startInfo.toInt()
                possibleStartFields.filter { it.first == row }.toHashSet()
            }

            return possibleStartFields
        }
    }
}