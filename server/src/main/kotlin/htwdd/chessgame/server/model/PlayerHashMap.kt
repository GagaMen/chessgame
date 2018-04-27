package htwdd.chessgame.server.model

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class PlayerHashMap(
        @XmlElement
        val player: HashMap<Int, Player> = HashMap()
)