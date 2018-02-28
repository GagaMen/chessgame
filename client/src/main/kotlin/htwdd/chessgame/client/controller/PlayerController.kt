package htwdd.chessgame.client.controller

import htwdd.chessgame.client.model.Client
import htwdd.chessgame.client.model.Player
import htwdd.chessgame.client.model.ViewState
import htwdd.chessgame.client.view.PlayerView
import kotlinx.html.BUTTON
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.get

class PlayerController(val client: Client) : Controller {

    private val playerView = PlayerView(this)

    init {
        client.addObserver(playerView)
    }

    override fun actionPerformed(e: Any, arg: Any?) {
        when (e) {
            "showStartAction" -> showStartAction()
            "showPlayerAction" -> showPlayerAction()
            "addPlayerAction" -> addPlayerAction(arg)
            "editPlayerAction" -> editPlayerAction(arg)
            "updatePlayerAction" -> updatePlayerAction(arg)
            "removePlayerAction" -> removePlayerAction(arg)
        }
    }

    fun getPlayers(): HashMap<Int, Player> {
        return client.players
    }

    private fun showStartAction() {
        client.changeState(ViewState.START)
    }

    private fun showPlayerAction() {
        client.changeState(ViewState.PLAYER)
    }

    private fun addPlayerAction(arg: Any?) {
        when (arg) {
            is HTMLFormElement -> {
                val name = arg[0] as? HTMLInputElement ?: return
                val password = arg[1] as? HTMLInputElement ?: return

                if (name.type != "text" || password.type != "password") {
                    // wrong type
                    return
                }

                if (name.value == "" || password.value == "") {
                    // empty value
                    return
                }

                client.addPlayer(Player(name.value, password.value))
                name.value = ""
                password.value = ""
            }
        }
    }

    private fun editPlayerAction(arg: Any?) {
        when (arg) {
            is BUTTON -> {
                val playerId = arg.attributes["data-id"]?.toIntOrNull() ?: return

                if (!client.players.containsKey(playerId)) {
                    return
                }

                playerView.update(client.players[playerId], "editPlayer")
            }
        }
    }

    private fun updatePlayerAction(arg: Any?) {
        when (arg) {
            is HTMLFormElement -> {
                val playerId = arg.attributes["data-id"]?.value?.toIntOrNull() ?: return
                val passwordInput = arg.getElementsByClassName("player--password")[0] as? HTMLInputElement ?: return
                val newPassword = passwordInput.value

                if (newPassword == "") {
                    // empty value
                    return
                }

                client.updatePlayer(playerId, newPassword)
            }
        }
    }

    private fun removePlayerAction(arg: Any?) {
        when (arg) {
            is BUTTON -> {
                val playerId = arg.attributes["data-id"]?.toIntOrNull() ?: return
                client.removePlayer(playerId)
            }
        }
    }
}