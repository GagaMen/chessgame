package htwdd.chessgame.client.view.partial

import htwdd.chessgame.client.controller.ClientController
import htwdd.chessgame.client.controller.Controller
import kotlinx.html.dom.create
import kotlinx.html.js.ul
import kotlinx.html.li
import kotlinx.html.span
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class PlayerListPartial : Partial {
    override fun getView(controller: Controller): HTMLElement {
        return when (controller) {
            is ClientController -> document.create.ul(classes = "list--player") {
                controller.getPlayers().forEach { player ->
                    li(classes = "list--player-item") {
                        span {
                            +"${player.value.id}: ${player.value.name}"
                        }
                    }
                }
            }
            else -> document.create.ul()
        }
    }
}