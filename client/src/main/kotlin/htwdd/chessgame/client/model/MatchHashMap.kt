package htwdd.chessgame.client.model

import kotlinx.serialization.Serializable

@Serializable
class MatchHashMap(val matches: HashMap<Int, Match>)