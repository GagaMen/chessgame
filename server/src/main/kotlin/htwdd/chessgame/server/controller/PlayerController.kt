package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Player
import htwdd.chessgame.server.model.PlayerHashMap
import htwdd.chessgame.server.util.DatabaseUtility
import org.springframework.web.bind.annotation.*
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
    @GetMapping("player/{id}")
    fun getPlayerById(@PathVariable id: Int): Any {
        return playerDao!!.queryForId(id) ?: return "No player with id \"$id\" registered!"
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @DeleteMapping("player/{id}")
    fun deletePlayerById(@PathVariable id: Int): Boolean {
        val queryBuilder = matchDao!!.queryBuilder()
        val query = queryBuilder.where().eq("playerWhite_id", id).or().eq("playerBlack_id", id).prepare()

        // check if player is in use
        if (matchDao.query(query).size > 0) return false

        if (playerDao!!.deleteById(id) != 1) return false
        return true
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PostMapping("player")
    fun addPlayer(@RequestParam name: String,
                  @RequestParam password: String): Boolean {
        if (playerDao!!.create(Player(name = name, password = password)) != 1) return false
        return true
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PutMapping("player/{id}")
    fun replacePlayer(@PathVariable id: Int,
                      @RequestParam name: String,
                      @RequestParam password: String): Boolean {
        val player = playerDao!!.queryForId(id) ?: return false

        player.name = name
        player.password = password

        if (playerDao.update(player) != 1) return false
        return true
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PatchMapping("player/{id}")
    fun updatePlayer(@PathVariable id: Int,
                     @RequestParam password: String): Boolean {
        val player = playerDao!!.queryForId(id) ?: return false

        player.password = password

        if (playerDao.update(player) != 1) return false
        return true
    }
}