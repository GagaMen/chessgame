package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Draw
import htwdd.chessgame.server.model.Match
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition
import org.springframework.web.bind.annotation.*

@RestController
class DrawController {
    private val pultusORM: PultusORM = PultusORM("chessgame.db")

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping("draw")
    fun getDrawList(): MutableList<Draw> {
        val drawList: MutableList<Draw> = mutableListOf()

        pultusORM.find(Draw())
                .filterIsInstance<Draw>()
                .forEach { draw ->
                    draw.setValuesByDrawCode()
                    drawList.add(draw)
                }

        return drawList
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping("draw/{id}")
    fun getDrawById(@PathVariable id: Int): Any {
        val condition = PultusORMCondition.Builder()
                .eq("id", id)
                .build()

        pultusORM.find(Draw(), condition)
                .filterIsInstance<Draw>()
                .forEach { draw ->
                    draw.setValuesByDrawCode()
                    return draw
                }

        return "No draw with id \"$id\" registered!"
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @DeleteMapping("draw/{id}")
    fun deleteDrawById(@PathVariable id: Int): Boolean {
        val condition = PultusORMCondition.Builder()
                .eq("id", id)
                .build()

        return pultusORM.delete(Draw(), condition)
    }

    @CrossOrigin(origins = ["http://localhost:63342"])
    @PutMapping("draw")
    fun addDraw(@RequestParam matchId: Int,
                @RequestParam color: String,
                @RequestParam type: String,
                @RequestParam startRow: Int,
                @RequestParam startCol: Int,
                @RequestParam endRow: Int,
                @RequestParam endCol: Int,
                @RequestParam drawCode: String): Boolean {
        var match: Match? = null
        val condition = PultusORMCondition.Builder()
                .eq("id", matchId)
                .build()

        pultusORM.find(Match(), condition)
                .filterIsInstance<Match>()
                .forEach { it ->
                    if (it.id == matchId) match = it
                }

        if (match == null) return false

        return pultusORM.save(Draw(colorString = color,
                pieceTypeString = type,
                startRow = startRow,
                startColumn = startCol,
                endRow = endRow,
                endColumn = endCol,
                matchId = matchId,
                drawCode = drawCode))
    }
}