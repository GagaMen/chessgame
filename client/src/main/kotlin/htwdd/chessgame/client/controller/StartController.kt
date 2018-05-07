package htwdd.chessgame.client.controller

import htwdd.chessgame.client.model.Client
import htwdd.chessgame.client.model.ViewState
import htwdd.chessgame.client.view.StartView

class StartController(client: Client) : Controller(client) {

    private val startView: StartView = StartView(this)

    init {
        client.addObserver(startView)
        client.changeState(ViewState.START)
    }

    override fun actionPerformed(e: Any, arg: Any?) {
        when (e) {
            "showPlayerAction" -> showPlayerAction()
            "showMatchAction" -> showMatchAction()
        }
    }

    private fun showPlayerAction() {
        client.changeState(ViewState.PLAYER)
    }

    private fun showMatchAction() {
        client.changeState(ViewState.MATCH)
    }
}
