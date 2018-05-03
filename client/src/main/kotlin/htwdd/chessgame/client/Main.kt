package htwdd.chessgame.client

import htwdd.chessgame.client.controller.MatchController
import htwdd.chessgame.client.controller.PlayerController
import htwdd.chessgame.client.controller.StartController
import htwdd.chessgame.client.model.Client
import htwdd.chessgame.client.model.MatchHashMap
import htwdd.chessgame.client.model.PlayerHashMap
import htwdd.chessgame.client.util.RequestUtility.Companion.get
import htwdd.chessgame.client.util.RequestUtility.Companion.loadJSONConfiguration
import kotlinx.coroutines.experimental.await
import kotlinx.coroutines.experimental.launch
import kotlinx.serialization.json.JSON
import org.w3c.xhr.XMLHttpRequest
import kotlin.js.Promise

fun main(args: Array<String>) {
    launch {
        val client = loadData().await()
        StartController(client)
        PlayerController(client)
        MatchController(client)
    }
}

private fun loadData(): Promise<Client> {
    return Promise { resolve, reject ->
        launch {
            val client = Client()

            loadJSONConfiguration {
                if (it.target is XMLHttpRequest &&
                        (it.target as XMLHttpRequest).readyState == 4.toShort() &&
                        (it.target as XMLHttpRequest).status == 200.toShort()
                ) {
                    client.config = JSON.parse((it.target as XMLHttpRequest).responseText)
                }
            }.await()

            get("${client.config.serverRootUrl}/player") {
                if (it.target is XMLHttpRequest) {
                    val playerHashMap = JSON.parse<PlayerHashMap>((it.target as XMLHttpRequest).responseText)
                    playerHashMap.player.forEach { client.addPlayer(it.value) }
                }
            }.await()

            get("${client.config.serverRootUrl}/match", Pair("includePieceSets", false), Pair("includeHistory", false)) {
                if (it.target is XMLHttpRequest) {
                    val matchHashMap = JSON.parse<MatchHashMap>((it.target as XMLHttpRequest).responseText)
                    matchHashMap.matches.forEach { client.addMatch(it.value) }
                }
            }.await()

            resolve(client)
        }
    }
}