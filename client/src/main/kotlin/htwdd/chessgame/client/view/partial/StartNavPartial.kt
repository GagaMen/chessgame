package htwdd.chessgame.client.view.partial

import htwdd.chessgame.client.controller.Controller
import kotlinx.html.a
import kotlinx.html.dom.create
import kotlinx.html.h1
import kotlinx.html.js.nav
import kotlinx.html.li
import kotlinx.html.ul
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class StartNavPartial : Partial {
    override fun getView(controller: Controller): HTMLElement {
        return document.create.nav(classes = "nav--main") {
            h1(classes = "text--center") { +"The Chess Game" }
            ul(classes = "nav--list") {
                li {
                    a(classes = "btn btn--primary") {
                        href = "#players"
                        +"Players"
                    }
                }
                li {
                    a(classes = "btn btn--primary") {
                        href = "#matches"
                        +"Matches"
                    }
                }
            }
        }
    }
}