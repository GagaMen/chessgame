package htwdd.chessgame.server.controller

import htwdd.chessgame.server.dto.MatchDTO
import htwdd.chessgame.server.model.*
import htwdd.chessgame.server.model.PieceColor.BLACK
import htwdd.chessgame.server.model.PieceColor.WHITE
import htwdd.chessgame.server.util.DatabaseUtility
import htwdd.chessgame.server.util.HateoasUtility
import org.springframework.hateoas.Link
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.OPTIONS
import java.sql.SQLException
import javax.servlet.http.HttpServletResponse

/**
 * Controller to manage the match resource
 *
 * @author Felix Dimmel
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
     * @author Felix Dimmel
     *
     * @param response Object that contains the response for the http request
     *
     * @since 1.0.0
     */
    @RequestMapping(method = [OPTIONS])
    fun matchOptions(response: HttpServletResponse): ResponseEntity<Unit> {
        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(MatchController::class.java).matchOptions(response)).withSelfRel()
        links.add(Pair(selfLink, "OPTIONS"))
        val prevLink = linkTo(methodOn(MatchController::class.java).getMatchList(true, true, response)).withRel("prev")
        links.add(Pair(prevLink, "GET"))

        val headers = HateoasUtility.createLinkHeader(links)
        headers.set("Allow", "HEAD,GET,POST,OPTIONS")

        return ResponseEntity(headers, OK)
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
     * @author Felix Dimmel
     *
     * @param response Object that contains the response for the http request
     *
     * @since 1.0.0
     */
    @RequestMapping("/{id}", method = [OPTIONS])
    fun matchByIdOptions(
            @PathVariable
            id: Int,
            response: HttpServletResponse
    ): ResponseEntity<Unit> {
        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(MatchController::class.java).matchByIdOptions(id, response)).withSelfRel()
        links.add(Pair(selfLink, "OPTIONS"))
        val prevLink = linkTo(methodOn(MatchController::class.java).getMatchById(id, true, true, response)).withRel("prev")
        links.add(Pair(prevLink, "GET"))

        val headers = HateoasUtility.createLinkHeader(links)
        headers.set("Allow", "HEAD,GET,DELETE,OPTIONS")

        return ResponseEntity(headers, OK)
    }

    /**
     * Handles the OPTIONS request for the URI /matches/{id}/draws
     *
     * Possible request methods:
     * - HEAD
     * - GET
     * - OPTIONS
     *
     * @author Felix Dimmel
     *
     * @param response Object that contains the response for the http request
     *
     * @since 1.0.0
     */
    @RequestMapping("/{id}/draws", method = [OPTIONS])
    fun drawsByMatchOptions(
            @PathVariable
            id: Int,
            response: HttpServletResponse
    ): ResponseEntity<Unit> {
        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(MatchController::class.java).drawsByMatchOptions(id, response)).withSelfRel()
        links.add(Pair(selfLink, "OPTIONS"))
        val prevLink = linkTo(methodOn(MatchController::class.java).getMatchById(id, true, true, response)).withRel("prev")
        links.add(Pair(prevLink, "GET"))

        val headers = HateoasUtility.createLinkHeader(links)
        headers.set("Allow", "HEAD,GET,OPTIONS")

        return ResponseEntity(headers, OK)
    }

    /**
     * Handles the OPTIONS request for the URI /matches/{id}/pieceSets
     *
     * Possible request methods:
     * - HEAD
     * - GET
     * - OPTIONS
     *
     * @author Felix Dimmel
     *
     * @param response Object that contains the response for the http request
     *
     * @since 1.0.0
     */
    @RequestMapping("/{id}/pieceSets", method = [OPTIONS])
    fun pieceSetsByMatchOptions(
            @PathVariable
            id: Int,
            response: HttpServletResponse
    ): ResponseEntity<Unit> {
        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(MatchController::class.java).drawsByMatchOptions(id, response)).withSelfRel()
        links.add(Pair(selfLink, "OPTIONS"))
        val prevLink = linkTo(methodOn(MatchController::class.java).getMatchById(id, true, true, response)).withRel("prev")
        links.add(Pair(prevLink, "GET"))

        val headers = HateoasUtility.createLinkHeader(links)
        headers.set("Allow", "HEAD,GET,OPTIONS")

        return ResponseEntity(headers, OK)
    }

    /**
     * Handles the GET request for the URI /matches
     *
     * @author Felix Dimmel
     *
     * @param includePieceSets Returned match contains the pieceSets
     * @param includeHistory Returned match contains the history (list of draws)
     *
     * @return Hash map of matches
     *
     * @since 1.0.0
     */
    @GetMapping(produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE])
    fun getMatchList(
            @RequestParam(required = false, value = "includePieceSets", defaultValue = "true")
            includePieceSets: Boolean,
            @RequestParam(required = false, value = "includeHistory", defaultValue = "true")
            includeHistory: Boolean,
            response: HttpServletResponse
    ): ResponseEntity<MatchHashMap> {
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

        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(MatchController::class.java).getMatchList(includePieceSets, includeHistory, response)).withSelfRel()
        links.add(Pair(selfLink, "GET"))
        val optionsLink = linkTo(methodOn(MatchController::class.java).matchOptions(response)).withRel("options")
        links.add(Pair(optionsLink, "OPTIONS"))
        val newLink = linkTo(methodOn(MatchController::class.java).addMatch(0, 0, response)).withRel("new")
        links.add(Pair(newLink, "POST"))

        matchList.forEach { (matchId, _) ->
            val matchLink = linkTo(methodOn(MatchController::class.java).getMatchById(matchId, includePieceSets, includeHistory, response)).withRel("next")
            links.add(Pair(matchLink, "GET"))
        }

        return ResponseEntity(MatchHashMap(matchList), HateoasUtility.createLinkHeader(links), OK)
    }

    /**
     * Handles the GET request for the URI /matches/{id}
     *
     * @author Felix Dimmel
     *
     * @param id Identifier for a single match
     * @param includePieceSets Returned match contains the pieceSets
     * @param includeHistory Returned match contains the history (list of draws)
     *
     * @return Single match by an id
     *
     * @since 1.0.0
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
            includeHistory: Boolean,
            response: HttpServletResponse
    ): ResponseEntity<Match> {
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

        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(MatchController::class.java).getMatchById(id, includePieceSets, includeHistory, response)).withSelfRel()
        links.add(Pair(selfLink, "GET"))
        val optionsLink = linkTo(methodOn(MatchController::class.java).matchByIdOptions(id, response)).withRel("options")
        links.add(Pair(optionsLink, "OPTIONS"))
        val deleteLink = linkTo(methodOn(MatchController::class.java).deleteMatchById(id, response)).withRel("delete")
        links.add(Pair(deleteLink, "DELETE"))
        val prevLink = linkTo(methodOn(MatchController::class.java).getMatchList(includePieceSets, includeHistory, response)).withRel("prev")
        links.add(Pair(prevLink, "GET"))
        val nextLinkPieceSets = linkTo(methodOn(MatchController::class.java).getPieceSetsByMatchId(id, response)).withRel("next")
        links.add(Pair(nextLinkPieceSets, "GET"))
        val nextLinkHistory = linkTo(methodOn(MatchController::class.java).getDrawsByMatchId(id, response)).withRel("next")
        links.add(Pair(nextLinkHistory, "GET"))

        return ResponseEntity(match, HateoasUtility.createLinkHeader(links), OK)
    }

    /**
     * Handles the DELETE request for the URI /matches/{id}
     *
     * @author Felix Dimmel
     *
     * @param id Identifier of the match to be deleted
     *
     * @since 1.0.0
     */
    @DeleteMapping(
            value = ["/{id}"],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun deleteMatchById(
            @PathVariable
            id: Int,
            response: HttpServletResponse
    ): ResponseEntity<Unit> {
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

        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(MatchController::class.java).deleteMatchById(id, response)).withRel("delete")
        links.add(Pair(selfLink, "DELETE"))
        val nextLink = linkTo(methodOn(MatchController::class.java).getMatchList(true, true, response)).withRel("next")
        links.add(Pair(nextLink, "GET"))

        return ResponseEntity(HateoasUtility.createLinkHeader(links), OK)
    }

    /**
     * Handles the POST request for the URI /matches
     * Params encoded as application/x-www-form-urlencode
     *
     * @author Felix Dimmel
     *
     * @param playerWhiteId Player reference for the color white
     * @param playerBlackId Player reference for the color black
     *
     * @return Created draw
     *
     * @since 1.0.0
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
            playerBlackId: Int,
            response: HttpServletResponse
    ): ResponseEntity<Match> {
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

        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(MatchController::class.java).addMatch(playerWhiteId, playerBlackId, response)).withSelfRel()
        links.add(Pair(selfLink, "POST"))
        val optionsLink = linkTo(methodOn(MatchController::class.java).matchOptions(response)).withRel("options")
        links.add(Pair(optionsLink, "OPTIONS"))
        val nextLink = linkTo(methodOn(MatchController::class.java).getMatchById(match.id, true, true, response)).withRel("next")
        links.add(Pair(nextLink, "GET"))

        return ResponseEntity(match, HateoasUtility.createLinkHeader(links), CREATED)
    }

    /**
     * Handles the POST request for the URI /draws
     * Params encoded as application/json
     *
     * @author Felix Dimmel
     *
     * @param matchDTO Contains params as data transfer object
     *
     * @see addMatch
     * @see MatchDTO
     *
     * @return Created draw
     *
     * @since 1.0.0
     */
    @PostMapping(
            consumes = [APPLICATION_JSON_VALUE],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    @ResponseStatus(CREATED)
    fun addMatchWithJson(
            @RequestBody
            matchDTO: MatchDTO,
            response: HttpServletResponse
    ): ResponseEntity<Match> {
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

        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(MatchController::class.java).addMatch(matchDTO.playerWhiteId, matchDTO.playerBlackId, response)).withSelfRel()
        links.add(Pair(selfLink, "POST"))
        val optionsLink = linkTo(methodOn(MatchController::class.java).matchOptions(response)).withRel("options")
        links.add(Pair(optionsLink, "OPTIONS"))
        val nextLink = linkTo(methodOn(MatchController::class.java).getMatchById(match.id, true, true, response)).withRel("next")
        links.add(Pair(nextLink, "GET"))

        return ResponseEntity(match, HateoasUtility.createLinkHeader(links), CREATED)
    }

    /**
     * Handles the GET request for the URI /matches/{id}/draws
     *
     * @author Felix Dimmel
     *
     * @param id Identifier for a single match
     *
     * @return List of draws of a match
     *
     * @since 1.0.0
     */
    @GetMapping(
            value = ["/{id}/draws"],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun getDrawsByMatchId(
            @PathVariable
            id: Int,
            response: HttpServletResponse
    ): ResponseEntity<DrawList> {
        val drawList = mutableListOf<Draw>()
        drawDao!!.queryForEq("match_id", id).forEach {
            it.setValuesByDrawCode()
            drawList.add(it)
        }

        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(MatchController::class.java).getDrawsByMatchId(id, response)).withSelfRel()
        links.add(Pair(selfLink, "GET"))
        val optionsLink = linkTo(methodOn(MatchController::class.java).drawsByMatchOptions(id, response)).withRel("options")
        links.add(Pair(optionsLink, "OPTIONS"))
        val prevLinks = linkTo(methodOn(MatchController::class.java).getMatchById(id, true, true, response)).withRel("prev")
        links.add(Pair(prevLinks, "GET"))

        return ResponseEntity(DrawList(drawList), HateoasUtility.createLinkHeader(links), OK)
    }

    /**
     * Handles the GET request for the URI /matches/{id}/pieceSets
     *
     * @author Felix Dimmel
     *
     * @param id Identifier for a single match
     *
     * @return Hash map of pieceSets of the two players
     *
     * @since 1.0.0
     */
    @GetMapping(
            value = ["/{id}/pieceSets"],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun getPieceSetsByMatchId(
            @PathVariable
            id: Int,
            response: HttpServletResponse
    ): ResponseEntity<PieceSetHashMap> {
        if (!matchDao!!.idExists(id)) throw IllegalArgumentException("No match with the id '$id' registered!")
        val match = matchDao.queryForId(id)

        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(MatchController::class.java).getPieceSetsByMatchId(id, response)).withSelfRel()
        links.add(Pair(selfLink, "GET"))
        val optionsLink = linkTo(methodOn(MatchController::class.java).pieceSetsByMatchOptions(id, response)).withRel("options")
        links.add(Pair(optionsLink, "OPTIONS"))
        val prevLinks = linkTo(methodOn(MatchController::class.java).getMatchById(id, true, true, response)).withRel("prev")
        links.add(Pair(prevLinks, "GET"))

        return ResponseEntity(PieceSetHashMap(match.pieceSets), HateoasUtility.createLinkHeader(links), OK)
    }
}