package htwdd.chessgame.client.view.partial

import htwdd.chessgame.client.controller.ClientController
import htwdd.chessgame.client.controller.Controller
import kotlinx.html.dom.create
import kotlinx.html.js.ul
import kotlinx.html.li
import kotlinx.html.span
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class MatchListPartial : Partial {
    override fun getView(controller: Controller): HTMLElement {
        return when (controller) {
            is ClientController -> document.create.ul(classes = "list--match") {
                controller.getMatches().forEach { match ->
                    li(classes = "list--match-item") {
                        span {
                            +"${match.value.id}: "
                            match.value.players.forEach { player ->
                                +"${player.value?.name} "
                            }
                        }
                    }
                }
            }
            else -> document.create.ul()
        }
    }
}