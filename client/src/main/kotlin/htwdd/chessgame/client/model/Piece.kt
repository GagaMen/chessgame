package htwdd.chessgame.client.model

import kotlinx.serialization.Serializable

@Serializable
class Piece(var type: PieceType,
            var position: Field)