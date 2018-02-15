package htwdd.chessgame.client.view

import htwdd.chessgame.client.controller.Controller
import htwdd.chessgame.client.model.Client
import htwdd.chessgame.client.model.Player
import htwdd.chessgame.client.model.ViewState
import htwdd.chessgame.client.partial.PlayerEditFormPartial
import htwdd.chessgame.client.partial.PlayerFormPartial
import htwdd.chessgame.client.partial.PlayerPartial
import htwdd.chessgame.client.partial.PlayerTablePartial
import htwdd.chessgame.client.util.Observable
import htwdd.chessgame.client.util.Observer
import kotlinx.html.dom.create
import kotlinx.html.js.div
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.dom.clear
import kotlin.dom.removeClass

class PlayerView(private val controller: Controller) : Observer {

    private val root = document.getElementsByClassName("app")[0] as HTMLDivElement
    private var main: HTMLElement = document.create.div(classes = "main")

    private fun render() {
        root.clear()
        root.appendChild(main)
    }

    override fun update(o: Observable?, arg: Any?) {
        when (o) {
            is Client -> {
                when (arg) {
                    ViewState.PLAYER -> {
                        root.removeClass("box--shadow")
                        main.clear()
                        main.appendChild(PlayerPartial().getPartial(controller))
                        render()
                    }
                    "updatePlayerTable" -> {
                        val table = main.getElementsByClassName("table--player")[0]
                        table?.replaceWith(PlayerTablePartial().getPartial(controller))
                    }
                    "resetPlayerForm" -> {
                        val form = main.getElementsByClassName("form--player")[0]
                        form?.replaceWith(PlayerFormPartial().getPartial(controller))
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
        }
    }
}