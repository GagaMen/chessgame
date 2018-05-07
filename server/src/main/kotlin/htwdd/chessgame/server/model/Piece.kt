package htwdd.chessgame.server.model

import java.io.Serializable

class Piece(var type: PieceType,
            var position: Field) : Serializable