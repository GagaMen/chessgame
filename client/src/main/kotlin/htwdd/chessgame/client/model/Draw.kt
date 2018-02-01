package htwdd.chessgame.client.model

class Draw(var id: Int,
           var color: PieceColor,
           var piece: Piece,
           var start: Field,
           var end: Field,
           var drawCode: String)