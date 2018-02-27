package htwdd.chessgame.server.model

import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.PrimaryKey

data class Player(val name: String = "", val password: String = "") {
    @PrimaryKey
    @AutoIncrement
    var id: Int = 0
}