package htwdd.chessgame.client.view

import htwdd.chessgame.client.controller.ClientController
import htwdd.chessgame.client.util.Observable
import htwdd.chessgame.client.util.Observer
import htwdd.chessgame.client.view.partial.MatchFormPartial
import htwdd.chessgame.client.view.partial.MatchListPartial
import htwdd.chessgame.client.view.partial.PlayerFormPartial
import htwdd.chessgame.client.view.partial.PlayerListPartial
import kotlinx.html.dom.create
import kotlinx.html.h1
import kotlinx.html.js.div
import kotlinx.html.js.footer
import kotlinx.html.js.header
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.dom.clear

class MainView(private val controller: ClientController) : Observer {

    private val root = document.getElementsByClassName("app")[0] as HTMLDivElement
    private var header: HTMLElement
    private var main: HTMLElement
    private var footer: HTMLElement

    init {
        val mainElements = render()
        header = mainElements[0]
        main = mainElements[1]
        footer = mainElements[2]
    }

    private fun render(): Array<HTMLElement> {
        val header = document.create.header {
            h1 { +"Chess Game" }
        }

        val main = document.create.div(classes = "main")
        main.appendChild(MatchListPartial().getView(controller))
        main.appendChild(MatchFormPartial().getView(controller))
        main.appendChild(PlayerListPartial().getView(controller))
        main.appendChild(PlayerFormPartial().getView(controller))

        val footer = document.create.footer {

        }

        root.clear()
        root.appendChild(header)
        root.appendChild(main)
        root.appendChild(footer)

        return arrayOf(header, main, footer)
    }

    override fun update(o: Observable?, arg: Any?) {
        when (arg) {
            "updatePlayerList" -> {
                val list = main.getElementsByClassName("list--player")[0]
                list?.replaceWith(PlayerListPartial().getView(controller))

                val matchForm = main.getElementsByClassName("form--match")[0]
                matchForm?.replaceWith(MatchFormPartial().getView(controller))
            }
            "updateMatchList" -> {
                val list = main.getElementsByClassName("list--match")[0]
                list?.replaceWith(MatchListPartial().getView(controller))
            }
        }
    }
}