package htwdd.chessgame.server.util

import htwdd.chessgame.server.model.*

class FENUtility {
    companion object {
        private val drawDao = DatabaseUtility.drawDao

        fun calc(match: Match) {
            val sb = StringBuilder()
            val whitePieceSet = match.pieceSets[PieceColor.WHITE] ?: return
            val blackPieceSet = match.pieceSets[PieceColor.BLACK] ?: return

            // piece position
            for (i in 8 downTo 1) {
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
                    sb.append(pieceType.getMatchCode(pieceColor))
                }
                if (emptyCol > 0) sb.append(emptyCol)
                if (i != 1) sb.append("/")
            }

            // train right
            sb.append(" ${match.currentColor.getCode()}")

            // castling right
            if (!match.kingsideCastling[PieceColor.WHITE]!! &&
                    !match.queensideCastling[PieceColor.WHITE]!! &&
                    !match.kingsideCastling[PieceColor.BLACK]!! &&
                    !match.queensideCastling[PieceColor.BLACK]!!) {
                sb.append(" -")
            } else {
                sb.append(" ")
                if (match.kingsideCastling[PieceColor.WHITE]!!) sb.append("K")
                if (match.queensideCastling[PieceColor.WHITE]!!) sb.append("Q")
                if (match.kingsideCastling[PieceColor.BLACK]!!) sb.append("k")
                if (match.queensideCastling[PieceColor.BLACK]!!) sb.append("q")
            }

            // en passant
            if (match.enPassantField != null) {
                sb.append(" ${(match.enPassantField?.column?.plus(96))?.toChar()}${match.enPassantField?.row}")
            } else {
                sb.append(" -")
            }

            // halfMoves
            sb.append(" ${match.halfMoves}")

            // next train number
            val history = drawDao!!.queryForEq("match_id", match.id)
            sb.append(" ${history.size + 1}")

            match.matchCode = sb.toString()
        }
    }
}