package htwdd.chessgame.client.config

import kotlinx.serialization.Serializable

@Serializable
data class ClientConfig(val serverRootUrl: String = "")