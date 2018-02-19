package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceColor
import kotlin.browser.document
import kotlin.dom.hasClass

class KnightMovementUtility : MovementUtility {
    override fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, pieceColor: PieceColor, match: Match?) {
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
                    movementFields.add(Pair(row + 2, col + 1))
                }
            } else {
                movementFields.add(Pair(row + 2, col + 1))
            }
        }
        if (field2 != null) {
            if (field2.hasChildNodes()) {
                val child = field2.firstElementChild
                if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                        (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                    movementFields.add(Pair(row + 1, col + 2))
                }
            } else {
                movementFields.add(Pair(row + 1, col + 2))
            }
        }
        if (field3 != null) {
            if (field3.hasChildNodes()) {
                val child = field3.firstElementChild
                if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                        (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                    movementFields.add(Pair(row - 1, col + 2))
                }
            } else {
                movementFields.add(Pair(row - 1, col + 2))
            }
        }
        if (field4 != null) {
            if (field4.hasChildNodes()) {
                val child = field4.firstElementChild
                if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                        (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                    movementFields.add(Pair(row - 2, col + 1))
                }
            } else {
                movementFields.add(Pair(row - 2, col + 1))
            }
        }
        if (field5 != null) {
            if (field5.hasChildNodes()) {
                val child = field5.firstElementChild
                if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                        (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                    movementFields.add(Pair(row - 2, col - 1))
                }
            } else {
                movementFields.add(Pair(row - 2, col - 1))
            }
        }
        if (field6 != null) {
            if (field6.hasChildNodes()) {
                val child = field6.firstElementChild
                if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                        (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                    movementFields.add(Pair(row - 1, col - 2))
                }
            } else {
                movementFields.add(Pair(row - 1, col - 2))
            }
        }
        if (field7 != null) {
            if (field7.hasChildNodes()) {
                val child = field7.firstElementChild
                if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                        (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                    movementFields.add(Pair(row + 1, col - 2))
                }
            } else {
                movementFields.add(Pair(row + 1, col - 2))
            }
        }
        if (field8 != null) {
            if (field8.hasChildNodes()) {
                val child = field8.firstElementChild
                if ((pieceColor == PieceColor.WHITE && child!!.hasClass("piece--black")) ||
                        (pieceColor == PieceColor.BLACK && child!!.hasClass("piece--white"))) {
                    movementFields.add(Pair(row + 2, col - 1))
                }
            } else {
                movementFields.add(Pair(row + 2, col - 1))
            }
        }
    }

    override fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, pieceColor: PieceColor, match: Match?) {
        getMovementFields(threatedFields, row, col, pieceColor, match)
    }
}