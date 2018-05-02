package htwdd.chessgame.server.controller

import htwdd.chessgame.server.dto.PasswordDTO
import htwdd.chessgame.server.dto.PlayerDTO
import htwdd.chessgame.server.model.Player
import htwdd.chessgame.server.model.PlayerHashMap
import htwdd.chessgame.server.util.DatabaseUtility
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.OPTIONS
import java.sql.SQLException
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/player")
class PlayerController {
    private val playerDao = DatabaseUtility.playerDao
    private val matchDao = DatabaseUtility.matchDao
    private val drawDao = DatabaseUtility.drawDao
    private val fieldDao = DatabaseUtility.fieldDao

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping(method = [OPTIONS])
    fun playerOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,POST,OPTIONS")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("/{id}", method = [OPTIONS])
    fun playerByIdOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,PUT,PATCH,DELETE,OPTIONS")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping(produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE])
    fun getPlayerList(): PlayerHashMap {
        val playerList = HashMap<Int, Player>()
        playerDao!!.queryForAll().forEach { playerList[it.id] = it }

        return PlayerHashMap(playerList)
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping(
            value = ["/{id}"],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    @ResponseBody
    fun getPlayerById(
            @PathVariable
            id: Int
    ): Player {
        return playerDao!!.queryForId(id) ?: throw IllegalArgumentException("No player with the id '$id' registered!")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @DeleteMapping(
            value = ["/{id}"],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun deletePlayerById(
            @PathVariable
            id: Int
    ) {
        if (!playerDao!!.idExists(id)) throw IllegalArgumentException("No player with the id '$id' registered!")

        deleteMatchesByPlayer(id)

        if (playerDao.deleteById(id) != 1) throw SQLException("Can't delete player with the id '$id'!")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PostMapping(
            consumes = [APPLICATION_FORM_URLENCODED_VALUE],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    @ResponseStatus(CREATED)
    fun addPlayer(
            @RequestParam
            name: String,
            @RequestParam
            password: String
    ): Player {
        val player = Player(name = name, password = password)
        if (playerDao!!.create(player) != 1) throw SQLException("Can't create player!")
        return player
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PostMapping(
            consumes = [APPLICATION_JSON_VALUE],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    @ResponseStatus(CREATED)
    @ResponseBody
    fun addPlayerWithJson(
            @RequestBody
            playerDTO: PlayerDTO
    ): Player {
        val player = Player(name = playerDTO.name, password = playerDTO.password)
        if (playerDao!!.create(player) != 1) throw SQLException("Can't create player!")
        return player
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PatchMapping(
            value = ["/{id}"],
            consumes = [APPLICATION_FORM_URLENCODED_VALUE],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun updatePlayer(
            @PathVariable
            id: Int,
            @RequestParam
            password: String
    ) {
        val player = playerDao!!.queryForId(id)
                ?: throw IllegalArgumentException("No player with the id '$id' registered!")

        player.password = password

        if (playerDao.update(player) != 1) throw SQLException("Can't update player with the id '$id'!")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PatchMapping(
            value = ["/{id}"],
            consumes = [APPLICATION_JSON_VALUE],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun updatePlayerWithJson(
            @PathVariable
            id: Int,
            @RequestBody
            passwordDTO: PasswordDTO
    ) {
        val player = playerDao!!.queryForId(id)
                ?: throw IllegalArgumentException("No player with the id '$id' registered!")

        player.password = passwordDTO.password

        if (playerDao.update(player) != 1) throw SQLException("Can't update player with the id '$id'!")
    }

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