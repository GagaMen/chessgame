package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.*

class SANUtility {
    companion object {
        fun validateSAN(draw: Draw, match: Match): Boolean {
            val movementFields = HashSet<Pair<Int, Int>>()
            val movementUtility = when (draw.pieceType) {
                PieceType.PAWN -> PawnMovementUtility()
                PieceType.KING -> KingMovementUtility()
                PieceType.QUEEN -> QueenMovementUtility()
                PieceType.BISHOP -> BishopMovementUtility()
                PieceType.KNIGHT -> KnightMovementUtility()
                PieceType.ROOK -> RookMovementUtility()
                else -> return false
            }

            // validate castling
            if (draw.pieceType == PieceType.KING && (draw.kingsideCastling || draw.queensideCastling)) {
                return validateCastling(draw, match)
            }

            val possibleStartFields = calcPossibleStartFields(draw, match)

            possibleStartFields.forEach {
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

        private fun validateCastling(draw: Draw, match: Match): Boolean {
            val activePieces = match.pieceSets[match.currentColor]?.activePieces ?: return false
            val kingPosition = when (match.currentColor) {
                PieceColor.WHITE -> Pair(1,5)
                PieceColor.BLACK -> Pair(8,5)
            }

            draw.startField = Field(row = kingPosition.first, column = kingPosition.second)

            if (!activePieces.containsKey(kingPosition)) return false
            else {
                val piece = activePieces[kingPosition] ?: return false
                if (piece.type != PieceType.KING) return false
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

        private fun calcPossibleStartFields(draw: Draw, match: Match): HashSet<Pair<Int, Int>> {
            val possibleStartFields = HashSet<Pair<Int, Int>>()
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

            if (startInfo.toIntOrNull() == null) { // column
                val column = startInfo.toCharArray()[0].toInt() % 96
                possibleStartFields.forEach {
                    if (it.second != column) possibleStartFields.remove(it)
                }
            } else { // row
                val row = startInfo.toInt()
                possibleStartFields.forEach {
                    if (it.first != row) possibleStartFields.remove(it)
                }
            }

            return possibleStartFields
        }
    }
}