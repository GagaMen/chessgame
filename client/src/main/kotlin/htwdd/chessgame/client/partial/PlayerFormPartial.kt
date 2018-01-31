package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.Controller
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document

class PlayerFormPartial : Partial {
    override fun getPartial(controller: Controller): HTMLElement {
        return document.create.form(classes = "form form--player") {
            h2 {
                +"Add new player:"
            }
            label(classes = "form--label") {
                +"Name:"
                input(classes = "form--input player--name") {
                    type = InputType.text
                }
            }
            label(classes = "form--label") {
                +"Password:"
                input(classes = "form--input player--password") {
                    type = InputType.password
                }
            }
            input(classes = "btn btn--inline btn--submit") {
                title = "Add player"
                value = "Add player"
                type = InputType.submit
            }
            onSubmitFunction = { e ->
                e.preventDefault()
                controller.actionPerformed("addPlayer", document.getElementsByClassName("form--player")[0])
            }
        }
    }
}