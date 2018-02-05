package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.PieceColor
import htwdd.chessgame.client.model.PieceType
import org.w3c.dom.Element
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.dom.hasClass

class ChessRuleUtility {
    companion object {
        private val validDropFields = HashSet<Pair<Int, Int>>()

        fun getValidDropFields(image: Element): HashSet<Pair<Int, Int>> {
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
                    PieceType.BISHOP.toString() -> bishop(row, col, pieceColor)
                    PieceType.KING.toString() -> king(row, col, pieceColor)
                    PieceType.KNIGHT.toString() -> knight(row, col, pieceColor)
                    PieceType.PAWN.toString() -> pawn(row, col, pieceColor)
                    PieceType.QUEEN.toString() -> queen(row, col, pieceColor)
                    PieceType.ROOK.toString() -> rook(row, col, pieceColor)
                }
            }

            return validDropFields
        }

        private fun bishop(row: Int, col: Int, pieceColor: PieceColor) {
            //top
            if (row != 1) {
                //left
                if (col != 1) {
                    var tmpCol = col
                    for (i in row - 1 downTo 1) {
                        tmpCol--
                        if (tmpCol != 0) { // out of board
                            val field = document.getElementById("board--field-$i-$tmpCol")
                            if (field != null) {
                                if (field.hasChildNodes()) {
                                    val child = field.firstElementChild
                                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                                        validDropFields.add(Pair(i, tmpCol))
                                        break
                                    } else {
                                        break
                                    }
                                } else {
                                    validDropFields.add(Pair(i, tmpCol))
                                }
                            }
                            else break
                        } else {
                            break
                        }
                    }
                }
                //right
                if (col != 8) {
                    var tmpCol = col
                    for (i in row - 1 downTo 1) {
                        tmpCol++
                        if (tmpCol != 9) { // out of board
                            val field = document.getElementById("board--field-$i-$tmpCol")
                            if (field != null) {
                                if (field.hasChildNodes()) {
                                    val child = field.firstElementChild
                                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                                        validDropFields.add(Pair(i, tmpCol))
                                        break
                                    } else {
                                        break
                                    }
                                } else {
                                    validDropFields.add(Pair(i, tmpCol))
                                }
                            }
                            else break
                        } else {
                            break
                        }
                    }
                }
            }

            //bottom
            if (row != 8) {
                //left
                if (col != 1) {
                    var tmpCol = col
                    for (i in row + 1..8) {
                        tmpCol--
                        if (tmpCol != 0) { // out of board
                            val field = document.getElementById("board--field-$i-$tmpCol")
                            if (field != null) {
                                if (field.hasChildNodes()) {
                                    val child = field.firstElementChild
                                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                                        validDropFields.add(Pair(i, tmpCol))
                                        break
                                    } else {
                                        break
                                    }
                                } else {
                                    validDropFields.add(Pair(i, tmpCol))
                                }
                            }
                            else break
                        } else {
                            break
                        }
                    }
                }
                //right
                if (col != 8) {
                    var tmpCol = col
                    for (i in row + 1..8) {
                        tmpCol++
                        if (tmpCol != 9) { // out of board
                            val field = document.getElementById("board--field-$i-$tmpCol")
                            if (field != null) {
                                if (field.hasChildNodes()) {
                                    val child = field.firstElementChild
                                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                                        validDropFields.add(Pair(i, tmpCol))
                                        break
                                    } else {
                                        break
                                    }
                                } else {
                                    validDropFields.add(Pair(i, tmpCol))
                                }
                            }
                            else break
                        } else {
                            break
                        }
                    }
                }
            }
        }

        private fun king(row: Int, col: Int, pieceColor: PieceColor) {
            val field1 = document.getElementById("board--field-${row + 1}-$col")
            val field2 = document.getElementById("board--field-${row + 1}-${col + 1}")
            val field3 = document.getElementById("board--field-$row-${col + 1}")
            val field4 = document.getElementById("board--field-${row - 1}-${col + 1}")
            val field5 = document.getElementById("board--field-${row - 1}-$col")
            val field6 = document.getElementById("board--field-${row - 1}-${col - 1}")
            val field7 = document.getElementById("board--field-$row-${col - 1}")
            val field8 = document.getElementById("board--field-${row + 1}-${col - 1}")

            if (field1 != null) {
                if (field1.hasChildNodes()) {
                    val child = field1.firstElementChild
                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                        validDropFields.add(Pair(row + 1, col))
                    }
                } else {
                    validDropFields.add(Pair(row + 1, col))
                }
            }
            if (field2 != null) {
                if (field2.hasChildNodes()) {
                    val child = field2.firstElementChild
                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                        validDropFields.add(Pair(row + 1, col + 1))
                    }
                } else {
                    validDropFields.add(Pair(row + 1, col + 1))
                }
            }
            if (field3 != null) {
                if (field3.hasChildNodes()) {
                    val child = field3.firstElementChild
                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                        validDropFields.add(Pair(row, col + 1))
                    }
                } else {
                    validDropFields.add(Pair(row, col + 1))
                }
            }
            if (field4 != null) {
                if (field4.hasChildNodes()) {
                    val child = field4.firstElementChild
                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                        validDropFields.add(Pair(row - 1, col + 1))
                    }
                } else {
                    validDropFields.add(Pair(row - 1, col + 1))
                }
            }
            if (field5 != null) {
                if (field5.hasChildNodes()) {
                    val child = field5.firstElementChild
                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                        validDropFields.add(Pair(row - 1, col))
                    }
                } else {
                    validDropFields.add(Pair(row - 1, col))
                }
            }
            if (field6 != null) {
                if (field6.hasChildNodes()) {
                    val child = field6.firstElementChild
                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                        validDropFields.add(Pair(row - 1, col - 1))
                    }
                } else {
                    validDropFields.add(Pair(row - 1, col - 1))
                }
            }
            if (field7 != null) {
                if (field7.hasChildNodes()) {
                    val child = field7.firstElementChild
                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                        validDropFields.add(Pair(row, col - 1))
                    }
                } else {
                    validDropFields.add(Pair(row, col - 1))
                }
            }
            if (field8 != null) {
                if (field8.hasChildNodes()) {
                    val child = field8.firstElementChild
                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                        validDropFields.add(Pair(row + 1, col - 1))
                    }
                } else {
                    validDropFields.add(Pair(row + 1, col - 1))
                }

            }
            // todo remove fields in which the king is in check
            // todo implements rule "rochade"
        }

        private fun knight(row: Int, col: Int, pieceColor: PieceColor) {
            val field1 = document.getElementById("board--field-${row + 2}-${col + 1}")
            val field2 = document.getElementById("board--field-${row + 1}-${col + 2}")
            val field3 = document.getElementById("board--field-${row - 1}-${col + 2}")
            val field4 = document.getElementById("board--field-${row - 2}-${col + 1}")
            val field5 = document.getElementById("board--field-${row - 2}-${col - 1}")
            val field6 = document.getElementById("board--field-${row - 1}-${col - 2}")
            val field7 = document.getElementById("board--field-${row + 1}-${col - 2}")
            val field8 = document.getElementById("board--field-${row + 2}-${col - 1}")

            if (field1 != null) {
                if (field1.hasChildNodes()) {
                    val child = field1.firstElementChild
                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                        validDropFields.add(Pair(row + 2, col + 1))
                    }
                } else {
                    validDropFields.add(Pair(row + 2, col + 1))
                }
            }
            if (field2 != null) {
                if (field2.hasChildNodes()) {
                    val child = field2.firstElementChild
                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                        validDropFields.add(Pair(row + 1, col + 2))
                    }
                } else {
                    validDropFields.add(Pair(row + 1, col + 2))
                }
            }
            if (field3 != null) {
                if (field3.hasChildNodes()) {
                    val child = field3.firstElementChild
                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                        validDropFields.add(Pair(row - 1, col + 2))
                    }
                } else {
                    validDropFields.add(Pair(row - 1, col + 2))
                }
            }
            if (field4 != null) {
                if (field4.hasChildNodes()) {
                    val child = field4.firstElementChild
                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                        validDropFields.add(Pair(row - 2, col + 1))
                    }
                } else {
                    validDropFields.add(Pair(row - 2, col + 1))
                }
            }
            if (field5 != null) {
                if (field5.hasChildNodes()) {
                    val child = field5.firstElementChild
                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                        validDropFields.add(Pair(row - 2, col - 1))
                    }
                } else {
                    validDropFields.add(Pair(row - 2, col - 1))
                }
            }
            if (field6 != null) {
                if (field6.hasChildNodes()) {
                    val child = field6.firstElementChild
                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                        validDropFields.add(Pair(row - 1, col - 2))
                    }
                } else {
                    validDropFields.add(Pair(row - 1, col - 2))
                }
            }
            if (field7 != null) {
                if (field7.hasChildNodes()) {
                    val child = field7.firstElementChild
                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                        validDropFields.add(Pair(row + 1, col - 2))
                    }
                } else {
                    validDropFields.add(Pair(row + 1, col - 2))
                }
            }
            if (field8 != null) {
                if (field8.hasChildNodes()) {
                    val child = field8.firstElementChild
                    if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                            (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                        validDropFields.add(Pair(row + 2, col - 1))
                    }
                } else {
                    validDropFields.add(Pair(row + 2, col - 1))
                }
            }
        }

        private fun pawn(row: Int, col: Int, pieceColor: PieceColor) {
            when (pieceColor) {
                PieceColor.WHITE -> {
                    val field1 = document.getElementById("board--field-${row + 1}-$col")
                    val field2 = document.getElementById("board--field-${row + 2}-$col")
                    val field3 = document.getElementById("board--field-${row + 1}-${col + 1}")
                    val field4 = document.getElementById("board--field-${row + 1}-${col - 1}")

                    if (field1 != null && !field1.hasChildNodes()) {
                        validDropFields.add(Pair(row + 1, col))
                        if (row == 2 && field2 != null && !field2.hasChildNodes()) {
                            validDropFields.add(Pair(row + 2, col))
                        }
                    }
                    if (field3 != null && field3.hasChildNodes()) {
                        val child = field3.firstElementChild
                        if (child!!.hasClass("piece--black")) {
                            validDropFields.add(Pair(row + 1, col + 1))
                        }
                    }
                    if (field4 != null && field4.hasChildNodes()) {
                        val child = field4.firstElementChild
                        if (child!!.hasClass("piece--black")) {
                            validDropFields.add(Pair(row + 1, col - 1))
                        }
                    }
                    // todo implements rule "en passant"
                }
                PieceColor.BLACK -> {
                    val field1 = document.getElementById("board--field-${row - 1}-$col")
                    val field2 = document.getElementById("board--field-${row - 2}-$col")
                    val field3 = document.getElementById("board--field-${row - 1}-${col + 1}")
                    val field4 = document.getElementById("board--field-${row - 1}-${col - 1}")

                    if (field1 != null && !field1.hasChildNodes()) {
                        validDropFields.add(Pair(row - 1, col))
                        if (row == 7 && field2 != null && !field2.hasChildNodes()) {
                            validDropFields.add(Pair(row - 2, col))
                        }
                    }
                    if (field3 != null && field3.hasChildNodes()) {
                        val child = field3.firstElementChild
                        if (child!!.hasClass("piece--white")) {
                            validDropFields.add(Pair(row - 1, col + 1))
                        }
                    }
                    if (field4 != null && field4.hasChildNodes()) {
                        val child = field4.firstElementChild
                        if (child!!.hasClass("piece--white")) {
                            validDropFields.add(Pair(row - 1, col - 1))
                        }
                    }
                    // todo implements rule "en passant"
                }
            }
        }

        private fun queen(row: Int, col: Int, pieceColor: PieceColor) {
            bishop(row, col, pieceColor)
            rook(row, col, pieceColor)
        }

        private fun rook(row: Int, col: Int, pieceColor: PieceColor) {
            //horizontal left
            if (col != 1) {
                for (i in (col - 1 downTo 1)) {
                    val field = document.getElementById("board--field-$row-$i")
                    if (field != null) {
                        if (field.hasChildNodes()) {
                            val child = field.firstElementChild
                            if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                                    (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                                validDropFields.add(Pair(row, i))
                            }
                            break
                        } else {
                            validDropFields.add(Pair(row, i))
                        }
                    }
                    else break
                }
            }
            //horizontal right
            if (col != 8) {
                for (i in (col + 1..8)) {
                    val field = document.getElementById("board--field-$row-$i")
                    if (field != null) {
                        if (field.hasChildNodes()) {
                            val child = field.firstElementChild
                            if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                                    (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                                validDropFields.add(Pair(row, i))
                            }
                            break
                        } else {
                            validDropFields.add(Pair(row, i))
                        }
                    }
                    else break
                }
            }

            //vertical top
            if (row != 1) {
                for (i in (row - 1 downTo 1)) {
                    val field = document.getElementById("board--field-$i-$col")
                    if (field != null) {
                        if (field.hasChildNodes()) {
                            val child = field.firstElementChild
                            if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                                    (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                                validDropFields.add(Pair(i, col))
                            }
                            break
                        } else {
                            validDropFields.add(Pair(i, col))
                        }
                    }
                    else break
                }
            }
            //vertical bottom
            if (row != 8) {
                for (i in (row + 1..8)) {
                    val field = document.getElementById("board--field-$i-$col")
                    if (field != null) {
                        if (field.hasChildNodes()) {
                            val child = field.firstElementChild
                            if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                                    (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                                validDropFields.add(Pair(i, col))
                            }
                            break
                        } else {
                            validDropFields.add(Pair(i, col))
                        }
                    }
                    else break
                }
            }
        }
    }
}