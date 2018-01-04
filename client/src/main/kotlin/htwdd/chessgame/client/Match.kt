package htwdd.chessgame.client

class Match(var id: Int,
            var playerWhite: Player,
            var playerBlack: Player,
            var startFEN: String = "rnbqk bnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            var history: Array<Draw>)