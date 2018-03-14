package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.*

class FENUtility {
    companion object {
        fun calc(match: Match) {
            val sb = StringBuilder()
            val whitePieceSet = match.pieceSets[PieceColor.WHITE] ?: return
            val blackPieceSet = match.pieceSets[PieceColor.BLACK] ?: return

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
                    sb.append(pieceType.getMatchCode(pieceColor))
                }
                if (emptyCol > 0) sb.append(emptyCol)
                if (i != 8) sb.append("/")
            }

            // train right
            sb.append(" ${match.currentColor.getCode()}")

            // castling right
            if (!match.whiteCastlingKingSide &&
                    !match.whiteCastlingQueenSide &&
                    !match.blackCastlingKingSide &&
                    !match.blackCastlingQueenSide) {
                sb.append(" -")
            } else {
                sb.append(" ")
                if (match.whiteCastlingKingSide) sb.append("K")
                if (match.whiteCastlingQueenSide) sb.append("Q")
                if (match.blackCastlingKingSide) sb.append("k")
                if (match.blackCastlingQueenSide) sb.append("q")
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
            sb.append(" ${match.history.size + 1}")

            match.matchCode = sb.toString()
        }

        fun setByCode(match: Match) {
            val separation = match.matchCode.split(" ")[0].split("/")

            for (i in separation.indices) {
                var column = 1

                separation[i].split("").forEach { char ->
                    when (char) {
                        "" -> return@forEach
                        "P" -> {
                            setPiece(match, PieceColor.WHITE, PieceType.PAWN, Field(i + 1, column))
                            column++
                        }
                        "K" -> {
                            setPiece(match, PieceColor.WHITE, PieceType.KING, Field(i + 1, column))
                            column++
                        }
                        "Q" -> {
                            setPiece(match, PieceColor.WHITE, PieceType.QUEEN, Field(i + 1, column))
                            column++
                        }
                        "B" -> {
                            setPiece(match, PieceColor.WHITE, PieceType.BISHOP, Field(i + 1, column))
                            column++
                        }
                        "N" -> {
                            setPiece(match, PieceColor.WHITE, PieceType.KNIGHT, Field(i + 1, column))
                            column++
                        }
                        "R" -> {
                            setPiece(match, PieceColor.WHITE, PieceType.ROOK, Field(i + 1, column))
                            column++
                        }
                        "p" -> {
                            setPiece(match, PieceColor.BLACK, PieceType.PAWN, Field(i + 1, column))
                            column++
                        }
                        "k" -> {
                            setPiece(match, PieceColor.BLACK, PieceType.KING, Field(i + 1, column))
                            column++
                        }
                        "q" -> {
                            setPiece(match, PieceColor.BLACK, PieceType.QUEEN, Field(i + 1, column))
                            column++
                        }
                        "b" -> {
                            setPiece(match, PieceColor.BLACK, PieceType.BISHOP, Field(i + 1, column))
                            column++
                        }
                        "n" -> {
                            setPiece(match, PieceColor.BLACK, PieceType.KNIGHT, Field(i + 1, column))
                            column++
                        }
                        "r" -> {
                            setPiece(match, PieceColor.BLACK, PieceType.ROOK, Field(i + 1, column))
                            column++
                        }
                        else -> {
                            val number = char.toIntOrNull() ?: return@forEach
                            column += number
                        }
                    }
                }
            }
        }

        private fun setPiece(match: Match, pieceColor: PieceColor, pieceType: PieceType, field: Field) {
            val pieces = match.pieceSets[pieceColor]?.activePieces
            pieces!![field.asPair()] = Piece(pieceType, field)
        }
    }
}