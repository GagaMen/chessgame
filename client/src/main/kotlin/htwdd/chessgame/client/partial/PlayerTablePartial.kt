package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.ClientController
import htwdd.chessgame.client.controller.Controller
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class PlayerTablePartial : Partial {
    override fun getPartial(controller: Controller): HTMLElement {
        return when (controller) {
            is ClientController -> document.create.table(classes = "table--player") {
                tr {
                    th { +"ID" }
                    th { +"Name" }
                    th { +"Toolbar" }
                }
                if (controller.getPlayers().size > 0) {
                    controller.getPlayers().forEach { player ->
                        tr(classes = "table--player-item") {
                            td {
                                +player.value.id.toString()
                            }
                            td {
                                +player.value.name
                            }
                            td {
                                button(classes = "btn btn--symbol btn--symbol-edit") {
                                    attributes["data-id"] = player.value.id.toString()
                                    span(classes = "sr-only") {
                                        +"Edit player"
                                    }
                                    onClickFunction = { e ->
                                        e.preventDefault()
                                        controller.actionPerformed("editPlayer", this)
                                    }
                                }
                                button(classes = "btn btn--symbol btn--symbol-delete") {
                                    attributes["data-id"] = player.value.id.toString()
                                    span(classes = "sr-only") {
                                        +"Delete player"
                                    }
                                    onClickFunction = { e ->
                                        e.preventDefault()
                                        controller.actionPerformed("removePlayer", this)
                                    }
                                }
                            }
                        }
                    }
                } else {
                    tr(classes = "table--player-item") {
                        td {
                            colSpan = "3"
                            +"No match registered"
                        }
                    }
                }
            }
            else -> document.create.table()
        }
    }
}