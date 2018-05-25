package htwdd.chessgame.server.model

import java.io.Serializable
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

/**
 * @author Felix Dimmel
 *
 * @property activePieces Contains all active pieces. Key: Position of Piece as Pair; Value: Piece
 * @property capturedPieces Contains all captured pieces
 *
 * @since 1.0.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class PieceSet(
        @XmlElement
        var activePieces: HashMap<Pair<Int, Int>, Piece> = HashMap(),
        @XmlElement
        var capturedPieces: HashSet<Piece> = HashSet()
) : Serializable