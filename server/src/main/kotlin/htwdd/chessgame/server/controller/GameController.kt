package htwdd.chessgame.server.controller

import htwdd.chessgame.server.model.Draw
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition
import org.springframework.web.bind.annotation.*

@RestController
class GameController {
    private val pultusORM: PultusORM = PultusORM("chessgame.db")

    @GetMapping("match/{id}/draw")
    fun getDrawsByMatchId(@PathVariable id: Int): MutableList<Draw> {
        val drawList: MutableList<Draw> = mutableListOf()
        val condition = PultusORMCondition.Builder()
                .eq("ID", id)
                .build()

        pultusORM.find(Draw(), condition)
                .filterIsInstance<Draw>()
                .forEach { draw ->
                    draw.setValuesByDrawCode()
                    drawList.add(draw)
                }

        return drawList
    }
}