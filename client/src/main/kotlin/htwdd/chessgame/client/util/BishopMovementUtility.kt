package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceColor
import kotlin.browser.document
import kotlin.dom.hasClass

class BishopMovementUtility : MovementUtility {
    override fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, pieceColor: PieceColor, match: Match?) {
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
                                    movementFields.add(Pair(i, tmpCol))
                                    break
                                } else {
                                    break
                                }
                            } else {
                                movementFields.add(Pair(i, tmpCol))
                            }
                        } else break
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
                                    movementFields.add(Pair(i, tmpCol))
                                    break
                                } else {
                                    break
                                }
                            } else {
                                movementFields.add(Pair(i, tmpCol))
                            }
                        } else break
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
                                    movementFields.add(Pair(i, tmpCol))
                                    break
                                } else {
                                    break
                                }
                            } else {
                                movementFields.add(Pair(i, tmpCol))
                            }
                        } else break
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
                                    movementFields.add(Pair(i, tmpCol))
                                    break
                                } else {
                                    break
                                }
                            } else {
                                movementFields.add(Pair(i, tmpCol))
                            }
                        } else break
                    } else {
                        break
                    }
                }
            }
        }
    }

    override fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, pieceColor: PieceColor, match: Match?) {
        getMovementFields(threatedFields, row, col, pieceColor, match)
    }
}