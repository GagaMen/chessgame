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

class MatchPartial : Partial {
    override fun getPartial(controller: Controller): HTMLElement {
        val matchPartial = document.create.div(classes = "container") {
            div(classes = "row") {
                div(classes = "col-sm-12") {
                    h1 {
                        +"Match overview"
                    }
                }
            }
            div(classes = "row") {
                div(classes = "col-sm-6") {
                    div(classes = "placeholder--match-list")
                }
                div(classes = "col-sm-6") {
                    div(classes = "placeholder--match-form")
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

        val matchList = MatchTablePartial().getPartial(controller)
        val matchForm = MatchFormPartial().getPartial(controller)

        matchPartial.getElementsByClassName("placeholder--match-list")[0]?.replaceWith(matchList)
        matchPartial.getElementsByClassName("placeholder--match-form")[0]?.replaceWith(matchForm)

        return matchPartial
    }
}