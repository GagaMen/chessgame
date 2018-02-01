package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.Controller
import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceColor
import htwdd.chessgame.client.model.PieceType
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.div
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class GamePartial(val match: Match) : Partial {
    override fun getPartial(controller: Controller): HTMLElement {
        val activePiecesWhite = match.pieceSets[PieceColor.WHITE]?.activePieces
        val activePiecesBlack = match.pieceSets[PieceColor.BLACK]?.activePieces
        val basePath = "image/"

        return document.create.div(classes = "container") {
            div(classes = "row") {
                div(classes = "col-sm-12") {
                    h1(classes = "text--center") {
                        +"Match: ${match.players[PieceColor.WHITE]?.name} vs. ${match.players[PieceColor.BLACK]?.name}"
                    }
                }
            }
            div(classes = "row") {
                div(classes = "col-sm-8") {
                    div(classes = "board") {
                        div(classes = "board--legend-left") {
                            p { span { +"1" } }
                            p { span { +"2" } }
                            p { span { +"3" } }
                            p { span { +"4" } }
                            p { span { +"5" } }
                            p { span { +"6" } }
                            p { span { +"7" } }
                            p { span { +"8" } }
                        }
                        div(classes = "board--legend-top") {
                            p { span { +"A" } }
                            p { span { +"B" } }
                            p { span { +"C" } }
                            p { span { +"D" } }
                            p { span { +"E" } }
                            p { span { +"F" } }
                            p { span { +"G" } }
                            p { span { +"H" } }
                        }
                        div(classes = "board--legend-right") {
                            p { span { +"1" } }
                            p { span { +"2" } }
                            p { span { +"3" } }
                            p { span { +"4" } }
                            p { span { +"5" } }
                            p { span { +"6" } }
                            p { span { +"7" } }
                            p { span { +"8" } }
                        }
                        div(classes = "board--legend-bottom") {
                            p { span { +"A" } }
                            p { span { +"B" } }
                            p { span { +"C" } }
                            p { span { +"D" } }
                            p { span { +"E" } }
                            p { span { +"F" } }
                            p { span { +"G" } }
                            p { span { +"H" } }
                        }
                        for (i in 1..8) {
                            div(classes = "board--row") {
                                for (j in 1..8) {
                                    div(classes = "board--col") {
                                        div(classes = "board--field") {
                                            if (activePiecesWhite != null && activePiecesWhite.contains(Pair(i, j))) {
                                                img(classes = "piece--white") {
                                                    src = when (activePiecesWhite[Pair(i, j)]?.type) {
                                                        PieceType.BISCHOP -> basePath + "bishop_white.svg"
                                                        PieceType.KING -> basePath + "king_white.svg"
                                                        PieceType.KNIGHT -> basePath + "knight_white.svg"
                                                        PieceType.PAWN -> basePath + "pawn_white.svg"
                                                        PieceType.QUEEN -> basePath + "queen_white.svg"
                                                        PieceType.ROOK -> basePath + "rook_white.svg"
                                                        else -> ""
                                                    }
                                                }
                                            }
                                            if (activePiecesBlack != null && activePiecesBlack.contains(Pair(i, j))) {
                                                img(classes = "piece--white") {
                                                    src = when (activePiecesBlack[Pair(i, j)]?.type) {
                                                        PieceType.BISCHOP -> basePath + "bishop_black.svg"
                                                        PieceType.KING -> basePath + "king_black.svg"
                                                        PieceType.KNIGHT -> basePath + "knight_black.svg"
                                                        PieceType.PAWN -> basePath + "pawn_black.svg"
                                                        PieceType.QUEEN -> basePath + "queen_black.svg"
                                                        PieceType.ROOK -> basePath + "rook_black.svg"
                                                        else -> ""
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                div(classes = "col-sm-4") {
                    div(classes = "properties") {
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
                                    if (match.history.size > 0) {
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
        }
    }
}