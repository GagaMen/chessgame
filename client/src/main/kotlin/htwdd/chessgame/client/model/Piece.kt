package htwdd.chessgame.client.model

import kotlinx.serialization.Serializable

@Serializable
data class Piece(var type: PieceType,
                 var position: Field) {
    fun deepCopy(
            type: PieceType = this.type,
            position: Field = this.position.copy()
    ) = Piece(type, position)
}