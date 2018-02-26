package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.PieceColor
import htwdd.chessgame.server.model.Player
import org.springframework.web.bind.annotation.*

@RestController
class MatchController {
    private final val matchList: HashMap<Int, Match> = HashMap()

    @GetMapping("/match")
    fun getMatchList(): HashMap<Int, Match> {
        return matchList
    }

    @GetMapping("/match/{id}")
    fun getMatchById(@PathVariable id: Int): Any? {
        if (matchList.containsKey(id)) return matchList[id]
        return "No match with id \"$id\" registered!"
    }

    @DeleteMapping("/match/{id}")
    fun deleteMatchById(@PathVariable id: Int): Boolean {
        if (matchList.containsKey(id)) {
            matchList.remove(id)
            return true
        }
        return false
    }

    @PutMapping("/match/{id}")
    fun addMatch(@PathVariable id: Int,
                 @RequestParam playerWhite: Player,
                 @RequestParam playerBlack: Player): Boolean {
        if (!matchList.containsKey(id)) {
            val players: HashMap<PieceColor, Player> = HashMap()
            players[PieceColor.WHITE] = playerWhite
            players[PieceColor.BLACK] = playerBlack
            matchList[id] = Match(id, players)
            return true
        }
        return false
    }
}