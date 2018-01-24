package htwdd.chessgame.client.model

import kotlin.js.Date

data class Player(var name: String,
                  var password: String) {

    var id: Int = Date().getTime().hashCode()
}