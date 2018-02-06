package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.PieceColor
import kotlin.browser.document
import kotlin.dom.hasClass

class RookMovementUtility : MovementUtility {
    override fun setValidDropFields(validDropFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, pieceColor: PieceColor) {
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
                } else break
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
                } else break
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
                } else break
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
                } else break
            }
        }
    }
}