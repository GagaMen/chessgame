package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Player
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition
import ninja.sakib.pultusorm.core.PultusORMUpdater
import org.springframework.web.bind.annotation.*

@RestController
class PlayerController {
    private val pultusORM: PultusORM = PultusORM("chessgame.db")

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

    @GetMapping("/player/{id}")
    fun getPlayerById(@PathVariable id: Int): Any {
        val condition = PultusORMCondition.Builder()
                .eq("ID", id)
                .build()

        pultusORM.find(Player(), condition)
                .filterIsInstance<Player>()
                .forEach { it ->
                    return it
                }

        return "No player with id \"$id\" registered!"
    }

    @DeleteMapping("/player/{id}")
    fun deletePlayerById(@PathVariable id: Int): Boolean {
        val condition = PultusORMCondition.Builder()
                .eq("ID", id)
                .build()

        return pultusORM.delete(Player(), condition)
    }

    @PutMapping("/player")
    fun addPlayer(@RequestParam name: String,
                  @RequestParam password: String): Boolean {
        return pultusORM.save(Player(name, password))
    }

    @PatchMapping("/player/{id}")
    fun updatePlayer(@PathVariable id: Int,
                     @RequestParam password: String): Boolean {
        val condition = PultusORMCondition.Builder()
                .eq("ID", id)
                .build()
        val updater = PultusORMUpdater.Builder()
                .set("PASSWORD", password)
                .condition(condition)
                .build()

        return pultusORM.update(Player(), updater)
    }
}