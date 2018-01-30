package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.Controller
import htwdd.chessgame.client.model.Player
import kotlinx.html.InputType
import kotlinx.html.dom.create
import kotlinx.html.h2
import kotlinx.html.input
import kotlinx.html.js.form
import kotlinx.html.js.onSubmitFunction
import kotlinx.html.label
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document

class PlayerEditFormPartial(val player: Player) : Partial {
    override fun getPartial(controller: Controller): HTMLElement {
        return document.create.form(classes = "form form--player") {
            attributes["data-id"] = player.id.toString()
            h2 {
                +"Edit player: ${player.name}"
            }
            label(classes = "form--label") {
                +"Password:"
                input(classes = "form--input player--password") {
                    type = InputType.password
                }
            }
            input(classes = "btn btn--inline btn--submit") {
                value = "Submit"
                type = InputType.submit
            }
            input(classes = "btn btn--inline btn--cancel") {
                value = "Cancel"
                type = InputType.button
            }
            onSubmitFunction = { e ->
                e.preventDefault()
                controller.actionPerformed("updatePlayer", document.getElementsByClassName("form--player")[0])
            }
        }
    }
}