package htwdd.chessgame.client.model

import htwdd.chessgame.client.util.copy
import kotlinx.serialization.Serializable

@Serializable
data class PieceSet(var activePieces: HashMap<String, Piece> = HashMap(),
                    var capturedPieces: HashSet<Piece> = HashSet()) {
    fun deepCopy(
            activePieces: HashMap<String, Piece> = this.activePieces.copy()!!,
            capturedPieces: HashSet<Piece> = HashSet(this.capturedPieces)
    ) = PieceSet(activePieces, capturedPieces)
}