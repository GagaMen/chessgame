package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.*
import htwdd.chessgame.server.model.PieceType.*
import htwdd.chessgame.server.util.*
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
class DrawController {
    private val drawDao = DatabaseUtility.drawDao
    private val matchDao = DatabaseUtility.matchDao
    private val fieldDao = DatabaseUtility.fieldDao

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("draw", method = [RequestMethod.OPTIONS])
    fun drawOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,POST,OPTIONS")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("draw/{id}", method = [RequestMethod.OPTIONS])
    fun drawByIdOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,DELETE,OPTIONS")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping("draw")
    fun getDrawList(): MutableList<Draw> {
        val drawList: MutableList<Draw> = mutableListOf()
        drawDao!!.queryForAll().forEach {
            it.setValuesByDrawCode()
            drawList.add(it)
        }
        return drawList
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping("draw/{id}")
    fun getDrawById(@PathVariable id: Int): Any {
        val draw = drawDao!!.queryForId(id) ?: return "No draw with id \"$id\" registered!"
        draw.setValuesByDrawCode()
        return draw
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @DeleteMapping("draw/{id}")
    fun deleteDrawById(@PathVariable id: Int): Boolean {
        val draw = drawDao!!.queryForId(id) ?: return false

        // check if draw is in use
        if (matchDao!!.queryForId(draw.match?.id) != null) return false

        if (drawDao.delete(draw) != 1) return false
        return true
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PostMapping("draw")
    fun addDraw(@RequestParam matchId: Int,
                @RequestParam drawCode: String,
                @RequestParam(required = false) startColumn: Int? = null,
                @RequestParam(required = false) startRow: Int? = null): Draw? {
        val match = matchDao!!.queryForId(matchId) ?: return null
        match.setPieceSetsByMatchCode()

        val draw = Draw(color = match.currentColor,
                match = match,
                drawCode = drawCode)

        if (startRow != null && startColumn != null) draw.startField = Field(row = startRow, column = startColumn)
        if (!draw.setValuesByDrawCode()) return null

        val validate = validateDraw(draw, match)
        if (!validate) return null

        if (fieldDao!!.create(draw.startField) != 1) return null
        if (fieldDao.create(draw.endField) != 1) return null
        if (drawDao!!.create(draw) != 1) {
            fieldDao.delete(draw.startField)
            fieldDao.delete(draw.endField)
            return null
        }

        updateMatch(match, draw)

        return draw
    }

    private fun validateDraw(draw: Draw, match: Match): Boolean {
        val movementFields = HashSet<Pair<Int, Int>>()
        val movementUtility = when (draw.pieceType) {
            PAWN -> PawnMovementUtility()
            KING -> KingMovementUtility()
            QUEEN -> QueenMovementUtility()
            BISHOP -> BishopMovementUtility()
            KNIGHT -> KnightMovementUtility()
            ROOK -> RookMovementUtility()
            else -> return false
        }
        //todo check castling

        val possibleStartFields = calcPossibleStartFields(draw, match)

        possibleStartFields.forEach {
            movementUtility.getFilteredMovementFields(movementFields, it.first, it.second, match)
            if (movementFields.contains(draw.endField?.asPair())) {
                if (
                        draw.startField == null ||
                        draw.startField?.row != it.first ||
                        draw.startField?.column != it.second
                ) {
                    draw.startField = Field(row = it.first, column = it.second)
                }
                return true
            }
        }

        return false
    }

    private fun calcPossibleStartFields(draw: Draw, match: Match): HashSet<Pair<Int, Int>> {
        val possibleStartFields = HashSet<Pair<Int, Int>>()
        val regex = "([KQBNR])?([a-h]|[1-8])?(x)?([a-h])([1-8])([QBRN])?(e\\.p\\.)?(\\+{1,2}|#)?".toRegex()
        val matchResult = regex.find(draw.drawCode) ?: return possibleStartFields

        val startInfo = matchResult.groups[2]?.value ?: ""

        if (draw.startField != null) {
            possibleStartFields.add(draw.startField!!.asPair())
        } else {
            match.pieceSets[match.currentColor]!!.activePieces.forEach {
                if (it.value.type != draw.pieceType) return@forEach
                possibleStartFields.add(it.key)
            }
        }

        if (startInfo == "") return possibleStartFields

        if (startInfo.toIntOrNull() == null) { // column
            val column = startInfo.toCharArray()[0].toInt() % 96
            possibleStartFields.forEach {
                if (it.second != column) possibleStartFields.remove(it)
            }
        } else { // row
            val row = startInfo.toInt()
            possibleStartFields.forEach {
                if (it.first != row) possibleStartFields.remove(it)
            }
        }

        return possibleStartFields
    }

    private fun updateMatch(match: Match, draw: Draw) {
        updatePieceSets(match, draw)

        if (draw.throwPiece || draw.pieceType == PAWN) {
            match.halfMoves = 0
        } else {
            match.halfMoves++
        }

        if (draw.kingsideCastling) match.kingsideCastling[match.currentColor] = false
        if (draw.queensideCastling) match.queensideCastling[match.currentColor] = false

        if (draw.pieceType == PAWN && (
                        (match.currentColor == PieceColor.WHITE && draw.endField?.row == 4) ||
                                (match.currentColor == PieceColor.BLACK && draw.endField?.row == 5))
        ) {
            match.pieceSets[match.currentColor]?.activePieces?.forEach {
                if (it.value.type != PAWN) return@forEach
                if (it.key.second != draw.endField?.column) return@forEach

                val row = when (match.currentColor) {
                    PieceColor.WHITE -> 3
                    PieceColor.BLACK -> 6
                }

                if (match.enPassantField == null) {
                    match.enPassantField = Field(row = row, column = draw.endField?.column!!)
                    fieldDao!!.create(match.enPassantField) // todo maybe throw an error
                } else {
                    match.enPassantField?.row = row
                    match.enPassantField?.column = draw.endField?.column!!
                    fieldDao!!.update(match.enPassantField)
                }
            }
        }

        match.check[match.currentColor] = draw.check
        match.checkmate = draw.checkmate

        match.switchColor()

        FENUtility.calc(match)

        matchDao!!.update(match)
    }

    private fun updatePieceSets(match: Match, draw: Draw): Boolean {
        val activePieces = match.pieceSets[match.currentColor]?.activePieces ?: return false
        val opposingActivePieces = match.pieceSets[match.currentColor.getOpposite()]?.activePieces ?: return false
        val capturedPieces = match.pieceSets[match.currentColor]?.capturedPieces ?: return false

        if (draw.kingsideCastling || draw.queensideCastling) {
            val row = when (match.currentColor) {
                PieceColor.WHITE -> 1
                PieceColor.BLACK -> 8
            }
            val column = when {
                draw.kingsideCastling -> 8
                draw.queensideCastling -> 1
                else -> 0 // at no time possible
            }
            val king = activePieces[Pair(row, 5)] ?: return false
            val rook = activePieces[Pair(row, column)] ?: return false
            activePieces.remove(Pair(row, 5))
            activePieces.remove(Pair(row, column))

            val kingColumn = when {
                draw.kingsideCastling -> 7
                draw.queensideCastling -> 3
                else -> 0 // at no time possible
            }
            val rookColumn = when {
                draw.kingsideCastling -> 6
                draw.queensideCastling -> 4
                else -> 0 // at no time possible
            }

            king.position.row = row
            king.position.column = kingColumn
            rook.position.row = row
            rook.position.column = rookColumn

            activePieces[Pair(row, kingColumn)] = king
            activePieces[Pair(row, rookColumn)] = rook
        } else {
            val piece = activePieces[draw.startField?.asPair()] ?: return false
            activePieces.remove(draw.startField?.asPair())

            piece.position.row = draw.endField?.row!!
            piece.position.column = draw.endField?.column!!

            activePieces[draw.endField?.asPair()!!] = piece

            if (draw.throwPiece) {
                var capturedPiecePosition = draw.endField?.asPair()!!
                if (draw.throwEnPassant) {
                    capturedPiecePosition = when (match.currentColor) {
                        PieceColor.WHITE -> Pair(6, draw.endField?.column!!)
                        PieceColor.BLACK -> Pair(3, draw.endField?.column!!)
                    }
                }

                val capturedPiece = opposingActivePieces[capturedPiecePosition] ?: return false
                opposingActivePieces.remove(capturedPiecePosition)

                capturedPieces.add(capturedPiece)
            }
        }

        return true
    }
}