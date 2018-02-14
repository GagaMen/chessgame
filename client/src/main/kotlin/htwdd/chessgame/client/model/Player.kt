package htwdd.chessgame.client.model

import htwdd.chessgame.client.util.Observable

data class Player(var name: String,
                  var password: String) : Observable() {
    var id: Int = 0
}