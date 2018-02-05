package htwdd.chessgame.client.util

import org.w3c.dom.*
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.dom.addClass
import kotlin.dom.removeClass

class DraggableUtility {
    companion object {
        var validDropFields = HashSet<Pair<Int, Int>>()

        fun dragStart(event: Event) {
            when (event) {
                is DragEvent -> {
                    val target = event.target
                    if (target is Element) {
                        if (validDropFields.size == 0) {
                            validDropFields = ChessRuleUtility.getValidDropFields(target)
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
                    validDropFields = ChessRuleUtility.getValidDropFields(target)
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
    }
}