package htwdd.chessgame.client.model

import kotlinx.serialization.Serializable

@Serializable
data class Draw(var id: Int = 0,
                val color: PieceColor,
                val pieceType: PieceType,
                val start: Field,
                val end: Field,
                private val match: Match,
                private val throwPiece: Boolean = false,
                private val throwEnPassant: Boolean = false,
                private val kingsideCastling: Boolean = false,
                private val queensideCastling: Boolean = false,
                var drawCode: String = "")