package htwdd.chessgame.client.view.partial

import htwdd.chessgame.client.controller.Controller
import kotlinx.html.div
import kotlinx.html.dom.create
import kotlinx.html.js.div
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document

class MatchPartial : Partial {
    override fun getView(controller: Controller): HTMLElement {
        val matchPartial = document.create.div(classes = "container") {
            div(classes = "row") {
                div(classes = "col-sm-6") {
                    div(classes = "placeholder--match-list")
                }
                div(classes = "col-sm-6") {
                    div(classes = "placeholder--match-form")
                }
            }
        }

        val matchList = MatchListPartial().getView(controller)
        val matchForm = MatchFormPartial().getView(controller)

        matchPartial.getElementsByClassName("placeholder--match-list")[0]?.replaceWith(matchList)
        matchPartial.getElementsByClassName("placeholder--match-form")[0]?.replaceWith(matchForm)

        return matchPartial
    }
}