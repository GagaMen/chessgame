package htwdd.chessgame.server.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.io.Serializable

@DatabaseTable(tableName = "Player")
data class Player(@DatabaseField(generatedId = true) val id: Int = 0,
                  @DatabaseField var name: String = "",
                  @DatabaseField var password: String = ""): Serializable