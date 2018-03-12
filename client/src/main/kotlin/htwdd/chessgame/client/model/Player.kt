package htwdd.chessgame.client.model

import htwdd.chessgame.client.util.Observable
import kotlinx.serialization.SerialId
import kotlinx.serialization.Serializable

@Serializable
data class Player(var id: Int = 0,
                  var name: String,
                  var password: String) : Observable()