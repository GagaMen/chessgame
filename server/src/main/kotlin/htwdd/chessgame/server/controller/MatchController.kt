package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.MatchHashMap
import htwdd.chessgame.server.model.PieceColor
import htwdd.chessgame.server.model.PieceColor.BLACK
import htwdd.chessgame.server.model.PieceColor.WHITE
import htwdd.chessgame.server.model.Player
import htwdd.chessgame.server.util.DatabaseUtility
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.APPLICATION_XML_VALUE
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.OPTIONS
import java.sql.SQLException
import javax.servlet.http.HttpServletResponse

@RestController
class MatchController {
    private val matchDao = DatabaseUtility.matchDao
    private val playerDao = DatabaseUtility.playerDao
    private val drawDao = DatabaseUtility.drawDao
    private val fieldDao = DatabaseUtility.fieldDao

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
    @GetMapping(
            value = ["match"],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun getMatchList(
            @RequestParam(required = false, value = "includePieceSets", defaultValue = "true")
            includePieceSets: Boolean,
            @RequestParam(required = false, value = "includeHistory", defaultValue = "true")
            includeHistory: Boolean
    ): MatchHashMap {
        val matchList = HashMap<Int, Match>()
        matchDao!!.queryForAll().forEach { match ->
            match.players.forEach { playerDao!!.refresh(it.value) }

            if (!includePieceSets) match.pieceSets = HashMap()
            if (includeHistory) {
                val draws = drawDao!!.query(drawDao.queryBuilder()
                        .where()
                        .eq("match_id", match.id)
                        .prepare())
                match.history.addAll(draws)
            }

            matchList[match.id] = match
        }

        return MatchHashMap(matchList)
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping(
            value = ["match/{id}"],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    @ResponseBody
    fun getMatchById(
            @PathVariable
            id: Int,
            @RequestParam(required = false, value = "includePieceSets", defaultValue = "true")
            includePieceSets: Boolean,
            @RequestParam(required = false, value = "includeHistory", defaultValue = "true")
            includeHistory: Boolean
    ): Match {
        val match = matchDao!!.queryForId(id)
                ?: throw IllegalArgumentException("No match with the id '$id' registered!")
        match.players.forEach { playerDao!!.refresh(it.value) }

        if (!includePieceSets) match.pieceSets = HashMap()
        if (includeHistory) {
            val draws = drawDao!!.query(drawDao.queryBuilder()
                    .where()
                    .eq("match_id", match.id)
                    .prepare())
            match.history.addAll(draws)
        }

        return match
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @DeleteMapping("match/{id}")
    fun deleteMatchById(
            @PathVariable
            id: Int
    ) {
        if (!matchDao!!.idExists(id)) throw IllegalArgumentException("No match with the id '$id' registered!")
        val match = matchDao.queryForId(id)

        if (match.enPassantField != null) {
            if (fieldDao!!.delete(match.enPassantField) != 1) {
                throw SQLException("Can't delete field from match with the id '$id'")
            }
        }

        val draws = drawDao!!.query(drawDao.queryBuilder()
                .where()
                .eq("match_id", id)
                .prepare())

        if (draws.size != 0) {

            draws.forEach { draw ->
                if (draw.startField != null) {
                    if (fieldDao!!.delete(draw.startField) != 1) {
                        throw SQLException("Can't delete field from draw with the id '$id'")
                    }
                }
                if (draw.endField != null) {
                    if (fieldDao!!.delete(draw.endField) != 1) {
                        throw SQLException("Can't delete field from draw with the id '$id'")
                    }
                }
            }

            if (drawDao.delete(draws) == 0) throw SQLException("Can't delete draws from match with the id '$id'")
        }

        if (matchDao.deleteById(id) != 1) throw SQLException("Can't delete match with the id '$id'!")
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