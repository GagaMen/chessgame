package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Draw
import htwdd.chessgame.server.model.DrawList
import htwdd.chessgame.server.util.DatabaseUtility
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.OPTIONS
import javax.servlet.http.HttpServletResponse

@RestController
class GameController {
    private val drawDao = DatabaseUtility.drawDao

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("match/{id}/draw", method = [OPTIONS])
    fun drawsByMatchOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,OPTIONS")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping("match/{id}/draw")
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
}