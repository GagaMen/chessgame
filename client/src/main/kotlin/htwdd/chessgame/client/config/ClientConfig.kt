package htwdd.chessgame.client.config

import kotlinx.serialization.Serializable
import kotlin.browser.window

@Serializable
data class ClientConfig(
        private val useWindowLocation: Boolean = true,
        var serverRootUrl: String = "",
        private var apiRootEntryPoint: String = "api/",
        var pollingDelayTime: Int = 5000
) {
    init {
        if (useWindowLocation) serverRootUrl = window.location.toString()
        serverRootUrl = "$serverRootUrl$apiRootEntryPoint"
    }
}