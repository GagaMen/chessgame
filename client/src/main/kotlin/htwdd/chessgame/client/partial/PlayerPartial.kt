package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.Controller
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.dom.create
import kotlinx.html.h1
import kotlinx.html.js.div
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document

class PlayerPartial : Partial {
    override fun getPartial(controller: Controller): HTMLElement {
        val playerPartial = document.create.div(classes = "container") {
            div(classes = "row") {
                div(classes = "col-sm-12") {
                    h1 {
                        +"Player overview"
                    }
                }
            }
            div(classes = "row") {
                div(classes = "col-sm-6") {
                    div(classes = "placeholder--player-list")
                }
                div(classes = "col-sm-6") {
                    div(classes = "placeholder--player-form")
                }
            }
            div(classes = "row") {
                div(classes = "col-sm-12") {
                    a(classes = "btn btn--ghost") {
                        href = "#start"
                        +"Return"
                        onClickFunction = { e ->
                            e.preventDefault()
                            controller.actionPerformed("showStart")
                        }
                    }
                }
            }
        }

        val playerList = PlayerListPartial().getPartial(controller)
        val playerForm = PlayerFormPartial().getPartial(controller)
        playerPartial.getElementsByClassName("placeholder--player-list")[0]?.replaceWith(playerList)
        playerPartial.getElementsByClassName("placeholder--player-form")[0]?.replaceWith(playerForm)

        return playerPartial
    }
}