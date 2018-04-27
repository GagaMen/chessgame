package htwdd.chessgame.server.controller

import htwdd.chessgame.server.dto.DrawDTO
import htwdd.chessgame.server.model.Draw
import htwdd.chessgame.server.model.DrawList
import htwdd.chessgame.server.model.Field
import htwdd.chessgame.server.util.DatabaseUtility
import htwdd.chessgame.server.util.SANUtility
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.OPTIONS
import java.sql.SQLException
import javax.servlet.http.HttpServletResponse

@RestController
class DrawController {
    private val drawDao = DatabaseUtility.drawDao
    private val matchDao = DatabaseUtility.matchDao
    private val fieldDao = DatabaseUtility.fieldDao

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("draw", method = [OPTIONS])
    fun drawOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,POST,OPTIONS")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("draw/{id}", method = [OPTIONS])
    fun drawByIdOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,DELETE,OPTIONS")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping(
            value = ["draw"],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun getDrawList(): DrawList {
        val drawList: MutableList<Draw> = mutableListOf()
        drawDao!!.queryForAll().forEach {
            it.setValuesByDrawCode()
            drawList.add(it)
        }
        return DrawList(drawList)
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping(
            value = ["draw/{id}"],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun getDrawById(
            @PathVariable
            id: Int
    ): Draw {
        return drawDao!!.queryForId(id) ?: throw IllegalArgumentException("No draw with id '$id' registered!")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PostMapping(
            value = ["draw"],
            consumes = [APPLICATION_FORM_URLENCODED_VALUE],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    @ResponseStatus(CREATED)
    fun addDraw(
            @RequestParam
            matchId: Int,
            @RequestParam
            drawCode: String,
            @RequestParam(required = false)
            startColumn: Int? = null,
            @RequestParam(required = false)
            startRow: Int? = null
    ): Draw {
        val match = matchDao!!.queryForId(matchId)
                ?: throw IllegalArgumentException("No match with the id '$matchId' registered!")

        val draw = Draw(
                color = match.currentColor,
                match = match,
                drawCode = drawCode
        )

        if (startRow != null && startColumn != null) draw.startField = Field(row = startRow, column = startColumn)
        draw.setValuesByDrawCode()

        if (!SANUtility.validateSAN(draw, match)) throw RuntimeException("The draw isn't valid!")

        if (fieldDao!!.create(draw.startField) != 1) throw SQLException("Can't create start field!")
        if (fieldDao.create(draw.endField) != 1) throw SQLException("Can't create end field!")

        if (drawDao!!.create(draw) != 1) {
            fieldDao.delete(draw.startField)
            fieldDao.delete(draw.endField)
            throw SQLException("Can't create draw!")
        }

        match.updateByDraw(draw)
        matchDao.update(match)

        return draw
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PostMapping(
            value = ["draw"],
            consumes = [APPLICATION_JSON_VALUE],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    @ResponseStatus(CREATED)
    fun addDrawWithJson(
            @RequestBody
            drawDTO: DrawDTO
    ): Draw {
        val match = matchDao!!.queryForId(drawDTO.matchId)
                ?: throw IllegalArgumentException("No match with the id '${drawDTO.matchId}' registered!")

        val draw = Draw(
                color = match.currentColor,
                match = match,
                drawCode = drawDTO.drawCode
        )

        if (drawDTO.startRow != null && drawDTO.startColumn != null) {
            draw.startField = Field(row = drawDTO.startRow, column = drawDTO.startColumn)
        }
        draw.setValuesByDrawCode()

        if (!SANUtility.validateSAN(draw, match)) throw RuntimeException("The draw isn't valid!")

        if (fieldDao!!.create(draw.startField) != 1) throw SQLException("Can't create start field!")
        if (fieldDao.create(draw.endField) != 1) throw SQLException("Can't create end field!")

        if (drawDao!!.create(draw) != 1) {
            fieldDao.delete(draw.startField)
            fieldDao.delete(draw.endField)
            throw SQLException("Can't create draw!")
        }

        match.updateByDraw(draw)
        matchDao.update(match)

        return draw
    }
}