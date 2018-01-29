package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.ClientController
import htwdd.chessgame.client.controller.Controller
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.form
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document

class MatchFormPartial : Partial {
    override fun getPartial(controller: Controller): HTMLElement {
        return when (controller) {
            is ClientController -> document.create.form(classes = "form--match") {
                label {
                    +"Player White:"
                    select(classes = "match--player-white") {
                        option {
                            +"Please choose"
                            value = "-1"
                        }
                        controller.getPlayers().forEach { player ->
                            option {
                                +player.value.name
                                value = "${player.value.id}"
                            }
                        }
                    }
                }
                label {
                    +"Player Black:"
                    select(classes = "match--player-black") {
                        option {
                            +"Please choose"
                            value = "-1"
                        }
                        controller.getPlayers().forEach { player ->
                            option {
                                +player.value.name
                                value = "${player.value.id}"
                            }
                        }
                    }
                }
                input {
                    value = "Submit"
                    type = InputType.submit
                }
                onSubmitFunction = { e ->
                    e.preventDefault()
                    controller.actionPerformed("addMatch", document.getElementsByClassName("form--match")[0])
                }
            }
            else -> document.create.form()
        }


    }
}