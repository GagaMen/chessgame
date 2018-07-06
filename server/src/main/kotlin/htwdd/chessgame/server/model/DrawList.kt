package htwdd.chessgame.server.model

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

/**
 * Data wrapper for a list of draws. Necessary for JSON serialization/deserialization.
 *
 * @author Felix Dimmel
 *
 * @property draws Mutable list of draws
 *
 * @since 1.0.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class DrawList(
        @XmlElement
        val draws: MutableList<Draw> = mutableListOf()
)