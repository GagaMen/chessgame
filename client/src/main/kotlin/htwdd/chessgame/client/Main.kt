package htwdd.chessgame.client

import htwdd.chessgame.client.controller.MatchController
import htwdd.chessgame.client.controller.PlayerController
import htwdd.chessgame.client.controller.StartController
import htwdd.chessgame.client.model.*
import htwdd.chessgame.client.util.RequestUtility.Companion.get
import htwdd.chessgame.client.util.RequestUtility.Companion.head
import org.w3c.xhr.XMLHttpRequest

fun main(args: Array<String>) {
    val client = loadData()
    StartController(client)
    PlayerController(client)
    MatchController(client)

    get("http://127.0.0.1:8080/match") {
        if (it.target is XMLHttpRequest) {
            println((it.target as XMLHttpRequest).responseText)
        }
    }

    head("http://127.0.0.1:8080/match")
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
    //match.addObserver(mainView)

    client.addPlayer(player1)
    client.addPlayer(player2)
    client.addMatch(match)

    //------------------------------------------------------------

    return client
}