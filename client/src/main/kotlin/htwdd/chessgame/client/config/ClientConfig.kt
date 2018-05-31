package htwdd.chessgame.client.config

import kotlinx.serialization.Serializable
import kotlin.browser.window

@Serializable
data class ClientConfig(
        private val useWindowLocation: Boolean = true,
        var serverRootUrl: String = "",
        var pollingDelayTime: Int = 5000
) {
    init {
        if (useWindowLocation) serverRootUrl = window.location.toString()
    }
}