package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.PieceColor
import htwdd.chessgame.client.model.PieceType
import org.w3c.dom.Element
import org.w3c.dom.get
import kotlin.dom.hasClass

class ChessRuleUtility {
    companion object {
        fun getValidDropFields(image: Element): HashSet<Pair<Int, Int>> {
            val validDropFields = HashSet<Pair<Int, Int>>()
            var pieceColor = PieceColor.WHITE
            val type = image.attributes["data-type"]?.nodeValue
            val parent = image.parentElement
            val row = parent!!.attributes["data-row"]?.nodeValue?.toIntOrNull()
            val col = parent.attributes["data-col"]?.nodeValue?.toIntOrNull()

            if (image.hasClass("piece--black")) {
                pieceColor = PieceColor.BLACK
            } else if (!image.hasClass("piece--white")) {
                // todo throw error
            }

            when (type) {
                PieceType.BISCHOP.toString() -> {
                }
                PieceType.KING.toString() -> {
                }
                PieceType.KNIGHT.toString() -> {
                }
                PieceType.PAWN.toString() -> {
                    if (row != null && col != null) {
                        if (pieceColor == PieceColor.WHITE) {
                            validDropFields.add(Pair(row + 1, col))
                            // start row
                            if (row == 2) {
                                validDropFields.add(Pair(row + 2, col))
                            }
                        }

                        if (pieceColor == PieceColor.BLACK) {
                            validDropFields.add(Pair(row - 1, col))
                            // start row
                            if (row == 7) {
                                validDropFields.add(Pair(row - 2, col))
                            }
                        }
                    }
                }
                PieceType.QUEEN.toString() -> {
                }
                PieceType.ROOK.toString() -> {
                }
            }

            return validDropFields
        }
    }
}