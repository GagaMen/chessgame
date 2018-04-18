package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.*
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

        if (!SANUtility.validateSAN(draw, match)) return null

        if (fieldDao!!.create(draw.startField) != 1) return null
        if (fieldDao.create(draw.endField) != 1) return null
        if (drawDao!!.create(draw) != 1) {
            fieldDao.delete(draw.startField)
            fieldDao.delete(draw.endField)
            return null
        }

        match.updateByDraw(draw)
        matchDao.update(match)

        return draw
    }
}