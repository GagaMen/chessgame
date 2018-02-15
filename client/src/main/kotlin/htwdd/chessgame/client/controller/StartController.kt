package htwdd.chessgame.client.controller

import htwdd.chessgame.client.model.Client
import htwdd.chessgame.client.model.ViewState
import htwdd.chessgame.client.view.StartView

class StartController(private val client: Client) : Controller {

    //private var client: Client
    private val startView: StartView = StartView(this)

    init {
        client.addObserver(startView)
        client.changeState(ViewState.START)
    }

    override fun actionPerformed(e: Any, arg: Any?) {
        when (e) {
            "showPlayer" -> client.changeState(ViewState.PLAYER)
            "showMatch" -> client.changeState(ViewState.MATCH)
        }
    }
}
