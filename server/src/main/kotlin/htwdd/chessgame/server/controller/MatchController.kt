package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.MatchHashMap
import htwdd.chessgame.server.model.PieceColor
import htwdd.chessgame.server.model.PieceColor.BLACK
import htwdd.chessgame.server.model.PieceColor.WHITE
import htwdd.chessgame.server.model.Player
import htwdd.chessgame.server.util.DatabaseUtility
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.OPTIONS
import java.sql.SQLException
import javax.servlet.http.HttpServletResponse

@RestController
class MatchController {
    private val matchDao = DatabaseUtility.matchDao
    private val playerDao = DatabaseUtility.playerDao
    private val drawDao = DatabaseUtility.drawDao

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("match", method = [OPTIONS])
    fun matchOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,POST,OPTIONS")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("match/{id}", method = [OPTIONS])
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
    fun getMatchById(
            @PathVariable
            id: Int
    ): Match {
        val match = matchDao!!.queryForId(id)
                ?: throw IllegalArgumentException("No match with the id '$id' registered!")
        match.players.forEach { playerDao!!.refresh(it.value) }
        match.setPieceSetsByMatchCode()
        return match
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @DeleteMapping("match/{id}")
    fun deleteMatchById(
            @PathVariable
            id: Int
    ) {
        if (!matchDao!!.idExists(id)) throw IllegalArgumentException("No match with the id '$id' registered!")

        if (matchDao.deleteById(id) != 1) throw SQLException("Can't delete match with the id '$id'!")

        val draws = drawDao!!.query(drawDao.queryBuilder()
                .where()
                .eq("match_id", id)
                .prepare())

        if (drawDao.delete(draws) != 1) throw SQLException("Can't delete draws from match with the id '$id'")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PostMapping("match")
    @ResponseStatus(CREATED)
    fun addMatch(
            @RequestParam
            playerWhiteId: Int,
            @RequestParam
            playerBlackId: Int
    ): Match {
        val playerWhite = playerDao!!.queryForId(playerWhiteId)
                ?: throw IllegalArgumentException("No player with the id '$playerWhiteId' registered!")
        val playerBlack = playerDao.queryForId(playerBlackId)
                ?: throw IllegalArgumentException("No player with the id '$playerBlackId' registered!")

        val players = HashMap<PieceColor, Player>()
        players[WHITE] = playerWhite
        players[BLACK] = playerBlack

        val match = Match(players = players, playerWhite = playerWhite, playerBlack = playerBlack)
        match.setPieceSetsByMatchCode()

        if (matchDao!!.create(match) != 1) throw SQLException("Can't create match!")
        return match
    }
}