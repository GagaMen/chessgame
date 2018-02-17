package htwdd.chessgame.client.controller

import htwdd.chessgame.client.model.*
import htwdd.chessgame.client.view.MatchView
import kotlinx.html.BUTTON
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.get

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
                val players = HashMap<PieceColor, Player?>()
                val pieceSets = HashMap<PieceColor, PieceSet>()
                val playerWhiteSelect = arg[0] as? HTMLSelectElement ?: return
                val playerBlackSelect = arg[1] as? HTMLSelectElement ?: return

                if (playerWhiteSelect.value == "-1" || playerBlackSelect.value == "-1") {
                    // empty type
                    return
                }

                val playerWhiteID = playerWhiteSelect.value.toIntOrNull() ?: return
                val playerBlackID = playerBlackSelect.value.toIntOrNull() ?: return

                if (!client.players.containsKey(playerWhiteID) || !client.players.containsKey(playerBlackID)) {
                    // don't contains players
                    return
                }

                players[PieceColor.WHITE] = client.players[playerWhiteID]
                players[PieceColor.BLACK] = client.players[playerBlackID]
                pieceSets[PieceColor.WHITE] = PieceSet(pieceColor = PieceColor.WHITE, initialize = true)
                pieceSets[PieceColor.BLACK] = PieceSet(pieceColor = PieceColor.BLACK, initialize = true)

                val match = Match(players, pieceSets, PieceColor.WHITE, mutableListOf())
                client.addMatch(match)

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
                client.removeMatch(matchId)
            }
        }
    }
}