package htwdd.chessgame.client.model

import kotlinx.serialization.Serializable

@Serializable
data class Draw(var id: Int = 0,
                val color: PieceColor,
                val pieceType: PieceType,
                val startField: Field,
                val endField: Field,
                private val match: Match,
                private val conversion: PieceType? = null,
                private val throwPiece: Boolean = false,
                private val throwEnPassant: Boolean = false,
                private val kingsideCastling: Boolean = false,
                private val queensideCastling: Boolean = false,
                private val check: Boolean = false,
                private val checkmate: Boolean = false,
                var drawCode: String = "")