package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.Player
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition
import org.springframework.web.bind.annotation.*

@RestController
class MatchController {
    private val pultusORM: PultusORM = PultusORM("chessgame.db")

    @GetMapping("/match")
    fun getMatchList(): HashMap<Int, Match> {
        val matchList = HashMap<Int, Match>()

        pultusORM.find(Match())
                .filterIsInstance<Match>()
                .forEach { it ->
                    it.setValuesByMatchCode()
                    matchList[it.id] = it
                }

        return matchList
    }

    @GetMapping("/match/{id}")
    fun getMatchById(@PathVariable id: Int): Any? {
        val condition = PultusORMCondition.Builder()
                .eq("ID", id)
                .build()

        pultusORM.find(Match(), condition)
                .filterIsInstance<Match>()
                .forEach { it ->
                    it.setValuesByMatchCode()
                    return it
                }

        return "No match with id \"$id\" registered!"
    }

    @DeleteMapping("/match/{id}")
    fun deleteMatchById(@PathVariable id: Int): Boolean {
        val condition = PultusORMCondition.Builder()
                .eq("ID", id)
                .build()

        return pultusORM.delete(Match(), condition)
    }

    @PutMapping("/match")
    fun addMatch(@RequestParam playerWhiteId: Int,
                 @RequestParam playerBlackId: Int): Boolean {
        var playerWhite: Player? = null
        var playerBlack: Player? = null
        val condition = PultusORMCondition.Builder()
                .eq("ID", playerWhiteId)
                .or()
                .eq("ID", playerBlackId)
                .build()

        pultusORM.find(Player(), condition)
                .filterIsInstance<Player>()
                .forEach { it ->
                    if (it.id == playerWhiteId) playerWhite = it
                    if (it.id == playerBlackId) playerBlack = it
                }

        if (playerWhite == null || playerBlack == null) return false
        return pultusORM.save(Match(playerWhiteId, playerBlackId))
    }
}