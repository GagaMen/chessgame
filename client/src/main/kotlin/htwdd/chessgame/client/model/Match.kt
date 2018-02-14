package htwdd.chessgame.client.model

import htwdd.chessgame.client.util.Observable

data class Match(var players: HashMap<PieceColor, Player?>,
                 var pieceSets: HashMap<PieceColor, PieceSet>,
                 var currentColor: PieceColor,
                 var history: MutableList<Draw>,
                 var whiteCastlingKingSide: Boolean = true,
                 var whiteCastlingQueenSide: Boolean = true,
                 var blackCastlingKingSide: Boolean = true,
                 var blackCastlingQueenSide: Boolean = true,
                 var enPassantField: Field? = null,
                 var halfMoves: Int = 0,
                 private var matchCode: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1") : Observable() {

    var id: Int = 0

    fun addDraw(draw: Draw) {
        history.add(draw)
        updatePieceSet(draw)
        switchColor()
        updateMatchCode()
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
        val startPosition = Pair(draw.start.row, draw.start.column)
        val endPosition = Pair(draw.end.row, draw.end.column)
        val pieceSet = pieceSets[draw.color] ?: return
        val piece = pieceSet.activePieces[startPosition] ?: return

        piece.position = draw.end
        pieceSet.activePieces.remove(startPosition)
        pieceSet.activePieces[endPosition] = piece

        val pieceColor = when (draw.color) {
            PieceColor.WHITE -> PieceColor.BLACK
            PieceColor.BLACK -> PieceColor.WHITE
        }

        val opposingPieceSet = pieceSets[pieceColor] ?: return
        if (opposingPieceSet.activePieces.containsKey(endPosition)) {
            val capturedPiece = opposingPieceSet.activePieces[endPosition]
            if (capturedPiece != null) pieceSet.capturedPieces.add(capturedPiece)
            opposingPieceSet.activePieces.remove(endPosition)
        }
    }

    private fun updateMatchCode() {
        val sb = StringBuilder()
        val whitePieceSet = pieceSets[PieceColor.WHITE] ?: return
        val blackPieceSet = pieceSets[PieceColor.BLACK] ?: return

        // piece position
        for (i in 1..8) {
            var emptyCol = 0
            for (j in 1..8) {
                var pieceType: PieceType? = null
                var pieceColor: PieceColor? = null

                when {
                    whitePieceSet.activePieces.containsKey(Pair(i, j)) -> {
                        pieceType = whitePieceSet.activePieces[Pair(i, j)]?.type ?: return
                        pieceColor = PieceColor.WHITE
                    }
                    blackPieceSet.activePieces.containsKey(Pair(i, j)) -> {
                        pieceType = blackPieceSet.activePieces[Pair(i, j)]?.type ?: return
                        pieceColor = PieceColor.BLACK
                    }
                    else -> emptyCol++
                }

                if (pieceType == null || pieceColor == null) continue

                if (emptyCol > 0) {
                    sb.append(emptyCol)
                    emptyCol = 0
                }
                sb.append(pieceType.getCode(pieceColor))
            }
            if (emptyCol > 0) sb.append(emptyCol)
            if (i != 8) sb.append("/")
        }

        // train right
        sb.append(" ${currentColor.getCode()}")

        // castling right
        if (!whiteCastlingKingSide && !whiteCastlingQueenSide && !blackCastlingKingSide && !blackCastlingQueenSide) {
            sb.append(" -")
        } else {
            sb.append(" ")
            if (whiteCastlingKingSide) sb.append("K")
            if (whiteCastlingQueenSide) sb.append("Q")
            if (blackCastlingKingSide) sb.append("k")
            if (blackCastlingQueenSide) sb.append("q")
        }

        // en passant
        if (enPassantField != null) {
            sb.append(" ${(enPassantField?.column?.plus(96))?.toChar()}${enPassantField?.row}")
        } else {
            sb.append(" -")
        }

        // halfMoves
        sb.append(" $halfMoves")

        // next train number
        sb.append(" ${history.size + 1}")

        matchCode = sb.toString()
    }
}