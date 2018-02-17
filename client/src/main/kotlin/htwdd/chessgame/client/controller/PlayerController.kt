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
                val name = arg[0]
                val password = arg[1]

                if (
                        (name != null && name is HTMLInputElement && name.type == "text" && name.value != "") &&
                        (password != null && password is HTMLInputElement && password.type == "password" && password.value != "")
                ) {
                    client.addPlayer(Player(name.value, password.value))

                    //reset form
                    name.value = ""
                    password.value = ""
                } else {
                    //todo: throw exception
                }
            }
        }
    }

    private fun editPlayerAction(arg: Any?) {
        when (arg) {
            is BUTTON -> {
                val playerId = arg.attributes["data-id"]?.toInt()
                if (playerId != null && client.players.containsKey(playerId)) {
                    val player = client.players[playerId]
                    playerView.update(player, "editPlayerAction")
                } else {
                    //todo error
                }
            }
        }
    }

    private fun updatePlayerAction(arg: Any?) {
        when (arg) {
            is HTMLFormElement -> {
                val playerId = arg.attributes["data-id"]?.value?.toInt()
                val passwordInput = arg.getElementsByClassName("player--password")[0] as HTMLInputElement
                val newPassword = passwordInput.value
                if (playerId != null && newPassword != "") {
                    client.updatePlayer(playerId, newPassword)
                }
            }
        }
    }

    private fun removePlayerAction(arg: Any?) {
        when (arg) {
            is BUTTON -> {
                val playerId = arg.attributes["data-id"]?.toInt()
                if (playerId != null) {
                    client.removePlayer(playerId)
                } else {
                    //todo error
                }
            }
        }
    }
}