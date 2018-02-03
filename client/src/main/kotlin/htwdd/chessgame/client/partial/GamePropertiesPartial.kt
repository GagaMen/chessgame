package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.Controller
import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceColor
import kotlinx.html.*
import kotlinx.html.dom.create
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class GamePropertiesPartial(val match: Match) : Partial {
    override fun getPartial(controller: Controller): HTMLElement {
        return document.create.div(classes = "properties") {
            div(classes = "properties--player") {
                h2 {
                    +"Players:"
                }
                p {
                    +"Player White: ${match.players[PieceColor.WHITE]?.name}"
                }
                p {
                    +"Player Black: ${match.players[PieceColor.BLACK]?.name}"
                }
            }
            div(classes = "properties--history") {
                h2 {
                    +"History:"
                }
                div(classes = "table--history-wrapper") {
                    table(classes = "table--history") {
                        tr {
                            th { +"Player" }
                            th { +"Piece" }
                            th { +"Start" }
                            th { +"End" }
                        }
                        if (match.history.isNotEmpty()) {
                            match.history.forEach { draw ->
                                tr(classes = "table--history-item") {
                                    td {
                                        +draw.color.toString()
                                    }
                                    td {
                                        +draw.piece.type.toString()
                                    }
                                    td {
                                        +"Row: ${draw.start.row}, Column: ${draw.start.column}"
                                    }
                                    td {
                                        +"Row: ${draw.end.row}, Column: ${draw.end.column}"
                                    }
                                }
                            }
                        } else {
                            tr(classes = "table--history-item") {
                                td {
                                    colSpan = "4"
                                    +"No draw made"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}