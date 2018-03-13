package htwdd.chessgame.client.controller

import htwdd.chessgame.client.model.Client
import htwdd.chessgame.client.model.Player
import htwdd.chessgame.client.model.ViewState
import htwdd.chessgame.client.util.RequestUtility
import htwdd.chessgame.client.util.RequestUtility.Companion.post
import htwdd.chessgame.client.view.PlayerView
import kotlinx.html.BUTTON
import kotlinx.serialization.json.JSON
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.get
import org.w3c.xhr.XMLHttpRequest

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

                if (name.type != "text" || password.type != "password") return
                if (name.value == "" || password.value == "") return

                post("http://localhost:8080/player", Pair("name", name.value), Pair("password", password.value)) {
                    if (it.target is XMLHttpRequest) {
                        val player = JSON.parse<Player>((it.target as XMLHttpRequest).responseText)
                        client.addPlayer(player)
                    }
                }

                name.value = ""
                password.value = ""
            }
        }
    }

    private fun editPlayerAction(arg: Any?) {
        when (arg) {
            is BUTTON -> {
                val playerId = arg.attributes["data-id"]?.toIntOrNull() ?: return

                if (!client.players.containsKey(playerId)) return

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

                if (newPassword == "") return

                RequestUtility.patch("http://localhost:8080/player/$playerId", Pair("password", newPassword)) {
                    if (it.target is XMLHttpRequest) {
                        val response = kotlin.js.JSON.parse<Boolean>((it.target as XMLHttpRequest).responseText)
                        if (response) client.updatePlayer(playerId, newPassword)
                    }
                }
            }
        }
    }

    private fun removePlayerAction(arg: Any?) {
        when (arg) {
            is BUTTON -> {
                val playerId = arg.attributes["data-id"]?.toIntOrNull() ?: return

                RequestUtility.delete("http://localhost:8080/player/$playerId") {
                    if (it.target is XMLHttpRequest) {
                        val response = kotlin.js.JSON.parse<Boolean>((it.target as XMLHttpRequest).responseText)
                        if (response) client.removePlayer(playerId)
                    }
                }
            }
        }
    }
}