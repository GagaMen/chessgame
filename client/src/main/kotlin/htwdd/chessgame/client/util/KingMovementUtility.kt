package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceColor
import kotlin.browser.document
import kotlin.dom.hasClass

class KingMovementUtility : MovementUtility {
    override fun setValidDropFields(validDropFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, pieceColor: PieceColor, match: Match?) {
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
}