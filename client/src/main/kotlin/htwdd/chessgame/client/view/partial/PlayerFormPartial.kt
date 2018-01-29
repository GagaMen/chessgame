package htwdd.chessgame.client.view.partial

import htwdd.chessgame.client.controller.Controller
import kotlinx.html.InputType
import kotlinx.html.dom.create
import kotlinx.html.form
import kotlinx.html.input
import kotlinx.html.js.onSubmitFunction
import kotlinx.html.label
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document

class PlayerFormPartial : Partial {
    override fun getPartial(controller: Controller): HTMLElement {
        return document.create.form(classes = "form--player") {
            label {
                +"Name:"
                input(classes = "player--name") {
                    type = InputType.text
                }
            }
            label {
                +"Password:"
                input(classes = "player--password") {
                    type = InputType.password
                }
            }
            input {
                value = "Submit"
                type = InputType.submit
            }
            onSubmitFunction = { e ->
                e.preventDefault()
                controller.actionPerformed("addPlayer", document.getElementsByClassName("form--player")[0])
            }
        }
    }
}