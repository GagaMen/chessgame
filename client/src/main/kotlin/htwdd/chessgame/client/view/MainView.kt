package htwdd.chessgame.client.view

import htwdd.chessgame.client.controller.ClientController
import htwdd.chessgame.client.model.Client
import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.Player
import htwdd.chessgame.client.model.ViewState
import htwdd.chessgame.client.partial.*
import htwdd.chessgame.client.util.Observable
import htwdd.chessgame.client.util.Observer
import kotlinx.html.dom.create
import kotlinx.html.js.div
import kotlinx.html.js.footer
import kotlinx.html.js.header
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.dom.addClass
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
        main.appendChild(StartPartial().getPartial(controller))

        val footer = document.create.footer(classes = "main-footer")

        root.appendChild(header)
        root.appendChild(main)
        root.appendChild(footer)

        return arrayOf(header, main, footer)
    }

    override fun update(o: Observable?, arg: Any?) {
        when (o) {
            is Client -> {
                when (arg) {
                    "updatePlayerTable" -> {
                        val table = main.getElementsByClassName("table--player")[0]
                        table?.replaceWith(PlayerTablePartial().getPartial(controller))
                    }
                    "resetPlayerForm" -> {
                        val form = main.getElementsByClassName("form--player")[0]
                        form?.replaceWith(PlayerFormPartial().getPartial(controller))
                    }
                    "updateMatchTable" -> {
                        val table = main.getElementsByClassName("table--match")[0]
                        table?.replaceWith(MatchTablePartial().getPartial(controller))
                    }
                    ViewState.PLAYER -> {
                        root.removeClass("box--shadow")
                        main.clear()
                        main.append(PlayerPartial().getPartial(controller))
                    }
                    ViewState.MATCH -> {
                        root.removeClass("box--shadow")
                        main.clear()
                        main.append(MatchPartial().getPartial(controller))
                    }
                    ViewState.START -> {
                        root.addClass("box--shadow")
                        main.clear()
                        main.append(StartPartial().getPartial(controller))
                    }
                }
            }
            is Player -> {
                when (arg) {
                    "editPlayer" -> {
                        val form = main.getElementsByClassName("form--player")[0]
                        form?.replaceWith(PlayerEditFormPartial(o).getPartial(controller))
                    }
                }
            }
            is Match -> {
                when (arg) {
                    ViewState.GAME -> {
                        main.clear()
                        main.append(GamePartial(o).getPartial(controller))
                    }
                }
            }
        }


    }
}