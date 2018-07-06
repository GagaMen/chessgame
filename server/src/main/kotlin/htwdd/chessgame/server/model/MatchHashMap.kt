package htwdd.chessgame.server.model

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

/**
 * Data wrapper for a hash map of matches. Necessary for JSON serialization/deserialization.
 *
 * @author Felix Dimmel
 *
 * @property matches Hash map of matches
 *
 * @since 1.0.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class MatchHashMap(
        @XmlElement
        val matches: HashMap<Int, Match> = HashMap()
)