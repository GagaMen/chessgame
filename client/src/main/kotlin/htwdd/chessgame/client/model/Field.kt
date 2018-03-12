package htwdd.chessgame.client.model

import kotlinx.serialization.Serializable

@Serializable
class Field(var row: Int,
            var column: Int) {
    val id: Int = 0

    fun asPair(): Pair<Int, Int> {
        return Pair(row, column)
    }
}