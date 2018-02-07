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
        updatePieceSet(draw)
        switchColor()
        setChanged()
        notifyObservers("updateGameProperties")
    }

    private fun switchColor() {
        currentColor = when (currentColor) {
            PieceColor.WHITE -> PieceColor.BLACK
            PieceColor.BLACK -> PieceColor.WHITE
        }
    }

    private fun updatePieceSet(draw: Draw) {
        val pieceSet = pieceSets[draw.color]
        val startPosition = Pair(draw.start.row, draw.start.column)
        val endPosition = Pair(draw.end.row, draw.end.column)

        if (pieceSet != null) {
            val piece = pieceSet.activePieces[startPosition]

            if (piece != null) {
                piece.position = draw.end
                pieceSet.activePieces.remove(startPosition)
                pieceSet.activePieces[endPosition] = piece

                when (draw.color) {
                    PieceColor.WHITE -> {
                        val opposingPieceSet = pieceSets[PieceColor.BLACK]
                        if (opposingPieceSet != null && opposingPieceSet.activePieces.containsKey(endPosition)) {
                            opposingPieceSet.activePieces.remove(endPosition)
                        }
                    }
                    PieceColor.BLACK -> {
                        val opposingPieceSet = pieceSets[PieceColor.WHITE]
                        if (opposingPieceSet != null && opposingPieceSet.activePieces.containsKey(endPosition)) {
                            opposingPieceSet.activePieces.remove(endPosition)
                        }
                    }
                }
            }
        }

    }
}