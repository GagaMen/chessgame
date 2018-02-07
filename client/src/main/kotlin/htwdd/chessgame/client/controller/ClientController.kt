package htwdd.chessgame.client.controller

import htwdd.chessgame.client.model.*
import htwdd.chessgame.client.view.MainView
import kotlinx.html.BUTTON
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.get
import kotlin.collections.set

class ClientController : Controller {

    private var client: Client
    private val mainView: MainView = MainView(this)

    init {
        client = loadData()
        client.addObserver(mainView)
    }

    private fun loadData(): Client {
        //todo: load data from server

        // test data ------------------------------------------------
        val client = Client()
        val player1 = Player("Player1", "123456")
        val player2 = Player("Player2", "123456")

        val players = HashMap<PieceColor, Player?>()
        players[PieceColor.WHITE] = player1
        players[PieceColor.BLACK] = player2

        val pieceSets = HashMap<PieceColor, PieceSet>()
        pieceSets[PieceColor.WHITE] = PieceSet(pieceColor = PieceColor.WHITE, initialize = true)
        pieceSets[PieceColor.BLACK] = PieceSet(pieceColor = PieceColor.BLACK, initialize = true)

        val match = Match(players, pieceSets, PieceColor.WHITE, mutableListOf())
        match.addObserver(mainView)

        client.addPlayer(player1)
        client.addPlayer(player2)
        client.addMatch(match)

        //------------------------------------------------------------

        return client
    }

    override fun actionPerformed(e: Any, arg: Any?) {
        when (e) {
            "showStart" -> client.changeState(ViewState.START)
            "showPlayer" -> client.changeState(ViewState.PLAYER)
            "showMatch" -> client.changeState(ViewState.MATCH)
            "startMatch" -> startMatch(arg)
            "addPlayer" -> addPlayer(arg)
            "editPlayer" -> editPlayer(arg)
            "updatePlayer" -> updatePlayer(arg)
            "removePlayer" -> removePlayer(arg)
            "addMatch" -> addMatch(arg)
            "removeMatch" -> removeMatch(arg)
            "addDraw" -> addDraw(arg)
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

                    client.addMatch(Match(players, pieceSets, PieceColor.WHITE, mutableListOf()))

                    //reset form
                    playerWhiteSelect.value = "-1"
                    playerBlackSelect.value = "-1"
                } else {
                    //todo: throw exception
                }
            }
        }
    }

    private fun startMatch(arg: Any?) {
        when (arg) {
            is BUTTON -> {
                val matchId = arg.attributes["data-id"]?.toInt()
                if (matchId != null && client.matches.containsKey(matchId)) {
                    val match = client.matches[matchId]
                    mainView.update(match, ViewState.GAME)
                } else {
                    //todo error
                }
            }
        }
    }

    private fun removeMatch(arg: Any?) {
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
                    mainView.update(player, "editPlayer")
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

    private fun addDraw(arg: Any?) {
        when (arg) {
            is Pair<*, *> -> {
                val match = arg.first
                val draw = arg.second

                if (match is Match && draw is Draw) {
                    match.addDraw(draw)
                }
            }
        }
    }
}
