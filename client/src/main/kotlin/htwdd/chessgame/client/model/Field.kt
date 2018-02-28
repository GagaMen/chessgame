package htwdd.chessgame.client.model

class Field(var row: Int,
            var column: Int) {
    fun getAsPair(): Pair<Int, Int> {
        return Pair(row, column)
    }
}