package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.Controller
import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceColor
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.dom.create
import kotlinx.html.h1
import kotlinx.html.js.div
import kotlinx.html.js.onClickFunction
import kotlinx.html.title
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document

class GamePartial(val match: Match) : Partial {
    override fun getPartial(controller: Controller): HTMLElement {
        val gamePartial = document.create.div(classes = "container") {
            div(classes = "row") {
                div(classes = "col-sm-12") {
                    h1(classes = "text--center") {
                        +"Match: ${match.players[PieceColor.WHITE]?.name} vs. ${match.players[PieceColor.BLACK]?.name}"
                    }
                }
            }
            div(classes = "row") {
                div(classes = "col-sm-8") {
                    div(classes = "placeholder--game-board")
                }
                div(classes = "col-sm-4") {
                    div(classes = "placeholder--game-properties")
                }
            }
            div(classes = "row") {
                div(classes = "col-sm-12") {
                    a(classes = "btn btn--inline btn--ghost") {
                        title = "Return to start"
                        href = "#start"
                        +"Return"
                        onClickFunction = { e ->
                            e.preventDefault()
                            controller.actionPerformed("showMatchAction")
                        }
                    }
                }
            }
        }

        val gameBoard = GameBoardPartial(match).getPartial(controller)
        val gameProperties = GamePropertiesPartial(match).getPartial(controller)

        gamePartial.getElementsByClassName("placeholder--game-board")[0]?.replaceWith(gameBoard)
        gamePartial.getElementsByClassName("placeholder--game-properties")[0]?.replaceWith(gameProperties)

        return gamePartial
    }
}