package htwdd.chessgame.server.model

class Draw(val color: PieceColor,
           val pieceType: PieceType,
           val start: Field,
           val end: Field,
           private val match: Match,
           private val throwPiece: Boolean = false,
           private val throwEnPassant: Boolean = false,
           private val kingsideCastling: Boolean = false,
           private val queensideCastling: Boolean = false)