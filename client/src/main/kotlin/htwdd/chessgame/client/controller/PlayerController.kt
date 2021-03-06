package htwdd.chessgame.client.controller

import htwdd.chessgame.client.model.Client
import htwdd.chessgame.client.model.Player
import htwdd.chessgame.client.model.ViewState
import htwdd.chessgame.client.util.RequestUtility.Companion.delete
import htwdd.chessgame.client.util.RequestUtility.Companion.patch
import htwdd.chessgame.client.util.RequestUtility.Companion.post
import htwdd.chessgame.client.view.PlayerView
import kotlinx.html.BUTTON
import kotlinx.serialization.json.JSON
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.get
import org.w3c.xhr.XMLHttpRequest

class PlayerController(client: Client) : Controller(client) {

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

                post("${client.config.serverRootUrl}players", Pair("name", name.value), Pair("password", password.value)) {
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

                patch("${client.config.serverRootUrl}players/$playerId", Pair("password", newPassword)) {
                    if (it.target is XMLHttpRequest && (it.target as XMLHttpRequest).status == 200.toShort()) {
                        client.updatePlayer(playerId, newPassword)
                    }
                }
            }
        }
    }

    private fun removePlayerAction(arg: Any?) {
        when (arg) {
            is BUTTON -> {
                val playerId = arg.attributes["data-id"]?.toIntOrNull() ?: return

                delete("${client.config.serverRootUrl}players/$playerId") {
                    if (it.target is XMLHttpRequest && (it.target as XMLHttpRequest).status == 200.toShort()) {
                        client.removePlayer(playerId)
                    }
                }
            }
        }
    }
}