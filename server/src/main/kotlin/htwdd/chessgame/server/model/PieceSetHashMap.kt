package htwdd.chessgame.server.model

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

/**
 * Data wrapper for a hash map of pieceSets. Necessary for JSON serialization/deserialization.
 *
 * @author Felix Dimmel
 *
 * @property pieceSets Hash map of pieceSets
 *
 * @since 1.0.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class PieceSetHashMap(
        @XmlElement
        val pieceSets: HashMap<PieceColor, PieceSet> = HashMap()
)