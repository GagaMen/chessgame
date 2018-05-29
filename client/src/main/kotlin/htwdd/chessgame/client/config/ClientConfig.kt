package htwdd.chessgame.client.config

import kotlinx.serialization.Serializable
import kotlin.browser.window

@Serializable
data class ClientConfig(
        private val useWindowLocation: Boolean = true,
        var serverRootUrl: String = ""
) {
    init {
        if (useWindowLocation) serverRootUrl = window.location.toString()
    }
}