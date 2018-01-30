package htwdd.chessgame.client.model

import htwdd.chessgame.client.util.Observable
import kotlin.js.Date

data class Player(var name: String,
                  var password: String) : Observable() {

    var id: Int = Date().getTime().hashCode()
}