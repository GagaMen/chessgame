package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceColor
import kotlin.browser.document
import kotlin.dom.hasClass

class PawnMovementUtility : MovementUtility {
    override fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, pieceColor: PieceColor, match: Match?) {
        when (pieceColor) {
            PieceColor.WHITE -> {
                val field1 = document.getElementById("board--field-${row + 1}-$col")
                val field2 = document.getElementById("board--field-${row + 2}-$col")
                val field3 = document.getElementById("board--field-${row + 1}-${col + 1}")
                val field4 = document.getElementById("board--field-${row + 1}-${col - 1}")

                if (field1 != null && !field1.hasChildNodes()) {
                    movementFields.add(Pair(row + 1, col))
                    if (row == 2 && field2 != null && !field2.hasChildNodes()) {
                        movementFields.add(Pair(row + 2, col))
                    }
                }
                if (field3 != null && field3.hasChildNodes()) {
                    val child = field3.firstElementChild
                    if (child!!.hasClass("piece--black")) {
                        movementFields.add(Pair(row + 1, col + 1))
                    }
                }
                if (field4 != null && field4.hasChildNodes()) {
                    val child = field4.firstElementChild
                    if (child!!.hasClass("piece--black")) {
                        movementFields.add(Pair(row + 1, col - 1))
                    }
                }
                if (match?.enPassantField != null && match.enPassantField?.row == row + 1) {
                    if (match.enPassantField?.column == col + 1) {
                        movementFields.add(Pair(row + 1, col + 1))
                    }
                    if (match.enPassantField?.column == col - 1) {
                        movementFields.add(Pair(row + 1, col - 1))
                    }
                }
            }
            PieceColor.BLACK -> {
                val field1 = document.getElementById("board--field-${row - 1}-$col")
                val field2 = document.getElementById("board--field-${row - 2}-$col")
                val field3 = document.getElementById("board--field-${row - 1}-${col + 1}")
                val field4 = document.getElementById("board--field-${row - 1}-${col - 1}")

                if (field1 != null && !field1.hasChildNodes()) {
                    movementFields.add(Pair(row - 1, col))
                    if (row == 7 && field2 != null && !field2.hasChildNodes()) {
                        movementFields.add(Pair(row - 2, col))
                    }
                }
                if (field3 != null && field3.hasChildNodes()) {
                    val child = field3.firstElementChild
                    if (child!!.hasClass("piece--white")) {
                        movementFields.add(Pair(row - 1, col + 1))
                    }
                }
                if (field4 != null && field4.hasChildNodes()) {
                    val child = field4.firstElementChild
                    if (child!!.hasClass("piece--white")) {
                        movementFields.add(Pair(row - 1, col - 1))
                    }
                }
                if (match?.enPassantField != null && match.enPassantField?.row == row - 1) {
                    if (match.enPassantField?.column == col + 1) {
                        movementFields.add(Pair(row - 1, col + 1))
                    }
                    if (match.enPassantField?.column == col - 1) {
                        movementFields.add(Pair(row - 1, col - 1))
                    }
                }
            }
        }
    }

    override fun getThreadedFields(threadedFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, pieceColor: PieceColor, match: Match?) {
        when (pieceColor) {
            PieceColor.WHITE -> {
                val field1 = document.getElementById("board--field-${row + 1}-${col + 1}")
                val field2 = document.getElementById("board--field-${row + 1}-${col - 1}")

                if (field1 != null && field1.hasChildNodes()) {
                    val child = field1.firstElementChild
                    if (child!!.hasClass("piece--black")) {
                        threadedFields.add(Pair(row + 1, col + 1))
                    }
                }
                if (field2 != null && field2.hasChildNodes()) {
                    val child = field2.firstElementChild
                    if (child!!.hasClass("piece--black")) {
                        threadedFields.add(Pair(row + 1, col - 1))
                    }
                }
                if (match?.enPassantField != null && match.enPassantField?.row == row + 1) {
                    if (match.enPassantField?.column == col + 1) {
                        threadedFields.add(Pair(row + 1, col + 1))
                    }
                    if (match.enPassantField?.column == col - 1) {
                        threadedFields.add(Pair(row + 1, col - 1))
                    }
                }
            }
            PieceColor.BLACK -> {
                val field1 = document.getElementById("board--field-${row - 1}-${col + 1}")
                val field2 = document.getElementById("board--field-${row - 1}-${col - 1}")

                if (field1 != null && field1.hasChildNodes()) {
                    val child = field1.firstElementChild
                    if (child!!.hasClass("piece--white")) {
                        threadedFields.add(Pair(row - 1, col + 1))
                    }
                }
                if (field2 != null && field2.hasChildNodes()) {
                    val child = field2.firstElementChild
                    if (child!!.hasClass("piece--white")) {
                        threadedFields.add(Pair(row - 1, col - 1))
                    }
                }
                if (match?.enPassantField != null && match.enPassantField?.row == row - 1) {
                    if (match.enPassantField?.column == col + 1) {
                        threadedFields.add(Pair(row - 1, col + 1))
                    }
                    if (match.enPassantField?.column == col - 1) {
                        threadedFields.add(Pair(row - 1, col - 1))
                    }
                }
            }
        }
    }
}