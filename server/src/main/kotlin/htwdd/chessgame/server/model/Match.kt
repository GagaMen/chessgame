package htwdd.chessgame.server.model

data class Match(val id: Int,
                 var players: HashMap<PieceColor, Player>,
                 var pieceSets: HashMap<PieceColor, PieceSet> = HashMap(),
                 var currentColor: PieceColor = PieceColor.WHITE,
                 var history: MutableList<Draw> = mutableListOf(),
                 var whiteCastlingKingSide: Boolean = true,
                 var whiteCastlingQueenSide: Boolean = true,
                 var blackCastlingKingSide: Boolean = true,
                 var blackCastlingQueenSide: Boolean = true,
                 var enPassantField: Field? = null,
                 var halfMoves: Int = 0,
                 var check: HashMap<PieceColor, Boolean> = hashMapOf(PieceColor.WHITE to false, PieceColor.BLACK to false),
                 var checkmate: Boolean = false,
                 private var matchCode: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1") {
    init {
        pieceSets[PieceColor.WHITE] = PieceSet(pieceColor = PieceColor.WHITE)
        pieceSets[PieceColor.BLACK] = PieceSet(pieceColor = PieceColor.BLACK)
    }
}