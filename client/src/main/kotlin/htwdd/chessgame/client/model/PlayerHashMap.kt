package htwdd.chessgame.client.model

import kotlinx.serialization.Serializable

@Serializable
class PlayerHashMap(val player: HashMap<Int, Player>)