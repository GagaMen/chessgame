package htwdd.chessgame.server.model

import java.io.Serializable

class PieceSet(var activePieces: HashMap<Pair<Int, Int>, Piece> = HashMap(),
               var capturedPieces: HashSet<Piece> = HashSet()) : Serializable