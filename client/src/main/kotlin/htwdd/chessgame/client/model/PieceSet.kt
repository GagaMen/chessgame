package htwdd.chessgame.client.model

class PieceSet(var id: Int,
               var activePieces: Array<Piece>,
               var capturedPieces: Array<Piece>,
               var color: PieceColor)