package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.Controller
import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceColor
import htwdd.chessgame.client.model.PieceType
import kotlinx.html.div
import kotlinx.html.dom.create
import kotlinx.html.img
import kotlinx.html.p
import kotlinx.html.span
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class GameBoardPartial(val match: Match) : Partial {
    val activePiecesWhite = match.pieceSets[PieceColor.WHITE]?.activePieces
    val activePiecesBlack = match.pieceSets[PieceColor.BLACK]?.activePieces
    val basePath = "image/"

    override fun getPartial(controller: Controller): HTMLElement {
        return document.create.div(classes = "board") {
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
}