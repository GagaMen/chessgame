package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.*
import htwdd.chessgame.server.util.DatabaseUtility
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
        drawDao!!.queryForAll().forEach { drawList.add(it) }
        return drawList
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping("draw/{id}")
    fun getDrawById(@PathVariable id: Int): Any {
        return drawDao!!.queryForId(id) ?: return "No draw with id \"$id\" registered!"
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @DeleteMapping("draw/{id}")
    fun deleteDrawById(@PathVariable id: Int): Boolean {
        val draw = drawDao!!.queryForId(id) ?: return false

        // check if player is in use
        if (matchDao!!.queryForId(draw.match?.id) != null) return false

        if (drawDao.delete(draw) != 1) return false
        return true
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PostMapping("draw")
    fun addDraw(@RequestParam matchId: Int,
                @RequestParam color: String,
                @RequestParam type: String,
                @RequestParam startRow: Int,
                @RequestParam startColumn: Int,
                @RequestParam endRow: Int,
                @RequestParam endColumn: Int,
                @RequestParam drawCode: String): Draw? {
        val match = matchDao!!.queryForId(matchId) ?: return null
        val startField = Field(row = startRow, column = startColumn)
        if (fieldDao!!.create(startField) != 1) return null
        val endField = Field(row = endRow, column = endColumn)
        if (fieldDao.create(endField) != 1) return null

        val draw = Draw(color = PieceColor.valueOf(color),
                pieceType = PieceType.valueOf(type),
                start = startField,
                end = endField,
                drawCode = drawCode,
                match = match)

        if (drawDao!!.create(draw) != 1) return null
        return draw
    }
}