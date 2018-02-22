package htwdd.chessgame.client.util

import htwdd.chessgame.client.controller.Controller
import htwdd.chessgame.client.model.*
import org.w3c.dom.*
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.dom.addClass
import kotlin.dom.clear
import kotlin.dom.hasClass
import kotlin.dom.removeClass
import kotlin.math.absoluteValue

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
            if (match.checkmate) return

            val target = event.target as? Element ?: return
            if (event !is DragEvent) return

            if (validDropFields.size == 0) calculateValidDropFields(target, match)

            validDropFields.forEach {
                document.getElementById("board--field-${it.first}-${it.second}")?.addClass("highlighted")
            }

            event.dataTransfer?.setData("text", target.id)
        }

        fun dragOver(event: Event) {
            val currentTarget = (event.currentTarget ?: return) as? Element ?: return
            val row = currentTarget.attributes["data-row"]?.nodeValue?.toIntOrNull() ?: return
            val col = currentTarget.attributes["data-col"]?.nodeValue?.toIntOrNull() ?: return

            if (validDropFields.contains(Pair(row, col))) event.preventDefault()
        }

        fun dragEnd(match: Match) {
            if (match.checkmate) return

            validDropFields.forEach {
                document.getElementById("board--field-${it.first}-${it.second}")?.removeClass("highlighted")
            }
            validDropFields.clear()

            if (CheckUtility.calcThreatedFields(match)) {
                if (CheckUtility.checkmate(match)) {
                    match.checkmate = true
                    println("${match.currentColor} checkmate!")
                } else {
                    match.switchCheck(match.currentColor)
                }
            }

            if (match.check[match.currentColor.getOpposite()]!!) {
                match.switchCheck(match.currentColor.getOpposite())
            }

        }

        fun drop(event: Event, controller: Controller, match: Match) {
            event.preventDefault()

            if (event !is DragEvent) return

            val target = event.target ?: return
            val data = event.dataTransfer?.getData("text") ?: ""

            when (target) {
                is HTMLDivElement -> {
                    val image = document.getElementById(data) ?: return
                    val parent = image.parentElement ?: return
                    var pieceColor = PieceColor.WHITE
                    val pieceType = image.attributes["data-type"]?.nodeValue ?: ""
                    val oldRow = parent.attributes["data-row"]?.nodeValue?.toIntOrNull() ?: return
                    val oldCol = parent.attributes["data-col"]?.nodeValue?.toIntOrNull() ?: return
                    val newRow = target.attributes["data-row"]?.nodeValue?.toIntOrNull() ?: return
                    val newCol = target.attributes["data-col"]?.nodeValue?.toIntOrNull() ?: return

                    if (image.hasClass("piece--black")) pieceColor = PieceColor.BLACK

                    target.appendChild(image)

                    when (PieceType.valueOf(pieceType)) {
                        PieceType.PAWN -> dropPawn(controller, match, oldRow, oldCol, newRow, newCol, pieceColor)
                        PieceType.ROOK -> dropRook(controller, match, oldCol, pieceColor)
                        PieceType.KING -> dropKing(controller, match, oldCol, newRow, newCol, pieceColor)
                        else -> controller.actionPerformed("resetEnPassantFieldAction", match)
                    }

                    val newDraw = Draw(pieceColor,
                            PieceType.valueOf(pieceType),
                            Field(oldRow, oldCol),
                            Field(newRow, newCol))

                    controller.actionPerformed("increaseHalfMovesAction", match)
                    controller.actionPerformed("addDrawAction", Pair(match, newDraw))
                }
                is HTMLImageElement -> {
                    val image = document.getElementById(data) ?: return
                    val parent = image.parentElement ?: return
                    val targetParent = target.parentElement ?: return
                    var pieceColor = PieceColor.WHITE
                    val pieceType = image.attributes["data-type"]?.nodeValue ?: ""
                    val oldRow = parent.attributes["data-row"]?.nodeValue?.toIntOrNull() ?: return
                    val oldCol = parent.attributes["data-col"]?.nodeValue?.toIntOrNull() ?: return
                    val newRow = targetParent.attributes["data-row"]?.nodeValue?.toIntOrNull() ?: return
                    val newCol = targetParent.attributes["data-col"]?.nodeValue?.toIntOrNull() ?: return

                    if (image.hasClass("piece--black")) pieceColor = PieceColor.BLACK

                    target.replaceWith(image)

                    when (PieceType.valueOf(pieceType)) {
                        PieceType.PAWN -> dropPawn(controller, match, oldRow, oldCol, newRow, newCol, pieceColor)
                        else -> controller.actionPerformed("resetEnPassantFieldAction", match)
                    }

                    val newDraw = Draw(pieceColor,
                            PieceType.valueOf(pieceType),
                            Field(oldRow, oldCol),
                            Field(newRow, newCol))

                    controller.actionPerformed("resetHalfMovesAction", match)
                    controller.actionPerformed("addDrawAction", Pair(match, newDraw))
                }
            }
        }

        fun mouseOver(event: Event, match: Match) {
            val target = (event.target ?: return) as? Element ?: return

            calculateValidDropFields(target, match)
            validDropFields.forEach {
                document.getElementById("board--field-${it.first}-${it.second}")?.addClass("highlighted")
            }
        }

        fun mouseOut() {
            validDropFields.forEach {
                document.getElementById("board--field-${it.first}-${it.second}")?.removeClass("highlighted")
            }
            validDropFields.clear()
        }

        private fun calculateValidDropFields(image: Element, match: Match) {
            var pieceColor = PieceColor.WHITE
            val type = image.attributes["data-type"]?.nodeValue ?: return
            val parent = image.parentElement ?: return
            val row = parent.attributes["data-row"]?.nodeValue?.toIntOrNull() ?: return
            val col = parent.attributes["data-col"]?.nodeValue?.toIntOrNull() ?: return

            if (image.hasClass("piece--black")) pieceColor = PieceColor.BLACK
            if (pieceColor != match.currentColor) return

            when (type) {
                PieceType.BISHOP.toString() -> {
                    bishop.getFilteredMovementFields(validDropFields, row, col, match)
                }
                PieceType.KING.toString() -> {
                    king.getFilteredMovementFields(validDropFields, row, col, match)
                }
                PieceType.KNIGHT.toString() -> {
                    knight.getFilteredMovementFields(validDropFields, row, col, match)
                }
                PieceType.PAWN.toString() -> {
                    pawn.getFilteredMovementFields(validDropFields, row, col, match)
                }
                PieceType.QUEEN.toString() -> {
                    queen.getFilteredMovementFields(validDropFields, row, col, match)
                }
                PieceType.ROOK.toString() -> {
                    rook.getFilteredMovementFields(validDropFields, row, col, match)
                }
            }
        }

        private fun dropPawn(controller: Controller, match: Match, oldRow: Int, oldCol: Int, newRow: Int, newCol: Int, pieceColor: PieceColor) {
            if ((oldRow - newRow).absoluteValue == 2) {
                val row = when (pieceColor) {
                    PieceColor.WHITE -> 3
                    PieceColor.BLACK -> 6
                }
                controller.actionPerformed("setEnPassantFieldAction", Pair(match, Field(row, oldCol)))
                return
            }

            if (newRow == match.enPassantField?.row && newCol == match.enPassantField?.column) {
                val row = when (pieceColor) {
                    PieceColor.WHITE -> 5
                    PieceColor.BLACK -> 4
                }
                document.getElementById("board--field-$row-$newCol")?.clear()
                controller.actionPerformed("resetEnPassantFieldAction", match)
                return
            }


            if (newRow == 1 || newRow == 8) {
                val popup = document.getElementsByClassName("board--popup")[0] ?: return
                popup.setAttribute("data-row", newRow.toString())
                popup.setAttribute("data-col", newCol.toString())
                popup.removeClass("hidden")
                return
            }

            controller.actionPerformed("resetEnPassantFieldAction", match)
        }

        private fun dropRook(controller: Controller, match: Match, oldCol: Int, pieceColor: PieceColor) {
            controller.actionPerformed("resetEnPassantFieldAction", match)

            if ((match.whiteCastlingKingSide || match.blackCastlingKingSide) && oldCol == 8) {
                controller.actionPerformed("disableKingSideCastlingAction", Pair(match, pieceColor))
            }

            if ((match.whiteCastlingQueenSide || match.blackCastlingQueenSide) && oldCol == 1) {
                controller.actionPerformed("disableQueenSideCastlingAction", Pair(match, pieceColor))
            }
        }

        private fun dropKing(controller: Controller, match: Match, oldCol: Int, newRow: Int, newCol: Int, pieceColor: PieceColor) {
            controller.actionPerformed("resetEnPassantFieldAction", match)
            controller.actionPerformed("disableCastlingAction", Pair(match, pieceColor))

            when (newCol) {
                oldCol + 2 -> {
                    val rook = (document.getElementById("board--field-$newRow-8")?.firstElementChild ?: return)
                            as? HTMLImageElement ?: return
                    val rookTarget = document.getElementById("board--field-$newRow-6") ?: return
                    rookTarget.appendChild(rook)
                }
                oldCol - 2 -> {
                    val rook = (document.getElementById("board--field-$newRow-1")?.firstElementChild ?: return)
                            as? HTMLImageElement ?: return
                    val rookTarget = document.getElementById("board--field-$newRow-4") ?: return
                    rookTarget.appendChild(rook)
                }
            }
        }
    }
}