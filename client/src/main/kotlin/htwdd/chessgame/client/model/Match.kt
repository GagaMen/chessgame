package htwdd.chessgame.client.model

import htwdd.chessgame.client.util.Observable

data class Match(var players: HashMap<PieceColor, Player?>,
                 var pieceSets: HashMap<PieceColor, PieceSet>,
                 var currentColor: PieceColor,
                 var history: MutableList<Draw>,
                 var matchCode: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1") : Observable() {

    var id: Int = 0

    fun addDraw(draw: Draw) {
        history.add(draw)
        setChanged()
        notifyObservers("updateGameProperties")
    }
}