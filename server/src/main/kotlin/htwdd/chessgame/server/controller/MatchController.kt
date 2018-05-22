package htwdd.chessgame.server.controller

import htwdd.chessgame.server.dto.MatchDTO
import htwdd.chessgame.server.model.*
import htwdd.chessgame.server.model.PieceColor.BLACK
import htwdd.chessgame.server.model.PieceColor.WHITE
import htwdd.chessgame.server.util.DatabaseUtility
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.OPTIONS
import java.sql.SQLException
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/match")
class MatchController {
    private val matchDao = DatabaseUtility.matchDao
    private val playerDao = DatabaseUtility.playerDao
    private val drawDao = DatabaseUtility.drawDao
    private val fieldDao = DatabaseUtility.fieldDao

    @RequestMapping(method = [OPTIONS])
    fun matchOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,POST,OPTIONS")
    }

    @RequestMapping("/{id}", method = [OPTIONS])
    fun matchByIdOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,DELETE,OPTIONS")
    }

    @RequestMapping("/{id}/draw", method = [OPTIONS])
    fun drawsByMatchOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,OPTIONS")
    }

    @RequestMapping("/{id}/pieceSets", method = [OPTIONS])
    fun pieceSetsByMatchOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,OPTIONS")
    }

    @GetMapping(produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE])
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

    @GetMapping(
            value = ["/{id}"],
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

    @DeleteMapping(
            value = ["/{id}"],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
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

    @PostMapping(
            consumes = [APPLICATION_FORM_URLENCODED_VALUE],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
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

    @PostMapping(
            consumes = [APPLICATION_JSON_VALUE],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    @ResponseStatus(CREATED)
    fun addMatchWithJson(
            @RequestBody
            matchDTO: MatchDTO
    ): Match {
        val playerWhite = playerDao!!.queryForId(matchDTO.playerWhiteId)
                ?: throw IllegalArgumentException("No player with the id '${matchDTO.playerWhiteId}' registered!")
        val playerBlack = playerDao.queryForId(matchDTO.playerBlackId)
                ?: throw IllegalArgumentException("No player with the id '${matchDTO.playerBlackId}' registered!")

        val players = HashMap<PieceColor, Player>()
        players[WHITE] = playerWhite
        players[BLACK] = playerBlack

        val match = Match(players = players, playerWhite = playerWhite, playerBlack = playerBlack)
        match.setPieceSetsByMatchCode()

        if (matchDao!!.create(match) != 1) throw SQLException("Can't create match!")
        return match
    }

    @GetMapping(
            value = ["/{id}/draw"],
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

    @GetMapping(
            value = ["/{id}/pieceSets"],
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