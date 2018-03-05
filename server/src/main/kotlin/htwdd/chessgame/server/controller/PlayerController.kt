package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Player
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition
import ninja.sakib.pultusorm.core.PultusORMUpdater
import org.springframework.web.bind.annotation.*

@RestController
class PlayerController {
    private val pultusORM: PultusORM = PultusORM("chessgame.db")

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping("/player")
    fun getPlayerList(): HashMap<Int, Player> {
        val playerList = HashMap<Int, Player>()

        pultusORM.find(Player())
                .filterIsInstance<Player>()
                .forEach { it ->
                    playerList[it.id] = it
                }

        return playerList
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping("/player/{id}")
    fun getPlayerById(@PathVariable id: Int): Any {
        val condition = PultusORMCondition.Builder()
                .eq("id", id)
                .build()

        pultusORM.find(Player(), condition)
                .filterIsInstance<Player>()
                .forEach { it ->
                    return it
                }

        return "No player with id \"$id\" registered!"
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @DeleteMapping("/player/{id}")
    fun deletePlayerById(@PathVariable id: Int): Boolean {
        val condition = PultusORMCondition.Builder()
                .eq("id", id)
                .build()

        return pultusORM.delete(Player(), condition)
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PutMapping("/player")
    fun addPlayer(@RequestParam name: String,
                  @RequestParam password: String): Boolean {
        return pultusORM.save(Player(name, password))
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PatchMapping("/player/{id}")
    fun updatePlayer(@PathVariable id: Int,
                     @RequestParam password: String): Boolean {
        val condition = PultusORMCondition.Builder()
                .eq("id", id)
                .build()
        val updater = PultusORMUpdater.Builder()
                .set("password", password)
                .condition(condition)
                .build()

        return pultusORM.update(Player(), updater)
    }
}