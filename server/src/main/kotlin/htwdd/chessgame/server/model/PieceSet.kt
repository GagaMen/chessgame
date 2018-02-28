package htwdd.chessgame.server.model

class PieceSet(var activePieces: HashMap<Pair<Int, Int>, Piece> = HashMap(),
               var capturedPieces: HashSet<Piece> = HashSet(),
               val pieceColor: PieceColor)