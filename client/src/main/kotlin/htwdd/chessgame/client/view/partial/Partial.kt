package htwdd.chessgame.client.view.partial

import htwdd.chessgame.client.controller.Controller
import org.w3c.dom.HTMLElement

interface Partial {
    fun getView(controller: Controller): HTMLElement
}