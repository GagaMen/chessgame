package htwdd.chessgame.client.model

data class Match(var players: Map<PieceColor, Player>,
                 var pieceSets: Map<PieceColor, PieceSet>,
                 var history: List<Draw>,
                 var matchCode: String) {

    var id: Int = 0
}