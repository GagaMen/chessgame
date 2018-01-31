package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.ClientController
import htwdd.chessgame.client.controller.Controller
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.table
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
                                button(classes = "btn btn--symbol btn--symbol-start") {
                                    attributes["data-id"] = match.value.id.toString()
                                    span(classes = "sr-only") {
                                        +"Start match"
                                    }
                                    onClickFunction = { e ->
                                        e.preventDefault()
                                        controller.actionPerformed("startMatch", this)
                                    }
                                }
                                button(classes = "btn btn--symbol btn--symbol-delete") {
                                    attributes["data-id"] = match.value.id.toString()
                                    span(classes = "sr-only") {
                                        +"Delete match"
                                    }
                                    onClickFunction = { e ->
                                        e.preventDefault()
                                        controller.actionPerformed("removeMatch", this)
                                    }
                                }
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