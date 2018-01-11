package htwdd.chessgame.client

class Match(var id: Int,
            var players: Map<PieceColor, Player>,
            var pieceSets: Map<PieceColor, PieceSet>,
            var history: Array<Draw>,
            var matchCode: String)