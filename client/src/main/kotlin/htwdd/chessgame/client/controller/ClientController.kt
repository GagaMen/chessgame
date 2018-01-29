package htwdd.chessgame.client.controller

import htwdd.chessgame.client.model.*
import htwdd.chessgame.client.view.MainView
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.get

class ClientController : Controller {

    var client: Client
    val mainView: MainView

    init {
        client = loadData()
        mainView = MainView(this)

        client.addObserver(mainView)
    }

    fun loadData(): Client {
        //todo: load data from server
        return Client()
    }

    override fun actionPerformed(e: Any, arg: Any?) {
        when (e) {
            "addPlayer" -> addPlayer(arg)
            "addMatch" -> addMatch(arg)
            "showPlayer" -> client.changeState(ViewState.PLAYER)
            "showMatch" -> client.changeState(ViewState.MATCH)
        }
    }

    fun getMatches(): HashMap<Int, Match> {
        return client.matches
    }

    fun getPlayers(): HashMap<Int, Player> {
        return client.players
    }

    private fun addMatch(arg: Any?) {
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

                    client.addMatch(Match(players, pieceSets, emptyList()))

                    //reset form
                    playerWhiteSelect.value = "-1"
                    playerBlackSelect.value = "-1"
                } else {
                    //todo: throw exception
                }
            }
        }
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
}
