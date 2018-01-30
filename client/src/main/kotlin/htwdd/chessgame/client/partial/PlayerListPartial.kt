package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.ClientController
import htwdd.chessgame.client.controller.Controller
import kotlinx.html.dom.create
import kotlinx.html.js.ul
import kotlinx.html.li
import kotlinx.html.span
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class PlayerListPartial : Partial {
    override fun getPartial(controller: Controller): HTMLElement {
        return when (controller) {
            is ClientController -> document.create.ul(classes = "list--player") {
                if (controller.getPlayers().size > 0) {
                    controller.getPlayers().forEach { player ->
                        li(classes = "list--player-item") {
                            span {
                                +"${player.value.id}: ${player.value.name}"
                            }
                        }
                    }
                } else {
                    li(classes = "list--player-item") {
                        span {
                            +"No player registered"
                        }
                    }
                }
            }
            else -> document.create.ul()
        }
    }
}