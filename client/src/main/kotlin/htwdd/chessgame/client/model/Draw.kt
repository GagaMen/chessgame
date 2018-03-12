package htwdd.chessgame.client.model

import htwdd.chessgame.client.util.*
import kotlinx.serialization.Serializable

@Serializable
data class Draw(var id: Int = 0,
                val color: PieceColor,
                val pieceType: PieceType,
                val start: Field,
                val end: Field,
                private val match: Match,
                private val throwPiece: Boolean = false,
                private val throwEnPassant: Boolean = false,
                private val kingsideCastling: Boolean = false,
                private val queensideCastling: Boolean = false,
                var drawCode: String = "") {

    fun setDrawCode() {
        val sb = StringBuilder()

        if (kingsideCastling) sb.append("O-O")
        if (queensideCastling) sb.append("O-O-O")

        if (!kingsideCastling && !queensideCastling) {
            if (pieceType != PieceType.PAWN) sb.append(pieceType.getDrawCode())

            val activePieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: return
            activePieces.forEach piece@ { piece ->
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

                movementFields.forEach field@ { field ->
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

            if (throwEnPassant) sb.append("e.p.")

            if (match.check[match.currentColor]!!) sb.append("+")
            if (match.checkmate) sb.append("++")
        }

        drawCode = sb.toString()
    }
}