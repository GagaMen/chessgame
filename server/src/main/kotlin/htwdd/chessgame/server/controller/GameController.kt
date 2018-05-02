package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Draw
import htwdd.chessgame.server.model.DrawList
import htwdd.chessgame.server.model.PieceSetHashMap
import htwdd.chessgame.server.util.DatabaseUtility
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.APPLICATION_XML_VALUE
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.OPTIONS
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/match/{id}")
class GameController {
    private val drawDao = DatabaseUtility.drawDao
    private val matchDao = DatabaseUtility.matchDao

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("/draw", method = [OPTIONS])
    fun drawsByMatchOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,OPTIONS")
    }

    @RequestMapping("/pieceSets", method = [OPTIONS])
    fun pieceSetsByMatchOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,OPTIONS")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping(
            value = ["/draw"],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun getDrawsByMatchId(
            @PathVariable
            id: Int
    ): DrawList {
        val drawList = mutableListOf<Draw>()
        drawDao!!.queryForEq("match_id", id).forEach {
            it.setValuesByDrawCode()
            drawList.add(it)
        }
        return DrawList(drawList)
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping(
            value = ["/pieceSets"],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun getPieceSetsByMatchId(
            @PathVariable
            id: Int
    ): PieceSetHashMap {
        if (!matchDao!!.idExists(id)) throw IllegalArgumentException("No match with the id '$id' registered!")
        val match = matchDao.queryForId(id)
        return PieceSetHashMap(match.pieceSets)
    }
}