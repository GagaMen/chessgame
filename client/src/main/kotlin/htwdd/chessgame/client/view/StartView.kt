package htwdd.chessgame.client.view

import htwdd.chessgame.client.controller.StartController
import htwdd.chessgame.client.model.Client
import htwdd.chessgame.client.model.ViewState
import htwdd.chessgame.client.partial.StartPartial
import htwdd.chessgame.client.util.Observable
import kotlinx.html.dom.create
import kotlinx.html.js.div
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.dom.addClass
import kotlin.dom.clear

class StartView(private val controller: StartController) : View {

    private val root = document.getElementsByClassName("app")[0] as HTMLDivElement
    private var main: HTMLElement = document.create.div(classes = "main")

    override fun render() {
        root.clear()
        root.appendChild(main)
    }

    override fun update(o: Observable?, arg: Any?) {
        when (o) {
            is Client -> {
                when (arg) {
                    ViewState.START -> {
                        root.addClass("box--shadow")
                        main.clear()
                        main.appendChild(StartPartial().getPartial(controller))
                        render()
                    }
                }
            }
        }
    }
}