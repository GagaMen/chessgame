package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Player
import org.springframework.web.bind.annotation.*

@RestController
class PlayerController {
    private final val playerList: HashMap<Int, Player> = HashMap()

    @RequestMapping("*", method = [RequestMethod.GET, RequestMethod.POST])
    fun wrongRequestFallback(): String {
        return "Wrong request"
    }

    @GetMapping("/player")
    fun getPlayerList(): HashMap<Int, Player> {
        return playerList
    }

    @GetMapping("/player/{id}")
    fun getPlayerById(@PathVariable id: Int): Any? {
        if (playerList.containsKey(id)) return playerList[id]
        return "No player with id \"$id\" registered!"
    }

    @DeleteMapping("player/{id}")
    fun deletePlayerById(@PathVariable id: Int): Boolean {
        if (playerList.containsKey(id)) {
            playerList.remove(id)
            return true
        }
        return false
    }

    @PutMapping("player")
    fun addPlayer(@RequestParam id: Int,
                  @RequestParam name: String,
                  @RequestParam password: String): Boolean {
        if (!playerList.containsKey(id)) {
            playerList[id] = Player(id, name, password)
            return true
        }
        return false
    }

    @PatchMapping("player/{id}")
    fun updatePlayer(@PathVariable id: Int,
                     @RequestParam password: String): Boolean {
        if (playerList.containsKey(id)) {
            playerList[id]?.password = password
            return true
        }
        return false
    }
}