package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.Controller
import htwdd.chessgame.client.controller.PlayerController
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class PlayerTablePartial : Partial {
    override fun getPartial(controller: Controller): HTMLElement {
        return when (controller) {
            is PlayerController -> document.create.table(classes = "table--player") {
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
                                if (player.value.id != 1) {
                                    button(classes = "btn btn--symbol btn--symbol-edit") {
                                        title = "Edit player"
                                        attributes["data-id"] = player.value.id.toString()
                                        span(classes = "sr-only") {
                                            +"Edit player"
                                        }
                                        onClickFunction = { e ->
                                            e.preventDefault()
                                            controller.actionPerformed("editPlayerAction", this)
                                        }
                                    }
                                    button(classes = "btn btn--symbol btn--symbol-delete") {
                                        title = "Delete player"
                                        attributes["data-id"] = player.value.id.toString()
                                        span(classes = "sr-only") {
                                            +"Delete player"
                                        }
                                        onClickFunction = { e ->
                                            e.preventDefault()
                                            controller.actionPerformed("removePlayerAction", this)
                                        }
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