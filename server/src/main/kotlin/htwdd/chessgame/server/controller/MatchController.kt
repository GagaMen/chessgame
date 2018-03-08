package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.util.DatabaseUtility
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
class MatchController {
    private val matchDao = DatabaseUtility.matchDao
    private val playerDao = DatabaseUtility.playerDao

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
    fun getMatchList(): HashMap<Int, Match> {
        val matchList =  HashMap<Int, Match>()
        matchDao!!.queryForAll().forEach { matchList[it.id] = it }
        return matchList
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping("match/{id}")
    fun getMatchById(@PathVariable id: Int): Any {
        return matchDao!!.queryForId(id) ?: return "No match with id \"$id\" registered!"
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @DeleteMapping("match/{id}")
    fun deleteMatchById(@PathVariable id: Int): Boolean {
        if (matchDao!!.deleteById(id) != 1) return false
        return true
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PostMapping("match")
    fun addMatch(@RequestParam playerWhiteId: Int,
                 @RequestParam playerBlackId: Int): Boolean {
        val playerWhite = playerDao!!.queryForId(playerWhiteId) ?: return false
        val playerBlack = playerDao.queryForId(playerBlackId) ?: return false

        if (matchDao!!.create(Match(playerWhite = playerWhite, playerBlack = playerBlack)) != 1) return false
        return true
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PatchMapping("match/{id}")
    fun updateMatch(@PathVariable id: Int,
                    @RequestParam checkWhite: Boolean,
                    @RequestParam checkBlack: Boolean,
                    @RequestParam checkmate: Boolean,
                    @RequestParam matchCode: String): Boolean {
        val match = matchDao!!.queryForId(id)

        match.checkWhite = checkWhite
        match.checkBlack = checkBlack
        match.checkmate = checkmate
        match.matchCode = matchCode

        if (matchDao.update(match) != 1) return false
        return true
    }
}