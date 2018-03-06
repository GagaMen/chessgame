package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Draw
import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.PieceColor
import htwdd.chessgame.server.model.Player
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
class MatchController {
    private val pultusORM: PultusORM = PultusORM("chessgame.db")

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("match", method = [RequestMethod.OPTIONS])
    fun matchOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,PUT,OPTIONS")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("match/{id}", method = [RequestMethod.OPTIONS])
    fun matchByIdOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,DELETE,OPTIONS")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping("match")
    fun getMatchList(): HashMap<Int, Match> {
        val matchList = HashMap<Int, Match>()

        pultusORM.find(Match())
                .filterIsInstance<Match>()
                .forEach { match ->
                    match.setValuesByMatchCode()
                    setPlayer(match)
                    setHistory(match)
                    matchList[match.id] = match
                }

        return matchList
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping("match/{id}")
    fun getMatchById(@PathVariable id: Int): Any {
        val condition = PultusORMCondition.Builder()
                .eq("id", id)
                .build()

        pultusORM.find(Match(), condition)
                .filterIsInstance<Match>()
                .forEach { match ->
                    match.setValuesByMatchCode()
                    setPlayer(match)
                    setHistory(match)

                    return match
                }

        return "No match with id \"$id\" registered!"
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @DeleteMapping("match/{id}")
    fun deleteMatchById(@PathVariable id: Int): Boolean {
        val matchCondition = PultusORMCondition.Builder()
                .eq("id", id)
                .build()
        val drawCondition = PultusORMCondition.Builder()
                .eq("matchId", id)
                .build()

        if (!pultusORM.delete(Draw(), drawCondition)) return false
        return pultusORM.delete(Match(), matchCondition)
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PutMapping("match")
    fun addMatch(@RequestParam playerWhiteId: Int,
                 @RequestParam playerBlackId: Int): Boolean {
        var playerWhite: Player? = null
        var playerBlack: Player? = null
        val condition = PultusORMCondition.Builder()
                .eq("id", playerWhiteId)
                .or()
                .eq("id", playerBlackId)
                .build()

        pultusORM.find(Player(), condition)
                .filterIsInstance<Player>()
                .forEach { it ->
                    if (it.id == playerWhiteId) playerWhite = it
                    if (it.id == playerBlackId) playerBlack = it
                }

        if (playerWhite == null || playerBlack == null) return false
        return pultusORM.save(Match(playerWhiteId, playerBlackId))
    }

    private fun setPlayer(match: Match) {
        val playerCondition = PultusORMCondition.Builder()
                .eq("id", match.playerWhite)
                .or()
                .eq("id", match.playerBlack)
                .build()

        pultusORM.find(Player(), playerCondition)
                .filterIsInstance<Player>()
                .forEach { player ->
                    if (player.id == match.playerWhite) match.players[PieceColor.WHITE] = player
                    if (player.id == match.playerBlack) match.players[PieceColor.BLACK] = player
                }
    }

    private fun setHistory(match: Match) {
        val condition = PultusORMCondition.Builder()
                .eq("matchId", match.id)
                .build()

        pultusORM.find(Draw(), condition)
                .filterIsInstance<Draw>()
                .forEach { draw ->
                    draw.setValuesByDrawCode()
                    match.history.add(draw)
                }
    }
}