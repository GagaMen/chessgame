package htwdd.chessgame.server.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "Field")
class Field(@DatabaseField(generatedId = true) val id: Int = 0,
            @DatabaseField(canBeNull = false) var row: Int = 0,
            @DatabaseField(canBeNull = false) var column: Int = 0) {
    fun asPair(): Pair<Int, Int> {
        return Pair(row, column)
    }
}