package htwdd.chessgame.client.util

import org.w3c.dom.DragEvent
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLImageElement
import org.w3c.dom.Node
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
            event.preventDefault()
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