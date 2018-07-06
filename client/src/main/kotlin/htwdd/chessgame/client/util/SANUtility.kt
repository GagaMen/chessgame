package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Field
import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceType

class SANUtility {
    companion object {
        fun calc(pieceType: PieceType,
                 start: Field,
                 end: Field,
                 match: Match,
                 throwPiece: Boolean = false,
                 throwEnPassant: Boolean = false,
                 kingsideCastling: Boolean = false,
                 queensideCastling: Boolean = false,
                 conversion: PieceType? = null): String? {
            val sb = StringBuilder()

            if (kingsideCastling) sb.append("O-O")
            if (queensideCastling) sb.append("O-O-O")

            if (!kingsideCastling && !queensideCastling) {
                if (pieceType != PieceType.PAWN) sb.append(pieceType.getDrawCode())

                val activePieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: return null
                activePieces.forEach piece@{ piece ->
                    if (pieceType == PieceType.PAWN || piece.value.type != pieceType) return@piece
                    if (piece.value.position.row == end.row && piece.value.position.column == end.column) return@piece

                    val movementUtility = when (pieceType) {
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
                        if (field.first != end.row || field.second != end.column) return@field
                        if (start.column == piece.value.position.column) sb.append("${start.row}")
                        else sb.append("${start.column.plus(96).toChar()}")
                    }
                }

                if (pieceType == PieceType.PAWN && throwPiece) {
                    sb.append("${start.column.plus(96).toChar()}")
                }

                if (throwPiece) sb.append("x")

                sb.append("${end.column.plus(96).toChar()}${end.row}")

                if (conversion != null) sb.append(conversion.getDrawCode())

                if (throwEnPassant) sb.append("e.p.")
            }

            val matchAfterSimulateMove = simulateMove(
                    start,
                    end,
                    match,
                    throwPiece,
                    throwEnPassant,
                    kingsideCastling,
                    queensideCastling,
                    conversion
            )

            if (CheckUtility.checkmate(matchAfterSimulateMove)) sb.append("#")
            else if (CheckUtility.calcThreatedFields(matchAfterSimulateMove)) sb.append("+")

            return sb.toString()
        }

        private fun simulateMove(
                start: Field,
                end: Field,
                match: Match,
                throwPiece: Boolean,
                throwEnPassant: Boolean,
                kingsideCastling: Boolean,
                queensideCastling: Boolean,
                conversion: PieceType?

        ): Match {
            val copyOfMatch = match.deepCopy()
            val activePieces = copyOfMatch.pieceSets[copyOfMatch.currentColor]?.activePieces!!
            val opposingPieces = copyOfMatch.pieceSets[copyOfMatch.currentColor.getOpposite()]?.activePieces!!
            val piece = activePieces[start.asPair().toString()]!!

            if (conversion != null) piece.type = conversion

            activePieces.remove(start.asPair().toString())
            activePieces[end.asPair().toString()] = piece

            if (kingsideCastling) {
                val rookPosition = Pair(end.row, 8)
                val rook = activePieces[rookPosition.toString()]!!
                activePieces.remove(rookPosition.toString())
                activePieces[Pair(end.row, 6).toString()] = rook
            }

            if (queensideCastling) {
                val rookPosition = Pair(end.row, 1)
                val rook = activePieces[rookPosition.toString()]!!
                activePieces.remove(rookPosition.toString())
                activePieces[Pair(end.row, 4).toString()] = rook
            }

            if (throwPiece) {
                opposingPieces.remove(end.asPair().toString())
            }

            if (throwEnPassant) {
                val removePosition = when (end.row) {
                    3 -> Pair(4, end.column)
                    6 -> Pair(5, end.column)
                    else -> null
                }

                if (removePosition != null) {
                    opposingPieces.remove(removePosition.toString())
                }
            }

            copyOfMatch.currentColor = copyOfMatch.currentColor.getOpposite()

            return copyOfMatch
        }
    }
}