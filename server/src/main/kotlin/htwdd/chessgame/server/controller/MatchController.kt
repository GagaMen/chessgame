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

/**
 * Controller to manage the match resource
 *
 * @property matchDao Object to interact with the database to manage match objects
 * @property playerDao Object to interact with the database to manage player objects
 * @property drawDao Object to interact with the database to manage draw objects
 * @property fieldDao Object to interact with the database to manage field objects
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("/matches")
class MatchController {
    private val matchDao = DatabaseUtility.matchDao
    private val playerDao = DatabaseUtility.playerDao
    private val drawDao = DatabaseUtility.drawDao
    private val fieldDao = DatabaseUtility.fieldDao

    /**
     * Handles the OPTIONS request for the URI /matches
     *
     * Possible request methods:
     * - HEAD
     * - GET
     * - POST
     * - OPTIONS
     *
     * @param response Object that contains the response for the http request
     */
    @RequestMapping(method = [OPTIONS])
    fun matchOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,POST,OPTIONS")
    }

    /**
     * Handles the OPTIONS request for the URI /matches/{id}
     *
     * Possible request methods:
     * - HEAD
     * - GET
     * - DELETE
     * - OPTIONS
     *
     * @param response Object that contains the response for the http request
     */
    @RequestMapping("/{id}", method = [OPTIONS])
    fun matchByIdOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,DELETE,OPTIONS")
    }

    /**
     * Handles the OPTIONS request for the URI /matches/{id}/draws
     *
     * Possible request methods:
     * - HEAD
     * - GET
     * - OPTIONS
     *
     * @param response Object that contains the response for the http request
     */
    @RequestMapping("/{id}/draws", method = [OPTIONS])
    fun drawsByMatchOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,OPTIONS")
    }

    /**
     * Handles the OPTIONS request for the URI /matches/{id}/pieceSets
     *
     * Possible request methods:
     * - HEAD
     * - GET
     * - OPTIONS
     *
     * @param response Object that contains the response for the http request
     */
    @RequestMapping("/{id}/pieceSets", method = [OPTIONS])
    fun pieceSetsByMatchOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,OPTIONS")
    }

    /**
     * Handles the GET request for the URI /matches
     *
     * @param includePieceSets Returned match contains the pieceSets
     * @param includeHistory Returned match contains the history (list of draws)
     *
     * @return Hash map of matches
     */
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

    /**
     * Handles the GET request for the URI /matches/{id}
     *
     * @param id Identifier for a single match
     * @param includePieceSets Returned match contains the pieceSets
     * @param includeHistory Returned match contains the history (list of draws)
     *
     * @return Single match by an id
     */
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

    /**
     * Handles the DELETE request for the URI /matches/{id}
     *
     * @param id Identifier of the match to be deleted
     */
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

    /**
     * Handles the POST request for the URI /matches
     * Params encoded as application/x-www-form-urlencode
     *
     * @param playerWhiteId Player reference for the color white
     * @param playerBlackId Player reference for the color black
     *
     * @return Created draw
     */
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

    /**
     * Handles the POST request for the URI /draws
     * Params encoded as application/json
     *
     * @param matchDTO Contains params as data transfer object
     *
     * @see addMatch
     * @see MatchDTO
     *
     * @return Created draw
     */
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

    /**
     * Handles the GET request for the URI /matches/{id}/draws
     *
     * @param id Identifier for a single match
     *
     * @return List of draws of a match
     */
    @GetMapping(
            value = ["/{id}/draws"],
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

    /**
     * Handles the GET request for the URI /matches/{id}/pieceSets
     *
     * @param id Identifier for a single match
     *
     * @return Hash map of pieceSets of the two players
     */
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