package htwdd.chessgame.client.partial

import htwdd.chessgame.client.controller.Controller
import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceColor
import htwdd.chessgame.client.model.PieceType
import htwdd.chessgame.client.util.DraggableUtility
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.*
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class GameBoardPartial(val match: Match) : Partial {
    private val activePiecesWhite = match.pieceSets[PieceColor.WHITE]?.activePieces
    private val activePiecesBlack = match.pieceSets[PieceColor.BLACK]?.activePieces
    private val basePath = "image/"

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
                    id = "board--row-$i"
                    for (j in 1..8) {
                        div(classes = "board--col") {
                            id = "board--col-$j"
                            div(classes = "board--field") {
                                id = "board--field-$i-$j"
                                attributes["data-row"] = i.toString()
                                attributes["data-col"] = j.toString()
                                onDropFunction = { event -> DraggableUtility.drop(event) }
                                onDragOverFunction = { event -> DraggableUtility.dragOver(event) }

                                if (activePiecesWhite != null && activePiecesWhite.contains(Pair(i, j))) {
                                    img(classes = "piece--white") {
                                        onDragStartFunction = { event -> DraggableUtility.dragStart(event, match) }
                                        onDragEndFunction = { event -> DraggableUtility.dragEnd(event) }
                                        onMouseOverFunction = { event -> DraggableUtility.mouseOver(event, match) }
                                        onMouseOutFunction = { event -> DraggableUtility.mouseOut(event) }
                                        id = "$i$j"
                                        when (activePiecesWhite[Pair(i, j)]?.type) {
                                            PieceType.BISHOP -> {
                                                src = basePath + "bishop_white.svg"
                                                attributes["data-type"] = PieceType.BISHOP.toString()
                                            }
                                            PieceType.KING -> {
                                                src = basePath + "king_white.svg"
                                                attributes["data-type"] = PieceType.KING.toString()
                                            }
                                            PieceType.KNIGHT -> {
                                                src = basePath + "knight_white.svg"
                                                attributes["data-type"] = PieceType.KNIGHT.toString()
                                            }
                                            PieceType.PAWN -> {
                                                src = basePath + "pawn_white.svg"
                                                attributes["data-type"] = PieceType.PAWN.toString()
                                            }
                                            PieceType.QUEEN -> {
                                                src = basePath + "queen_white.svg"
                                                attributes["data-type"] = PieceType.QUEEN.toString()
                                            }
                                            PieceType.ROOK -> {
                                                src = basePath + "rook_white.svg"
                                                attributes["data-type"] = PieceType.ROOK.toString()
                                            }
                                        }
                                    }
                                }
                                if (activePiecesBlack != null && activePiecesBlack.contains(Pair(i, j))) {
                                    img(classes = "piece--black") {
                                        onDragStartFunction = { event -> DraggableUtility.dragStart(event, match) }
                                        onDragEndFunction = { event -> DraggableUtility.dragEnd(event) }
                                        onMouseOverFunction = { event -> DraggableUtility.mouseOver(event, match) }
                                        onMouseOutFunction = { event -> DraggableUtility.mouseOut(event) }
                                        id = "$i$j"
                                        when (activePiecesBlack[Pair(i, j)]?.type) {
                                            PieceType.BISHOP -> {
                                                src = basePath + "bishop_black.svg"
                                                attributes["data-type"] = PieceType.BISHOP.toString()
                                            }
                                            PieceType.KING -> {
                                                src = basePath + "king_black.svg"
                                                attributes["data-type"] = PieceType.KING.toString()
                                            }
                                            PieceType.KNIGHT -> {
                                                src = basePath + "knight_black.svg"
                                                attributes["data-type"] = PieceType.KNIGHT.toString()
                                            }
                                            PieceType.PAWN -> {
                                                src = basePath + "pawn_black.svg"
                                                attributes["data-type"] = PieceType.PAWN.toString()
                                            }
                                            PieceType.QUEEN -> {
                                                src = basePath + "queen_black.svg"
                                                attributes["data-type"] = PieceType.QUEEN.toString()
                                            }
                                            PieceType.ROOK -> {
                                                src = basePath + "rook_black.svg"
                                                attributes["data-type"] = PieceType.ROOK.toString()
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