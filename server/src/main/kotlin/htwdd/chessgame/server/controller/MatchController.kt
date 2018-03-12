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
        val matchList =  HashMap<Int, Match>()
        matchDao!!.queryForAll().forEach { match ->
//            drawDao!!.queryForEq("match_id", match.id).forEach { draw ->
//                match.history.add(draw)
//            }
            match.players.forEach{ playerDao!!.refresh(it.value) }
            match.setValuesByMatchCode()
            matchList[match.id] = match
        }

        return MatchHashMap(matchList)
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping("match/{id}")
    fun getMatchById(@PathVariable id: Int): Any {
        val match = matchDao!!.queryForId(id) ?: return "No match with id \"$id\" registered!"
//        drawDao!!.queryForEq("match_id", match.id).forEach { draw ->
//            match.history.add(draw)
//        }
        match.players.forEach{ playerDao!!.refresh(it.value) }
        match.setValuesByMatchCode()
        return match
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @DeleteMapping("match/{id}")
    fun deleteMatchById(@PathVariable id: Int): Boolean {
        if (matchDao!!.deleteById(id) != 1) return false

        // delete draw history of match
        val deleteBuilder = drawDao!!.deleteBuilder()
        deleteBuilder.where().eq("match_id", id)
        deleteBuilder.delete()

        return true
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PostMapping("match")
    fun addMatch(@RequestParam playerWhiteId: Int,
                 @RequestParam playerBlackId: Int): Boolean {
        val playerWhite = playerDao!!.queryForId(playerWhiteId) ?: return false
        val playerBlack = playerDao.queryForId(playerBlackId) ?: return false

        val players = HashMap<PieceColor, Player>()
        players[PieceColor.WHITE] = playerWhite
        players[PieceColor.BLACK] = playerBlack

        if (matchDao!!.create(Match(players = players)) != 1) return false
        return true
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PatchMapping("match/{id}")
    fun updateMatch(@PathVariable id: Int,
                    @RequestParam checkWhite: Boolean,
                    @RequestParam checkBlack: Boolean,
                    @RequestParam checkmate: Boolean,
                    @RequestParam matchCode: String): Boolean {
        val match = matchDao!!.queryForId(id) ?: return false

        match.check[PieceColor.WHITE] = checkWhite
        match.check[PieceColor.BLACK] = checkBlack
        match.checkmate = checkmate
        match.matchCode = matchCode

        if (matchDao.update(match) != 1) return false
        return true
    }
}