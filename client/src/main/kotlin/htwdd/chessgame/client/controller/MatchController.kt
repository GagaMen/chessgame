package htwdd.chessgame.client.controller

import htwdd.chessgame.client.model.Client
import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.Player
import htwdd.chessgame.client.model.ViewState
import htwdd.chessgame.client.util.RequestUtility.Companion.delete
import htwdd.chessgame.client.util.RequestUtility.Companion.post
import htwdd.chessgame.client.view.MatchView
import kotlinx.html.BUTTON
import kotlinx.serialization.json.JSON
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.get
import org.w3c.xhr.XMLHttpRequest

class MatchController(val client: Client) : Controller {

    private val matchView = MatchView(this)
    private val gameController = GameController(client)

    init {
        client.addObserver(matchView)
    }

    override fun actionPerformed(e: Any, arg: Any?) {
        when (e) {
            "showStartAction" -> showStartAction()
            "showMatchAction" -> showMatchAction()
            "addMatchAction" -> addMatchAction(arg)
            "startMatchAction" -> gameController.actionPerformed(e, arg)
            "removeMatchAction" -> removeMatchAction(arg)
        }
    }

    fun getMatches(): HashMap<Int, Match> {
        return client.matches
    }

    fun getPlayers(): HashMap<Int, Player> {
        return client.players
    }

    private fun showStartAction() {
        client.changeState(ViewState.START)
    }

    private fun showMatchAction() {
        client.changeState(ViewState.MATCH)
    }

    private fun addMatchAction(arg: Any?) {
        when (arg) {
            is HTMLFormElement -> {
                val playerWhiteSelect = arg[0] as? HTMLSelectElement ?: return
                val playerBlackSelect = arg[1] as? HTMLSelectElement ?: return

                if (playerWhiteSelect.value == "-1" || playerBlackSelect.value == "-1") return

                val playerWhiteID = playerWhiteSelect.value.toIntOrNull() ?: return
                val playerBlackID = playerBlackSelect.value.toIntOrNull() ?: return

                if (!client.players.containsKey(playerWhiteID) || !client.players.containsKey(playerBlackID)) return

                post("http://localhost:8080/match", Pair("playerWhiteId", playerWhiteID), Pair("playerBlackId", playerBlackID)) {
                    if (it.target is XMLHttpRequest) {
                        val match = JSON.parse<Match>((it.target as XMLHttpRequest).responseText)
                        match.setPieceSetsByMatchCode()
                        client.addMatch(match)
                    }
                }

                //reset form
                playerWhiteSelect.value = "-1"
                playerBlackSelect.value = "-1"
            }
        }
    }

    private fun removeMatchAction(arg: Any?) {
        when (arg) {
            is BUTTON -> {
                val matchId = arg.attributes["data-id"]?.toIntOrNull() ?: return

                delete("http://localhost:8080/match/$matchId") {
                    if (it.target is XMLHttpRequest) {
                        val response = kotlin.js.JSON.parse<Boolean>((it.target as XMLHttpRequest).responseText)
                        if (response) client.removeMatch(matchId)
                    }
                }
            }
        }
    }
}