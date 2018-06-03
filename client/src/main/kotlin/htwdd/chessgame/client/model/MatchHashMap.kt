package htwdd.chessgame.client.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchHashMap(val matches: HashMap<Int, Match>)