package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.PieceColor
import htwdd.chessgame.client.model.PieceType
import org.w3c.dom.Element
import org.w3c.dom.get
import kotlin.browser.document
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

            if (row != null && col != null) {
                when (type) {
                    PieceType.BISCHOP.toString() -> {
                    }
                    PieceType.KING.toString() -> {
                        val field1 = document.getElementById("board--field-${row + 1}-$col")
                        val field2 = document.getElementById("board--field-${row + 1}-${col + 1}")
                        val field3 = document.getElementById("board--field-$row-${col + 1}")
                        val field4 = document.getElementById("board--field-${row - 1}-${col + 1}")
                        val field5 = document.getElementById("board--field-${row - 1}-$col")
                        val field6 = document.getElementById("board--field-${row - 1}-${col - 1}")
                        val field7 = document.getElementById("board--field-$row-${col - 1}")
                        val field8 = document.getElementById("board--field-${row + 1}-${col - 1}")

                        if (field1 != null && !field1.hasChildNodes()) validDropFields.add(Pair(row + 1, col))
                        if (field2 != null && !field2.hasChildNodes()) validDropFields.add(Pair(row + 1, col + 1))
                        if (field3 != null && !field3.hasChildNodes()) validDropFields.add(Pair(row, col + 1))
                        if (field4 != null && !field4.hasChildNodes()) validDropFields.add(Pair(row - 1, col + 1))
                        if (field5 != null && !field5.hasChildNodes()) validDropFields.add(Pair(row - 1, col))
                        if (field6 != null && !field6.hasChildNodes()) validDropFields.add(Pair(row - 1, col - 1))
                        if (field7 != null && !field7.hasChildNodes()) validDropFields.add(Pair(row, col - 1))
                        if (field8 != null && !field8.hasChildNodes()) validDropFields.add(Pair(row + 1, col - 1))
                    }
                    PieceType.KNIGHT.toString() -> {
                    }
                    PieceType.PAWN.toString() -> {
                        if (pieceColor == PieceColor.WHITE) {
                            val field = document.getElementById("board--field-${row + 1}-$col")
                            if (field != null && !field.hasChildNodes()) {
                                validDropFields.add(Pair(row + 1, col))
                                // start row
                                if (row == 2) validDropFields.add(Pair(row + 2, col))
                            }
                        }

                        if (pieceColor == PieceColor.BLACK) {
                            val field = document.getElementById("board--field-${row - 1}-$col")
                            if (field != null && !field.hasChildNodes()) {
                                validDropFields.add(Pair(row - 1, col))
                                // start row
                                if (row == 7) validDropFields.add(Pair(row - 2, col))
                            }
                        }

                    }
                    PieceType.QUEEN.toString() -> {
                    }
                    PieceType.ROOK.toString() -> {
                    }
                }
            }

            return validDropFields
        }
    }
}