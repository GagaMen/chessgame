package htwdd.chessgame.server.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.io.Serializable
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@DatabaseTable(tableName = "Player")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class Player(
        @DatabaseField(generatedId = true)
        @XmlElement
        val id: Int = 0,
        @DatabaseField(canBeNull = false)
        @XmlElement
        var name: String = "",
        @DatabaseField(canBeNull = false)
        @XmlElement
        var password: String = ""
) : Serializable