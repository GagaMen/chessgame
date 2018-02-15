package htwdd.chessgame.client.view

import htwdd.chessgame.client.controller.Controller
import htwdd.chessgame.client.model.Client
import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.partial.GamePartial
import htwdd.chessgame.client.partial.GamePropertiesPartial
import htwdd.chessgame.client.util.Observable
import kotlinx.html.dom.create
import kotlinx.html.js.div
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.dom.clear

class GameView(private val controller: Controller) : View {
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
                    is Match -> {
                        main.clear()
                        main.appendChild(GamePartial(arg).getPartial(controller))
                        render()
                    }
                }
            }
            is Match -> {
                when (arg) {
                    "updateGameProperties" -> {
                        val properties = main.getElementsByClassName("properties")[0]
                        properties?.replaceWith(GamePropertiesPartial(o).getPartial(controller))
                    }
                }
            }
        }
    }
}