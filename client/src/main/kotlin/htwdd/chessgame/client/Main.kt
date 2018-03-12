package htwdd.chessgame.client

import htwdd.chessgame.client.controller.MatchController
import htwdd.chessgame.client.controller.PlayerController
import htwdd.chessgame.client.controller.StartController
import htwdd.chessgame.client.model.*
import htwdd.chessgame.client.util.RequestUtility.Companion.get
import kotlinx.serialization.json.JSON
import org.w3c.xhr.XMLHttpRequest

fun main(args: Array<String>) {
    val client = loadData()
    StartController(client)
    PlayerController(client)
    MatchController(client)
}

private fun loadData(): Client {
    val client = Client()

    get("http://127.0.0.1:8080/player") {
        if (it.target is XMLHttpRequest) {
            val playerHashMap = JSON.parse<PlayerHashMap>((it.target as XMLHttpRequest).responseText)
            playerHashMap.player.forEach { client.addPlayer(it.value) }
        }
    }

    get("http://127.0.0.1:8080/match") {
        if (it.target is XMLHttpRequest) {
            val matchHashMap = JSON.parse<MatchHashMap>((it.target as XMLHttpRequest).responseText)
            matchHashMap.matches.forEach { (matchId, match) ->

                get("http://127.0.0.1:8080/match/$matchId/draw") {
                    if (it.target is XMLHttpRequest) {
                        val drawList = JSON.parse<DrawList>((it.target as XMLHttpRequest).responseText)
                        match.history = drawList.draws
                    }
                }

                client.addMatch(match)
            }
        }
    }

    return client
}