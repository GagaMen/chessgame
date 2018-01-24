package htwdd.chessgame.client.view

import htwdd.chessgame.client.controller.ClientController
import htwdd.chessgame.client.util.Observable
import htwdd.chessgame.client.util.Observer
import kotlinx.html.dom.create
import kotlinx.html.h1
import kotlinx.html.js.div
import kotlinx.html.js.footer
import kotlinx.html.js.header
import kotlinx.html.li
import kotlinx.html.span
import kotlinx.html.ul
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.dom.clear

class MainView(private val control: ClientController) : Observer {

    private val root = document.getElementsByClassName("app")[0] as HTMLDivElement

    init {
        render()
    }

    fun render() {
        val header = document.create.header {
            h1 { +"Chess Game" }
        }

        val main = document.create.div(classes = "main") {
            ul {
                control.getPlayers().forEach { player ->
                    li {
                        span {
                            +"${player.value.id}: ${player.value.name}"
                        }
                    }
                }
            }
        }
        main.appendChild(PlayerFormView().getView(control))

        val footer = document.create.footer {

        }

        root.clear()
        root.appendChild(header)
        root.appendChild(main)
        root.appendChild(footer)
    }

    override fun update(o: Observable?, arg: Any?) {
        render()
    }
}