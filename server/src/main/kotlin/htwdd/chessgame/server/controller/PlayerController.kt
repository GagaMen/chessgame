package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Player
import htwdd.chessgame.server.model.PlayerHashMap
import htwdd.chessgame.server.util.DatabaseUtility
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.OPTIONS
import java.sql.SQLException
import javax.servlet.http.HttpServletResponse

@RestController
class PlayerController {
    private val playerDao = DatabaseUtility.playerDao
    private val matchDao = DatabaseUtility.matchDao
    private val drawDao = DatabaseUtility.drawDao

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("player", method = [OPTIONS])
    fun playerOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,POST,OPTIONS")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("player/{id}", method = [OPTIONS])
    fun playerByIdOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,PUT,PATCH,DELETE,OPTIONS")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping("player")
    fun getPlayerList(): PlayerHashMap {
        val playerList = HashMap<Int, Player>()
        playerDao!!.queryForAll().forEach { playerList[it.id] = it }

        return PlayerHashMap(playerList)
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping(value = ["player/{id}"])
    fun getPlayerById(
            @PathVariable
            id: Int
    ): Player {
        return playerDao!!.queryForId(id) ?: throw IllegalArgumentException("No player with the id '$id' registered!")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @DeleteMapping("player/{id}")
    fun deletePlayerById(
            @PathVariable
            id: Int
    ) {
        if (!playerDao!!.idExists(id)) throw IllegalArgumentException("No player with the id '$id' registered!")

        deleteMatchesByPlayer(id)

        if (playerDao.deleteById(id) != 1) throw SQLException("Can't delete player with the id '$id'!")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PostMapping("player")
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
    @PutMapping("player/{id}")
    fun replacePlayer(
            @PathVariable
            id: Int,
            @RequestParam
            name: String,
            @RequestParam
            password: String
    ) {
        val player = playerDao!!.queryForId(id)
                ?: throw IllegalArgumentException("No player with the id '$id' registered!")

        player.name = name
        player.password = password

        if (playerDao.update(player) != 1) throw SQLException("Can't replace player with the id '$id'!")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PatchMapping("player/{id}")
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

    private fun deleteMatchesByPlayer(id: Int) {
        val matches = matchDao!!.query(matchDao.queryBuilder()
                .where()
                .eq("playerWhite_id", id)
                .or()
                .eq("playerBlack_id", id)
                .prepare())

        if (matches.size != 0) {

            matches.forEach { match ->
                val draws = drawDao!!.query(drawDao.queryBuilder()
                        .where()
                        .eq("match_id", match.id)
                        .prepare())

                if (draws.size != 0) {
                    if (drawDao.delete(draws) == 0) throw SQLException("Can't delete draws from match with the id '$id'")
                }
            }

            if (matchDao.delete(matches) == 0) throw SQLException("Can't delete matches from player with the id '$id'")
        }
    }
}