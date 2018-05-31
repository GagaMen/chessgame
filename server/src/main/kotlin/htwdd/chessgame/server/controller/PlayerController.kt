package htwdd.chessgame.server.controller

import htwdd.chessgame.server.dto.PasswordDTO
import htwdd.chessgame.server.dto.PlayerDTO
import htwdd.chessgame.server.model.Player
import htwdd.chessgame.server.model.PlayerHashMap
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
 * @property playerDao Object to interact with the database to manage player objects
 * @property matchDao Object to interact with the database to manage match objects
 * @property drawDao Object to interact with the database to manage draw objects
 * @property fieldDao Object to interact with the database to manage field objects
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("/players")
class PlayerController {
    private val playerDao = DatabaseUtility.playerDao
    private val matchDao = DatabaseUtility.matchDao
    private val drawDao = DatabaseUtility.drawDao
    private val fieldDao = DatabaseUtility.fieldDao

    init {
        if (!playerDao!!.idExists(1)) {
            val aiPlayer = Player(name = "AI (Stockfish)", password = "an§AS373%()asd8%.,>")
            playerDao.create(aiPlayer)
        }
    }

    /**
     * Handles the OPTIONS request for the URI /players
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
    fun playerOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,POST,OPTIONS")
    }

    /**
     * Handles the OPTIONS request for the URI /players/{id}
     *
     * Possible request methods:
     * - HEAD
     * - GET
     * - PATCH
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
    fun playerByIdOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,PATCH,DELETE,OPTIONS")
    }

    /**
     * Handles the GET request for the URI /players
     *
     * @author Felix Dimmel
     *
     * @return Hash map of players
     *
     * @since 1.0.0
     */
    @GetMapping(produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE])
    fun getPlayerList(response: HttpServletResponse): ResponseEntity<PlayerHashMap> {
        val playerList = HashMap<Int, Player>()
        playerDao!!.queryForAll().forEach { playerList[it.id] = it }

        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(PlayerController::class.java).getPlayerList(response)).withSelfRel()
        links.add(Pair(selfLink, "HEAD,GET,POST,OPTIONS"))
        val newLink = linkTo(methodOn(PlayerController::class.java).addPlayer("valueOfName", "valueOfPassword", response)).withRel("new")
        links.add(Pair(newLink, "POST"))

        return ResponseEntity(PlayerHashMap(playerList), HateoasUtility.createLinkHeader(links), OK)
    }

    /**
     * Handle the GET request for URI /matches/{id}
     *
     * @author Felix Dimmel
     *
     * @param id Identifier for a single match
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
    fun getPlayerById(
            @PathVariable
            id: Int,
            response: HttpServletResponse
    ): ResponseEntity<Player> {
        val player = playerDao!!.queryForId(id)
                ?: throw IllegalArgumentException("No player with the id '$id' registered!")

        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(PlayerController::class.java).getPlayerById(id, response)).withSelfRel()
        links.add(Pair(selfLink, "GET"))
        val patchLink = linkTo(methodOn(PlayerController::class.java).updatePlayer(id, "valueOfPassword", response)).withRel("update")
        links.add(Pair(patchLink, "PATCH"))
        val deleteLink = linkTo(methodOn(PlayerController::class.java).deletePlayerById(id, response)).withRel("delete")
        links.add(Pair(deleteLink, "DELETE"))
        val prevLink = linkTo(methodOn(PlayerController::class.java).getPlayerList(response)).withRel("prev")
        links.add(Pair(prevLink, "GET"))

        return ResponseEntity(player, HateoasUtility.createLinkHeader(links), OK)
    }

    /**
     * Handles the DELETE request for the URI /players/{id}
     *
     * @author Felix Dimmel
     *
     * @param id Identifier of the player to be deleted
     *
     * @since 1.0.0
     */
    @DeleteMapping(
            value = ["/{id}"],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun deletePlayerById(
            @PathVariable
            id: Int,
            response: HttpServletResponse
    ): ResponseEntity<Unit> {
        if (!playerDao!!.idExists(id)) throw IllegalArgumentException("No player with the id '$id' registered!")

        deleteMatchesByPlayer(id)

        if (playerDao.deleteById(id) != 1) throw SQLException("Can't delete player with the id '$id'!")

        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(PlayerController::class.java).deletePlayerById(id, response)).withSelfRel()
        links.add(Pair(selfLink, "DELETE"))
        val nextLink = linkTo(methodOn(PlayerController::class.java).getPlayerList(response)).withRel("next")
        links.add(Pair(nextLink, "GET"))

        return ResponseEntity(HateoasUtility.createLinkHeader(links), OK)
    }

    /**
     * Handles the POST request for the URI /players
     * Params encoded as application/x-www-form-urlencode
     *
     * @author Felix Dimmel
     *
     * @param name The name of the player
     * @param password The password of the player
     *
     * @return Created match
     *
     * @since 1.0.0
     */
    @PostMapping(
            consumes = [APPLICATION_FORM_URLENCODED_VALUE],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    @ResponseStatus(CREATED)
    fun addPlayer(
            @RequestParam
            name: String,
            @RequestParam
            password: String,
            response: HttpServletResponse
    ): ResponseEntity<Player> {
        val player = Player(name = name, password = password)
        if (playerDao!!.create(player) != 1) throw SQLException("Can't create player!")

        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(PlayerController::class.java).addPlayer(name, password, response)).withSelfRel()
        links.add(Pair(selfLink, "POST"))
        val nextLink = linkTo(methodOn(PlayerController::class.java).getPlayerById(player.id, response)).withRel("next")
        links.add(Pair(nextLink, "GET"))

        return ResponseEntity(player, HateoasUtility.createLinkHeader(links), CREATED)
    }

    /**
     * Handles the POST request for the URI /players
     * Params encoded as application/json
     *
     * @author Felix Dimmel
     *
     * @param playerDTO Contains params as data transfer object
     *
     * @see addPlayer
     * @see PlayerDTO
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
    @ResponseBody
    fun addPlayerWithJson(
            @RequestBody
            playerDTO: PlayerDTO,
            response: HttpServletResponse
    ): ResponseEntity<Player> {
        val player = Player(name = playerDTO.name, password = playerDTO.password)
        if (playerDao!!.create(player) != 1) throw SQLException("Can't create player!")

        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(PlayerController::class.java).addPlayer(playerDTO.name, playerDTO.password, response)).withSelfRel()
        links.add(Pair(selfLink, "POST"))
        val nextLink = linkTo(methodOn(PlayerController::class.java).getPlayerById(player.id, response)).withRel("next")
        links.add(Pair(nextLink, "GET"))

        return ResponseEntity(player, HateoasUtility.createLinkHeader(links), CREATED)
    }

    /**
     * Handles the PATCH request for the URI /players/{id}
     * Params encoded as application/x-www-form-urlencode
     *
     * @author Felix Dimmel
     *
     * @param id Identifier for a single player
     * @param password The updated password of the player
     *
     * @since 1.0.0
     */
    @PatchMapping(
            value = ["/{id}"],
            consumes = [APPLICATION_FORM_URLENCODED_VALUE],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun updatePlayer(
            @PathVariable
            id: Int,
            @RequestParam
            password: String,
            response: HttpServletResponse
    ): ResponseEntity<Unit> {
        val player = playerDao!!.queryForId(id)
                ?: throw IllegalArgumentException("No player with the id '$id' registered!")

        player.password = password

        if (playerDao.update(player) != 1) throw SQLException("Can't update player with the id '$id'!")

        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(PlayerController::class.java).updatePlayer(id, password, response)).withSelfRel()
        links.add(Pair(selfLink, "PATCH"))
        val nextLink = linkTo(methodOn(PlayerController::class.java).getPlayerById(player.id, response)).withRel("next")
        links.add(Pair(nextLink, "GET"))

        return ResponseEntity(HateoasUtility.createLinkHeader(links), OK)
    }

    /**
     * Handles the PATCH request for the URI /players/{id}
     *
     * @author Felix Dimmel
     *
     * @param id Identifier for a single player
     * @param passwordDTO Contains params as data transfer object
     *
     * @see updatePlayer
     * @see PasswordDTO
     *
     * @since 1.0.0
     */
    @PatchMapping(
            value = ["/{id}"],
            consumes = [APPLICATION_JSON_VALUE],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun updatePlayerWithJson(
            @PathVariable
            id: Int,
            @RequestBody
            passwordDTO: PasswordDTO,
            response: HttpServletResponse
    ): ResponseEntity<Unit> {
        val player = playerDao!!.queryForId(id)
                ?: throw IllegalArgumentException("No player with the id '$id' registered!")

        player.password = passwordDTO.password

        if (playerDao.update(player) != 1) throw SQLException("Can't update player with the id '$id'!")

        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(PlayerController::class.java).updatePlayer(id, passwordDTO.password, response)).withSelfRel()
        links.add(Pair(selfLink, "PATCH"))
        val nextLink = linkTo(methodOn(PlayerController::class.java).getPlayerById(player.id, response)).withRel("next")
        links.add(Pair(nextLink, "GET"))

        return ResponseEntity(HateoasUtility.createLinkHeader(links), OK)
    }

    /**
     * Handles the DELETE request for the URI /players/{id}
     *
     * @author Felix Dimmel
     *
     * @param id Identifier of the player to be deleted
     *
     * @since 1.0.0
     */
    private fun deleteMatchesByPlayer(id: Int) {
        val matches = matchDao!!.query(matchDao.queryBuilder()
                .where()
                .eq("playerWhite_id", id)
                .or()
                .eq("playerBlack_id", id)
                .prepare())

        if (matches.size != 0) {

            matches.forEach { match ->
                if (match.enPassantField != null) {
                    if (fieldDao!!.delete(match.enPassantField) != 1) {
                        throw SQLException("Can't delete field from match with the id '$id'")
                    }
                }

                val draws = drawDao!!.query(drawDao.queryBuilder()
                        .where()
                        .eq("match_id", match.id)
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
            }

            if (matchDao.delete(matches) == 0) throw SQLException("Can't delete matches from player with the id '$id'")
        }
    }
}