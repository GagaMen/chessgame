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
            "showStart" -> client.changeState(ViewState.START)
            "showPlayer" -> client.changeState(ViewState.PLAYER)
            "addPlayer" -> addPlayer(arg)
            "editPlayer" -> editPlayer(arg)
            "updatePlayer" -> updatePlayer(arg)
            "removePlayer" -> removePlayer(arg)
        }
    }

    fun getPlayers(): HashMap<Int, Player> {
        return client.players
    }

    private fun addPlayer(arg: Any?) {
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

    private fun editPlayer(arg: Any?) {
        when (arg) {
            is BUTTON -> {
                val playerId = arg.attributes["data-id"]?.toInt()
                if (playerId != null && client.players.containsKey(playerId)) {
                    val player = client.players[playerId]
                    playerView.update(player, "editPlayer")
                } else {
                    //todo error
                }
            }
        }
    }

    private fun updatePlayer(arg: Any?) {
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

    private fun removePlayer(arg: Any?) {
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