package htwdd.chessgame.client.view.partial

import htwdd.chessgame.client.controller.Controller
import kotlinx.html.div
import kotlinx.html.dom.create
import kotlinx.html.js.div
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document

class PlayerPartial : Partial {
    override fun getView(controller: Controller): HTMLElement {
        val playerPartial = document.create.div(classes = "container") {
            div(classes = "row") {
                div(classes = "col-sm-6") {
                    div(classes = "placeholder--player-list")
                }
                div(classes = "col-sm-6") {
                    div(classes = "placeholder--player-form")
                }
            }
        }

        val playerList = PlayerListPartial().getView(controller)
        val playerForm = PlayerFormPartial().getView(controller)
        playerPartial.getElementsByClassName("placeholder--player-list")[0]?.replaceWith(playerList)
        playerPartial.getElementsByClassName("placeholder--player-form")[0]?.replaceWith(playerForm)

        return playerPartial
    }
}