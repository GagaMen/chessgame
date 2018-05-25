package htwdd.chessgame.server.model

import java.io.Serializable

/**
 * @author Felix Dimmel
 *
 * @property type Type of piece
 * @property position Position of the chessboard field
 *
 * @since 1.0.0
 */
class Piece(var type: PieceType,
            var position: Field) : Serializable