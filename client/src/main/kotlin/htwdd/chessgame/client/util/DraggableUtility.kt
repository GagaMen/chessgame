package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.PieceColor
import htwdd.chessgame.client.model.PieceType
import org.w3c.dom.*
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.dom.addClass
import kotlin.dom.hasClass
import kotlin.dom.removeClass

class DraggableUtility {
    companion object {
        private var validDropFields = HashSet<Pair<Int, Int>>()
        private val bishop = BishopMovementUtility()
        private val king = KingMovementUtility()
        private val knight = KnightMovementUtility()
        private val pawn = PawnMovementUtility()
        private val queen = QueenMovementUtility()
        private val rook = RookMovementUtility()

        fun dragStart(event: Event) {
            when (event) {
                is DragEvent -> {
                    val target = event.target
                    if (target is Element) {
                        if (validDropFields.size == 0) {
                            calculateValidDropFields(target)
                        }
                        validDropFields.forEach {
                            val field = document.getElementById("board--field-${it.first}-${it.second}")
                            field?.addClass("highlighted")
                        }
                        event.dataTransfer?.setData("text", target.id)
                    }
                }
            }
        }

        fun dragOver(event: Event) {
            val currentTarget = event.currentTarget
            if (currentTarget is Element) {
                val row = currentTarget.attributes["data-row"]?.nodeValue?.toIntOrNull()
                val col = currentTarget.attributes["data-col"]?.nodeValue?.toIntOrNull()
                if (row != null && col != null && validDropFields.contains(Pair(row, col))) {
                    event.preventDefault()
                }
            }
        }

        fun dragEnd(event: Event) {
            validDropFields.forEach {
                val field = document.getElementById("board--field-${it.first}-${it.second}")
                field?.removeClass("highlighted")
            }
            validDropFields.clear()
        }

        fun drop(event: Event) {
            event.preventDefault()
            when (event) {
                is DragEvent -> {
                    val data = event.dataTransfer?.getData("text") ?: ""
                    val target = event.target

                    when (target) {
                        is HTMLDivElement -> {
                            target.appendChild(document.getElementById(data) as Node)
                        }
                    }
                }
            }
        }

        fun mouseOver(event: Event) {
            val target = event.target
            when (target) {
                is Element -> {
                    calculateValidDropFields(target)
                    validDropFields.forEach {
                        val field = document.getElementById("board--field-${it.first}-${it.second}")
                        field?.addClass("highlighted")
                    }
                }
            }
        }

        fun mouseOut(event: Event) {
            val target = event.target
            when (target) {
                is Element -> {
                    validDropFields.forEach {
                        val field = document.getElementById("board--field-${it.first}-${it.second}")
                        field?.removeClass("highlighted")
                    }
                    validDropFields.clear()
                }
            }
        }

        fun calculateValidDropFields(image: Element) {
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
                    PieceType.BISHOP.toString() -> bishop.setValidDropFields(validDropFields, row, col, pieceColor)
                    PieceType.KING.toString() -> king.setValidDropFields(validDropFields, row, col, pieceColor)
                    PieceType.KNIGHT.toString() -> knight.setValidDropFields(validDropFields, row, col, pieceColor)
                    PieceType.PAWN.toString() -> pawn.setValidDropFields(validDropFields, row, col, pieceColor)
                    PieceType.QUEEN.toString() -> queen.setValidDropFields(validDropFields, row, col, pieceColor)
                    PieceType.ROOK.toString() -> rook.setValidDropFields(validDropFields, row, col, pieceColor)
                }
            }
        }
    }
}