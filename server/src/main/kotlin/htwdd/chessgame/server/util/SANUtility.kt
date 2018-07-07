package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.Draw
import htwdd.chessgame.server.model.Field
import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.PieceColor.BLACK
import htwdd.chessgame.server.model.PieceColor.WHITE
import htwdd.chessgame.server.model.PieceType
import htwdd.chessgame.server.model.PieceType.*

/**
 * Utility class to handle a String as standard algebraic notation (SAN)
 *
 * @author Felix Dimmel
 *
 * @since 1.0.0
 */
class SANUtility {
    /**
     * Static SANUtility object
     */
    companion object {
        /**
         * Validate a given draw based on his SAN code
         *
         * @author Felix Dimmel
         *
         * @param draw The draw to be added
         * @param match The match reference of the draw
         *
         * @return True if the draw is valid otherwise false
         *
         * @since 1.0.0
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
         * @author Felix Dimmel
         *
         * @param draw The draw to be added
         * @param match The match reference of the draw
         *
         * @return True if the castling is valid otherwise false
         *
         * @since 1.0.0
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
         * @author Felix Dimmel
         *
         * @param draw The draw to be added
         * @param match The match reference of the draw
         *
         * @return A hashset of all possible start fields
         *
         * @since 1.0.0
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

        /**
         * Calculate SAN string with given information from ai move
         *
         * @author Felix Dimmel
         *
         * @param startField Start position of moved piece
         * @param endField End position of moved piece
         * @param checkmate True if one player is checkmate otherwise false
         * @param check True if one player is in check otherwise false
         * @param match The match reference of the draw
         *
         * @since 1.0.0
         */
        fun calcSANFromAIMove(
                startField: Field,
                endField: Field,
                checkmate: Boolean,
                check: Boolean,
                conversionCode: String,
                match: Match
        ): String {
            val sb = StringBuilder()
            // use copy to avoid CurrentModificationException in forEach loop
            val activePieces = HashMap(match.pieceSets[match.currentColor]?.activePieces!!)
            val opposingPieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces!!
            val movedPiece = activePieces[startField.asPair()]
                    ?: throw Exception("Can't find the piece that has been moved!")
            var throwPiece = opposingPieces.containsKey(endField.asPair())
            var throwEnPassant = false

            if (match.kingsideCastling[match.currentColor]!! &&
                    movedPiece.type == KING &&
                    (endField.column - startField.column) == 2) {
                return sb.append("O-O").toString()
            }

            if (match.kingsideCastling[match.currentColor]!! &&
                    movedPiece.type == KING &&
                    (startField.column - endField.column) == 2) {
                return sb.append("O-O-O").toString()
            }


            if (movedPiece.type == PAWN && match.enPassantField?.asPair() == endField.asPair()) {
                throwEnPassant = true
                throwPiece = true
            }

            if (movedPiece.type != PAWN) sb.append(movedPiece.type.getDrawCode())

            // the run command is only a wrapper to break the inline forEach (unnecessary if the todo below is implemented)
            // todo: implement rare case if other multiple pieces can move to the end position
            // this is only possible through pawn conversion
            run breakForEach@{
                activePieces.forEach piece@{ piece ->
                    if (movedPiece.type == PieceType.PAWN || piece.value.type != movedPiece.type) return@piece
                    if (piece.value.position.row == endField.row && piece.value.position.column == endField.column) return@piece

                    val movementUtility = when (movedPiece.type) {
                        PieceType.BISHOP -> BishopMovementUtility()
                        PieceType.KING -> KingMovementUtility()
                        PieceType.KNIGHT -> KnightMovementUtility()
                        PieceType.PAWN -> PawnMovementUtility()
                        PieceType.QUEEN -> QueenMovementUtility()
                        PieceType.ROOK -> RookMovementUtility()
                    }

                    val movementFields = HashSet<Pair<Int, Int>>()
                    movementUtility.getFilteredMovementFields(movementFields, piece.value.position.row, piece.value.position.column, match)

                    movementFields.forEach field@{ field ->
                        if (field.first != endField.row || field.second != endField.column) return@field
                        if (startField.column == piece.value.position.column) sb.append("${startField.row}")
                        else sb.append("${startField.column.plus(96).toChar()}")
                        return@breakForEach
                    }
                }
            }

            if (movedPiece.type == PAWN && throwPiece) {
                sb.append("${startField.column.plus(96).toChar()}")
            }

            if (throwPiece) sb.append("x")

            sb.append("${endField.column.plus(96).toChar()}${endField.row}")

            if (conversionCode != "") sb.append(conversionCode)

            if (throwEnPassant) sb.append("e.p.")

            if (checkmate) sb.append("#")
            else if (check) sb.append("+")

            return sb.toString()
        }
    }
}