package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.Controller
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.nav
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class StartPartial : Partial {
    override fun getPartial(controller: Controller): HTMLElement {
        return document.create.nav(classes = "nav--main") {
            h1(classes = "text--center") { +"The Chess Game" }
            ul(classes = "nav--list") {
                li {
                    a(classes = "btn btn--primary") {
                        title = "Player overview"
                        href = "#players"
                        +"Players"
                        onClickFunction = { e ->
                            e.preventDefault()
                            controller.actionPerformed("showPlayerAction")
                        }
                    }
                }
                li {
                    a(classes = "btn btn--primary") {
                        title = "Match overview"
                        href = "#matches"
                        +"Matches"
                        onClickFunction = { e ->
                            e.preventDefault()
                            controller.actionPerformed("showMatchAction")
                        }
                    }
                }
            }
        }
    }
}