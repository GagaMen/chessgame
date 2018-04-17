package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.MatchHashMap
import htwdd.chessgame.server.model.PieceColor
import htwdd.chessgame.server.model.Player
import htwdd.chessgame.server.util.DatabaseUtility
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
class MatchController {
    private val matchDao = DatabaseUtility.matchDao
    private val playerDao = DatabaseUtility.playerDao
    private val drawDao = DatabaseUtility.drawDao
    private val fieldDao = DatabaseUtility.fieldDao

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("match", method = [RequestMethod.OPTIONS])
    fun matchOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,POST,OPTIONS")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("match/{id}", method = [RequestMethod.OPTIONS])
    fun matchByIdOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,DELETE,OPTIONS")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping("match")
    fun getMatchList(): MatchHashMap {
        val matchList = HashMap<Int, Match>()
        matchDao!!.queryForAll().forEach { match ->
            match.players.forEach { playerDao!!.refresh(it.value) }
            match.setPieceSetsByMatchCode()
            matchList[match.id] = match
        }

        return MatchHashMap(matchList)
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping("match/{id}")
    fun getMatchById(@PathVariable id: Int): Any {
        val match = matchDao!!.queryForId(id) ?: return "No match with id \"$id\" registered!"
        match.players.forEach { playerDao!!.refresh(it.value) }
        match.setPieceSetsByMatchCode()
        return match
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @DeleteMapping("match/{id}")
    fun deleteMatchById(@PathVariable id: Int): Boolean {
        if (matchDao!!.deleteById(id) != 1) return false

        val draws = drawDao!!.query(drawDao.queryBuilder()
                .where()
                .eq("match_id", id)
                .prepare())

        drawDao.delete(draws)

        return true
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PostMapping("match")
    fun addMatch(@RequestParam playerWhiteId: Int,
                 @RequestParam playerBlackId: Int): Match? {
        val playerWhite = playerDao!!.queryForId(playerWhiteId) ?: return null
        val playerBlack = playerDao.queryForId(playerBlackId) ?: return null

        val players = HashMap<PieceColor, Player>()
        players[PieceColor.WHITE] = playerWhite
        players[PieceColor.BLACK] = playerBlack

        val match = Match(players = players, playerWhite = playerWhite, playerBlack = playerBlack)

        if (matchDao!!.create(match) != 1) return null
        return match
    }
}