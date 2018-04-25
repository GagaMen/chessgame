package htwdd.chessgame.client.model

import kotlinx.serialization.Serializable

@Serializable
class PieceSet(var activePieces: HashMap<String, Piece> = HashMap(),
               var capturedPieces: HashSet<Piece> = HashSet())