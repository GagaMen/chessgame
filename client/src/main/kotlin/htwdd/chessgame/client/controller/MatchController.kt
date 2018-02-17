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
                val playerWhiteSelect = arg[0]
                val playerBlackSelect = arg[1]

                if (
                        (playerWhiteSelect != null && playerWhiteSelect is HTMLSelectElement && playerWhiteSelect.value != "-1") &&
                        (playerBlackSelect != null && playerBlackSelect is HTMLSelectElement && playerBlackSelect.value != "-1")
                ) {
                    val players = HashMap<PieceColor, Player?>()
                    val playerWhiteID = playerWhiteSelect.value.toInt()
                    val playerBlackID = playerBlackSelect.value.toInt()

                    if (client.players.containsKey(playerWhiteID) && client.players.containsKey(playerBlackID)) {
                        players[PieceColor.WHITE] = client.players[playerWhiteID]
                        players[PieceColor.BLACK] = client.players[playerBlackID]
                    } else {
                        //todo: throw exception
                    }

                    val pieceSets = HashMap<PieceColor, PieceSet>()
                    pieceSets[PieceColor.WHITE] = PieceSet(pieceColor = PieceColor.WHITE, initialize = true)
                    pieceSets[PieceColor.BLACK] = PieceSet(pieceColor = PieceColor.BLACK, initialize = true)

                    val match = Match(players, pieceSets, PieceColor.WHITE, mutableListOf())
                    client.addMatch(match)

                    //reset form
                    playerWhiteSelect.value = "-1"
                    playerBlackSelect.value = "-1"
                } else {
                    //todo: throw exception
                }
            }
        }
    }

    private fun removeMatchAction(arg: Any?) {
        when (arg) {
            is BUTTON -> {
                val matchId = arg.attributes["data-id"]?.toInt()
                if (matchId != null) {
                    client.removeMatch(matchId)
                } else {
                    //todo error
                }
            }
        }
    }
}