package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.Controller
import org.w3c.dom.HTMLElement

interface Partial {
    fun getPartial(controller: Controller): HTMLElement
}