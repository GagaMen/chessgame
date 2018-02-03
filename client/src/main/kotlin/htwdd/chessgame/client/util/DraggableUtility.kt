package htwdd.chessgame.client.util

import org.w3c.dom.*
import org.w3c.dom.events.Event
import kotlin.browser.document

class DraggableUtility {
    companion object {
        fun dragStart(event: Event) {
            when (event) {
                is DragEvent -> {
                    event.dataTransfer?.setData("text", (event.target as HTMLImageElement).id)
                }
            }
        }

        fun dragOver(event: Event) {
            val currentTarget = event.currentTarget
            if (!(currentTarget as HTMLElement).hasChildNodes()) {
                event.preventDefault()
            }
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
    }
}