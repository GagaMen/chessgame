package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.ClientController
import htwdd.chessgame.client.controller.Controller
import kotlinx.html.dom.create
import kotlinx.html.js.table
import kotlinx.html.td
import kotlinx.html.th
import kotlinx.html.tr
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class MatchTablePartial : Partial {
    override fun getPartial(controller: Controller): HTMLElement {
        return when (controller) {
            is ClientController -> document.create.table(classes = "table--match") {
                tr {
                    th { +"ID" }
                    th { +"Player White" }
                    th { +"Player Black" }
                    th { +"Toolbar" }
                }
                if (controller.getMatches().size > 0) {
                    controller.getMatches().forEach { match ->
                        tr(classes = "table--match-item") {
                            td {
                                +match.value.id.toString()
                            }
                            match.value.players.forEach { player ->
                                td {
                                    +"${player.value?.name}"
                                }
                            }
                            td {
                                +"Start/Delete"
                            }
                        }
                    }
                } else {
                    tr(classes = "table--match-item") {
                        td {
                            colSpan = "4"
                            +"No match registered"
                        }
                    }
                }

            }
            else -> document.create.table()
        }
    }
}