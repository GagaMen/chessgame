package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceType
import org.w3c.dom.get
import kotlin.browser.document

class CheckUtility {
    companion object {
        private var threatedFields = HashSet<Pair<Int, Int>>()
        private val bishop = BishopMovementUtility()
        private val king = KingMovementUtility()
        private val knight = KnightMovementUtility()
        private val pawn = PawnMovementUtility()
        private val queen = QueenMovementUtility()
        private val rook = RookMovementUtility()

        fun calcThreatedFields(match: Match) {
            val pieceColor = match.currentColor.getOpposite()
            val pieceSet = match.pieceSets[pieceColor]

            threatedFields.clear()

            pieceSet?.activePieces?.forEach {
                when (it.value.type) {
                    PieceType.BISHOP -> {
                        bishop.getThreadedFields(threatedFields, it.key.first, it.key.second, pieceColor)
                    }
                    PieceType.PAWN -> {
                        pawn.getThreadedFields(threatedFields, it.key.first, it.key.second, pieceColor, match)
                    }
                    PieceType.KING -> {
                        king.getThreadedFields(threatedFields, it.key.first, it.key.second, pieceColor, match)
                    }
                    PieceType.QUEEN -> {
                        queen.getThreadedFields(threatedFields, it.key.first, it.key.second, pieceColor)
                    }
                    PieceType.KNIGHT -> {
                        knight.getThreadedFields(threatedFields, it.key.first, it.key.second, pieceColor)
                    }
                    PieceType.ROOK -> {
                        rook.getThreadedFields(threatedFields, it.key.first, it.key.second, pieceColor)
                    }
                }
            }

            threatedFields.forEach {
                val field = document.getElementById("board--field-${it.first}-${it.second}")
                val image = field?.firstElementChild ?: return@forEach
                if (image.attributes["data-type"]?.value == PieceType.KING.toString()) {
                    println("${match.currentColor} is in check")
                    return
                }
            }
        }
    }
}