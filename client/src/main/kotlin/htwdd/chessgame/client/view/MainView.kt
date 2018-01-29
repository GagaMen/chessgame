package htwdd.chessgame.client.view

import htwdd.chessgame.client.controller.ClientController
import htwdd.chessgame.client.util.Observable
import htwdd.chessgame.client.util.Observer
import htwdd.chessgame.client.view.partial.*
import kotlinx.html.dom.create
import kotlinx.html.js.div
import kotlinx.html.js.footer
import kotlinx.html.js.header
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.dom.clear
import kotlin.dom.removeClass

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
        val header = document.create.header(classes = "main-header")

        val main = document.create.div(classes = "main")
        main.appendChild(StartPartial().getView(controller))

        val footer = document.create.footer(classes = "main-footer")

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
            "showPlayer" -> {
                root.removeClass("box--shadow")
                main.clear()
                main.append(PlayerPartial().getView(controller))
            }
            "showMatch" -> {
                root.removeClass("box--shadow")
                main.clear()
                main.append(MatchPartial().getView(controller))
            }
        }
    }
}