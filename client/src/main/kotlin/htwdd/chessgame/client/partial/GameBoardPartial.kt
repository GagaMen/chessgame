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
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.dom.addClass

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
                                onDropFunction = { event -> DraggableUtility.drop(event, controller, match) }
                                onDragOverFunction = { event -> DraggableUtility.dragOver(event) }

                                if (activePiecesWhite != null && activePiecesWhite.contains(Pair(i, j).toString())) {
                                    img(classes = "piece--white") {
                                        onDragStartFunction = { event -> DraggableUtility.dragStart(event, match) }
                                        onDragEndFunction = { DraggableUtility.dragEnd(match) }
                                        onMouseOverFunction = { event -> DraggableUtility.mouseOver(event, match) }
                                        onMouseOutFunction = { DraggableUtility.mouseOut() }
                                        id = "$i$j"
                                        when (activePiecesWhite[Pair(i, j).toString()]?.type) {
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
                                if (activePiecesBlack != null && activePiecesBlack.contains(Pair(i, j).toString())) {
                                    img(classes = "piece--black") {
                                        onDragStartFunction = { event -> DraggableUtility.dragStart(event, match) }
                                        onDragEndFunction = { DraggableUtility.dragEnd(match) }
                                        onMouseOverFunction = { event -> DraggableUtility.mouseOver(event, match) }
                                        onMouseOutFunction = { DraggableUtility.mouseOut() }
                                        id = "$i$j"
                                        when (activePiecesBlack[Pair(i, j).toString()]?.type) {
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
            div(classes = "board--popup hidden") {
                h2(classes = "text--center") {
                    +"Pawn conversion"
                }
                button(classes = "btn btn--piece-type") {
                    +"Queen"
                    onClickFunction = { e ->
                        e.preventDefault()
                        convertPiece(PieceType.QUEEN)
                        controller.actionPerformed("convertPieceAction", Pair(match, PieceType.QUEEN))
                    }
                }
                button(classes = "btn btn--piece-type") {
                    +"Bishop"
                    onClickFunction = { e ->
                        e.preventDefault()
                        convertPiece(PieceType.BISHOP)
                        controller.actionPerformed("convertPieceAction", Pair(match, PieceType.BISHOP))
                    }
                }
                button(classes = "btn btn--piece-type") {
                    +"Rook"
                    onClickFunction = { e ->
                        e.preventDefault()
                        convertPiece(PieceType.ROOK)
                        controller.actionPerformed("convertPieceAction", Pair(match, PieceType.ROOK))
                    }
                }
                button(classes = "btn btn--piece-type") {
                    +"Knight"
                    onClickFunction = { e ->
                        e.preventDefault()
                        convertPiece(PieceType.KNIGHT)
                        controller.actionPerformed("convertPieceAction", Pair(match, PieceType.KNIGHT))
                    }
                }
            }
        }
    }

    private fun convertPiece(pieceType: PieceType) {
        val popup = document.getElementsByClassName("board--popup")[0]
        val row = popup!!.attributes["data-row"]?.nodeValue?.toIntOrNull()
        val col = popup.attributes["data-col"]?.nodeValue?.toIntOrNull()

        if (row != null && col != null) {
            val image = document.getElementById("board--field-$row-$col")?.firstElementChild
            val pieceColor = match.currentColor.getOpposite()
            when (pieceType) {
                PieceType.QUEEN -> {
                    image!!.attributes["data-type"]?.nodeValue = PieceType.QUEEN.toString()
                    image.attributes["src"]?.nodeValue = basePath + "queen_${pieceColor.toString().toLowerCase()}.svg"
                }
                PieceType.BISHOP -> {
                    image!!.attributes["data-type"]?.nodeValue = PieceType.BISHOP.toString()
                    image.attributes["src"]?.nodeValue = basePath + "bishop_${pieceColor.toString().toLowerCase()}.svg"
                }
                PieceType.ROOK -> {
                    image!!.attributes["data-type"]?.nodeValue = PieceType.ROOK.toString()
                    image.attributes["src"]?.nodeValue = basePath + "rook_${pieceColor.toString().toLowerCase()}.svg"
                }
                PieceType.KNIGHT -> {
                    image!!.attributes["data-type"]?.nodeValue = PieceType.KNIGHT.toString()
                    image.attributes["src"]?.nodeValue = basePath + "knight_${pieceColor.toString().toLowerCase()}.svg"
                }
                else -> {
                }
            }
            popup.addClass("hidden")
        }
    }
}