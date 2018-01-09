package htwdd.chessgame.client

class Match(var id: Int,
            var players: Map<PieceColor, Player>,
            var board: Board,
            var history: Array<Draw>)