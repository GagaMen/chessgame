package htwdd.chessgame.client.model

import kotlinx.serialization.Serializable

@Serializable
data class PieceSetHashMap(val pieceSets: HashMap<PieceColor, PieceSet>)