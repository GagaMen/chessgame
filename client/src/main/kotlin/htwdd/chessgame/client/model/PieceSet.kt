package htwdd.chessgame.client.model

import kotlinx.serialization.Serializable

@Serializable
class PieceSet(var activePieces: HashMap<Pair<Int, Int>, Piece> = HashMap(),
               var capturedPieces: HashSet<Piece> = HashSet())