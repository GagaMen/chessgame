package htwdd.chessgame.client.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerHashMap(val player: HashMap<Int, Player>)