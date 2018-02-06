package htwdd.chessgame.client.model

class Draw(var color: PieceColor,
           var pieceType: PieceType,
           var start: Field,
           var end: Field) {
    var drawCode: String = ""
}