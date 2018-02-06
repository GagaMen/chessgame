package htwdd.chessgame.client.util

import htwdd.chessgame.client.controller.Controller
import htwdd.chessgame.client.model.*
import org.w3c.dom.DragEvent
import org.w3c.dom.Element
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.events.Event
import org.w3c.dom.get
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

        fun dragStart(event: Event, match: Match) {
            when (event) {
                is DragEvent -> {
                    val target = event.target
                    if (target is Element) {
                        if (validDropFields.size == 0) {
                            calculateValidDropFields(target, match)
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

        fun drop(event: Event, controller: Controller, match: Match) {
            event.preventDefault()
            when (event) {
                is DragEvent -> {
                    val data = event.dataTransfer?.getData("text") ?: ""
                    val target = event.target

                    when (target) {
                        is HTMLDivElement -> {
                            val image = document.getElementById(data)
                            val parent = image?.parentElement

                            if (image != null && parent != null) {
                                var pieceColor = PieceColor.WHITE
                                val pieceType = image.attributes["data-type"]?.nodeValue

                                val oldRow = parent.attributes["data-row"]?.nodeValue?.toIntOrNull()
                                val oldCol = parent.attributes["data-col"]?.nodeValue?.toIntOrNull()
                                val newRow = target.attributes["data-row"]?.nodeValue?.toIntOrNull()
                                val newCol = target.attributes["data-col"]?.nodeValue?.toIntOrNull()

                                if (image.hasClass("piece--black")) pieceColor = PieceColor.BLACK

                                if (oldRow != null &&
                                        oldCol != null &&
                                        newRow != null &&
                                        newCol != null &&
                                        pieceType != null) {
                                    val newDraw = Draw(pieceColor,
                                            PieceType.valueOf(pieceType),
                                            Field(oldRow, oldCol),
                                            Field(newRow, newCol))

                                    controller.actionPerformed("addDraw", Pair(match, newDraw))
                                    target.appendChild(image)
                                }
                            }
                        }
                    }
                }
            }
        }

        fun mouseOver(event: Event, match: Match) {
            val target = event.target
            when (target) {
                is Element -> {
                    calculateValidDropFields(target, match)
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

        private fun calculateValidDropFields(image: Element, match: Match) {
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

            if (pieceColor != match.currentColor) {
                return
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