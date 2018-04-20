package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Player
import htwdd.chessgame.server.model.PlayerHashMap
import htwdd.chessgame.server.util.DatabaseUtility
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.sql.SQLException
import javax.servlet.http.HttpServletResponse

@RestController
class PlayerController {
    private val playerDao = DatabaseUtility.playerDao
    private val matchDao = DatabaseUtility.matchDao

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("player", method = [RequestMethod.OPTIONS])
    fun playerOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,POST,OPTIONS")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("player/{id}", method = [RequestMethod.OPTIONS])
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
    @GetMapping(value = ["player/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPlayerById(@PathVariable id: Int): Player {
        return playerDao!!.queryForId(id) ?: throw IllegalArgumentException("No player with the id '$id' registered!")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @DeleteMapping("player/{id}")
    fun deletePlayerById(@PathVariable id: Int) {
        val queryBuilder = matchDao!!.queryBuilder()
        val query = queryBuilder.where().eq("playerWhite_id", id).or().eq("playerBlack_id", id).prepare()

        if (!playerDao!!.idExists(id)) throw IllegalArgumentException("No player with the id '$id' registered!")

        val matches = matchDao.query(query)
        if (matchDao.delete(matches) != 1) throw SQLException("Can't delete matches from player with the id '$id'")

        if (playerDao.deleteById(id) != 1) throw SQLException("Can't delete player with the id '$id'!")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PostMapping("player")
    @ResponseStatus(HttpStatus.CREATED)
    fun addPlayer(@RequestParam name: String,
                  @RequestParam password: String): Player {
        val player = Player(name = name, password = password)
        if (playerDao!!.create(player) != 1) throw SQLException("Can't create player!")
        return player
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PutMapping("player/{id}")
    fun replacePlayer(@PathVariable id: Int,
                      @RequestParam name: String,
                      @RequestParam password: String) {
        val player = playerDao!!.queryForId(id) ?: throw IllegalArgumentException("No player with the id '$id' registered!")

        player.name = name
        player.password = password

        if (playerDao.update(player) != 1) throw SQLException("Can't replace player with the id '$id'!")
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PatchMapping("player/{id}")
    fun updatePlayer(@PathVariable id: Int,
                     @RequestParam password: String) {
        val player = playerDao!!.queryForId(id) ?: throw IllegalArgumentException("No player with the id '$id' registered!")

        player.password = password

        if (playerDao.update(player) != 1) throw SQLException("Can't update player with the id '$id'!")
    }
}