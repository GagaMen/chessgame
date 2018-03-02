package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Draw
import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.PieceColor
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
                .forEach { match ->
                    match.setValuesByMatchCode()
                    setPlayer(match)
                    setHistory(match)
                    matchList[match.id] = match
                }

        return matchList
    }

    @GetMapping("/match/{id}")
    fun getMatchById(@PathVariable id: Int): Any {
        val condition = PultusORMCondition.Builder()
                .eq("ID", id)
                .build()

        pultusORM.find(Match(), condition)
                .filterIsInstance<Match>()
                .forEach { match ->
                    match.setValuesByMatchCode()
                    setPlayer(match)
                    setHistory(match)

                    return match
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

    private fun setPlayer(match: Match) {
        val playerCondition = PultusORMCondition.Builder()
                .eq("ID", match.playerWhite)
                .or()
                .eq("ID", match.playerBlack)
                .build()

        pultusORM.find(Player(), playerCondition)
                .filterIsInstance<Player>()
                .forEach { player ->
                    if (player.id == match.playerWhite) match.players[PieceColor.WHITE] = player
                    if (player.id == match.playerBlack) match.players[PieceColor.BLACK] = player
                }
    }

    private fun setHistory(match: Match) {
        val condition = PultusORMCondition.Builder()
                .eq("MATCHID", match.id)
                .build()

        pultusORM.find(Draw(), condition)
                .filterIsInstance<Draw>()
                .forEach { draw ->
                    draw.setValuesByDrawCode()
                    match.history.add(draw)
                }
    }
}