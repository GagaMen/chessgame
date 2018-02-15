package htwdd.chessgame.client.view

import htwdd.chessgame.client.controller.MatchController
import htwdd.chessgame.client.model.Client
import htwdd.chessgame.client.model.ViewState
import htwdd.chessgame.client.partial.MatchPartial
import htwdd.chessgame.client.partial.MatchTablePartial
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

class MatchView(private val controller: MatchController) : Observer {
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
                    ViewState.MATCH -> {
                        root.removeClass("box--shadow")
                        main.clear()
                        main.append(MatchPartial().getPartial(controller))
                        render()
                    }
                    "updateMatchTable" -> {
                        val table = main.getElementsByClassName("table--match")[0]
                        table?.replaceWith(MatchTablePartial().getPartial(controller))
                    }
                }
            }
        }
    }
}