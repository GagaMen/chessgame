package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.Controller
import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceColor
import htwdd.chessgame.client.model.PieceType
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
                div(classes = "player--white") {
                    p {
                        +"Player White: ${match.players[PieceColor.WHITE]?.name}"
                    }
                    table(classes = "table--captured-pieces") {
                        var pawn = 0
                        var queen = 0
                        var bishop = 0
                        var knight = 0
                        var rook = 0
                        match.pieceSets[PieceColor.WHITE]?.capturedPieces?.forEach {
                            when (it.type) {
                                PieceType.PAWN -> pawn++
                                PieceType.QUEEN -> queen++
                                PieceType.BISHOP -> bishop++
                                PieceType.KNIGHT -> knight++
                                PieceType.ROOK -> rook++
                                else -> {
                                }
                            }
                        }
                        tr {
                            th {
                                +"Captured Pieces"
                                colSpan = "2"
                            }
                        }
                        tr {
                            td { +"Pawn" }
                            td { +"$pawn" }
                        }
                        tr {
                            td { +"Queen" }
                            td { +"$queen" }
                        }
                        tr {
                            td { +"Bishop" }
                            td { +"$bishop" }
                        }
                        tr {
                            td { +"Knight" }
                            td { +"$knight" }
                        }
                        tr {
                            td { +"Rook" }
                            td { +"$rook" }
                        }
                    }
                    p {
                        +"Check: ${match.check[PieceColor.WHITE]}"
                    }
                }
                div(classes = "player--black") {
                    p {
                        +"Player Black: ${match.players[PieceColor.BLACK]?.name}"
                    }
                    table(classes = "table--captured-pieces") {
                        var pawn = 0
                        var queen = 0
                        var bishop = 0
                        var knight = 0
                        var rook = 0
                        match.pieceSets[PieceColor.BLACK]?.capturedPieces?.forEach {
                            when (it.type) {
                                PieceType.PAWN -> pawn++
                                PieceType.QUEEN -> queen++
                                PieceType.BISHOP -> bishop++
                                PieceType.KNIGHT -> knight++
                                PieceType.ROOK -> rook++
                                else -> {
                                }
                            }
                        }
                        tr {
                            th {
                                +"Captured Pieces"
                                colSpan = "2"
                            }
                        }
                        tr {
                            td { +"Pawn" }
                            td { +"$pawn" }
                        }
                        tr {
                            td { +"Queen" }
                            td { +"$queen" }
                        }
                        tr {
                            td { +"Bishop" }
                            td { +"$bishop" }
                        }
                        tr {
                            td { +"Knight" }
                            td { +"$knight" }
                        }
                        tr {
                            td { +"Rook" }
                            td { +"$rook" }
                        }
                    }
                    p {
                        +"Check: ${match.check[PieceColor.BLACK]}"
                    }
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
                                        +draw.pieceType.toString()
                                    }
                                    td {
                                        +"${draw.start.row}${(draw.start.column + 64).toChar()}"
                                    }
                                    td {
                                        +"${draw.end.row}${(draw.end.column + 64).toChar()}"
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