package htwdd.chessgame.server.model

import java.io.Serializable
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class PieceSet(
        @XmlElement
        var activePieces: HashMap<Pair<Int, Int>, Piece> = HashMap(),
        @XmlElement
        var capturedPieces: HashSet<Piece> = HashSet()
) : Serializable