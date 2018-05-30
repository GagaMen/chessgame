package htwdd.chessgame.server.controller

import htwdd.chessgame.server.dto.DrawDTO
import htwdd.chessgame.server.model.Draw
import htwdd.chessgame.server.model.DrawList
import htwdd.chessgame.server.model.Field
import htwdd.chessgame.server.model.Match
import htwdd.chessgame.server.model.PieceColor.BLACK
import htwdd.chessgame.server.model.PieceColor.WHITE
import htwdd.chessgame.server.spring.web.config.AppProperties
import htwdd.chessgame.server.util.DatabaseUtility
import htwdd.chessgame.server.util.FENUtility.Companion.prepareFENForURLParam
import htwdd.chessgame.server.util.SANUtility
import htwdd.chessgame.server.util.SANUtility.Companion.calcSANFromAIMove
import kotlinx.coroutines.experimental.launch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.OPTIONS
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.sql.SQLException
import javax.servlet.http.HttpServletResponse

/**
 * Controller to manage the draw resource
 *
 * @author Felix Dimmel
 *
 * @property drawDao Object to interact with the database to manage draw objects
 * @property matchDao Object to interact with the database to manage match objects
 * @property fieldDao Object to interact with the database to manage field objects
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("/draws")
class DrawController @Autowired constructor(private var appProperties: AppProperties) {
    private val drawDao = DatabaseUtility.drawDao
    private val matchDao = DatabaseUtility.matchDao
    private val fieldDao = DatabaseUtility.fieldDao

    /**
     * Handles the OPTIONS request for the URI /draws
     *
     * Possible request methods:
     * - HEAD
     * - GET
     * - POST
     * - OPTIONS
     *
     * @author Felix Dimmel
     *
     * @param response Object that contains the response for the http request
     *
     * @since 1.0.0
     */
    @RequestMapping(method = [OPTIONS])
    fun drawOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,POST,OPTIONS")
    }

    /**
     * Handles the OPTIONS request for the URI /draws/{id}
     *
     * Possible request methods:
     * - HEAD
     * - GET
     * - OPTIONS
     *
     * @author Felix Dimmel
     *
     * @param response Object that contains the response for the http request
     *
     * @since 1.0.0
     */
    @RequestMapping("/{id}", method = [OPTIONS])
    fun drawByIdOptions(response: HttpServletResponse) {
        response.setHeader("Allow", "HEAD,GET,OPTIONS")
    }

    /**
     * Handles the GET request for the URI /draws
     *
     * @author Felix Dimmel
     *
     * @return List of draws
     *
     * @since 1.0.0
     */
    @GetMapping(produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE])
    fun getDrawList(): DrawList {
        val drawList: MutableList<Draw> = mutableListOf()
        drawDao!!.queryForAll().forEach {
            it.setValuesByDrawCode()
            drawList.add(it)
        }
        return DrawList(drawList)
    }

    /**
     * Handle the GET request for URI /draws/{id}
     *
     * @author Felix Dimmel
     *
     * @param id Identifier for a single draw
     *
     * @return Single draw by an id
     *
     * @since 1.0.0
     */
    @GetMapping(
            value = ["/{id}"],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun getDrawById(
            @PathVariable
            id: Int
    ): Draw {
        return drawDao!!.queryForId(id) ?: throw IllegalArgumentException("No draw with id '$id' registered!")
    }

    /**
     * Handles the POST request for the URI /draws
     * Params encoded as application/x-www-form-urlencode
     *
     * @author Felix Dimmel
     *
     * @param matchId Match reference
     * @param drawCode SAN code to indicate draw properties
     * @param startColumn Value to indicate the column of start field
     * @param startRow Value to indicate the row of start field
     *
     * @return Created draw
     *
     * @since 1.0.0
     */
    @PostMapping(
            consumes = [APPLICATION_FORM_URLENCODED_VALUE],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    @ResponseStatus(CREATED)
    fun addDraw(
            @RequestParam
            matchId: Int,
            @RequestParam
            drawCode: String,
            @RequestParam(required = false)
            startColumn: Int? = null,
            @RequestParam(required = false)
            startRow: Int? = null
    ): Draw {
        val match = matchDao!!.queryForId(matchId)
                ?: throw IllegalArgumentException("No match with the id '$matchId' registered!")

        val draw = Draw(
                color = match.currentColor,
                match = match,
                drawCode = drawCode
        )

        if (startRow != null && startColumn != null) draw.startField = Field(row = startRow, column = startColumn)
        draw.setValuesByDrawCode()

        if (!SANUtility.validateSAN(draw, match)) throw RuntimeException("The draw isn't valid!")

        if (fieldDao!!.create(draw.startField) != 1) throw SQLException("Can't create start field!")
        if (fieldDao.create(draw.endField) != 1) throw SQLException("Can't create end field!")

        if (drawDao!!.create(draw) != 1) {
            fieldDao.delete(draw.startField)
            fieldDao.delete(draw.endField)
            throw SQLException("Can't create draw!")
        }

        match.updateByDraw(draw)
        matchDao.update(match)

        if (match.players[WHITE]?.id == 1 || match.players[BLACK]?.id == 1) {
            // launch calls the function addDrawByAi asynchronous
            launch {
                addDrawByAi(match)
            }
        }

        return draw
    }

    /**
     * Handles the POST request for the URI /draws
     * Params encoded as application/json
     *
     * @author Felix Dimmel
     *
     * @param drawDTO Contains params as data transfer object
     *
     * @see addDraw
     * @see DrawDTO
     *
     * @return Created draw
     *
     * @since 1.0.0
     */
    @PostMapping(
            consumes = [APPLICATION_JSON_VALUE],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    @ResponseStatus(CREATED)
    fun addDrawWithJson(
            @RequestBody
            drawDTO: DrawDTO
    ): Draw {
        val match = matchDao!!.queryForId(drawDTO.matchId)
                ?: throw IllegalArgumentException("No match with the id '${drawDTO.matchId}' registered!")

        val draw = Draw(
                color = match.currentColor,
                match = match,
                drawCode = drawDTO.drawCode
        )

        if (drawDTO.startRow != null && drawDTO.startColumn != null) {
            draw.startField = Field(row = drawDTO.startRow, column = drawDTO.startColumn)
        }
        draw.setValuesByDrawCode()

        if (!SANUtility.validateSAN(draw, match)) throw RuntimeException("The draw isn't valid!")

        if (fieldDao!!.create(draw.startField) != 1) throw SQLException("Can't create start field!")
        if (fieldDao.create(draw.endField) != 1) throw SQLException("Can't create end field!")

        if (drawDao!!.create(draw) != 1) {
            fieldDao.delete(draw.startField)
            fieldDao.delete(draw.endField)
            throw SQLException("Can't create draw!")
        }

        match.updateByDraw(draw)
        matchDao.update(match)

        if (match.players[WHITE]?.id == 1 || match.players[BLACK]?.id == 1) {
            launch {
                addDrawByAi(match)
            }
        }

        return draw
    }

    /**
     * Adds a draw from ai (stockfish) engine by given match
     *
     * @author Felix Dimmel
     *
     * @param match Match to which the draw is to be added
     *
     * @since 1.0.0
     */
    private fun addDrawByAi(match: Match) {
        val urlNextAiDraw = URL("${appProperties.aiServerRootUrl}/game?fen=${prepareFENForURLParam(match.matchCode)}")
        val nextDraw = readFromAIRequest(urlNextAiDraw)

        val regexBestMove = "\"bestMove\": \"([a-h][1-8][a-h][1-8])\"".toRegex()
        val matchResultsBestMove = regexBestMove.find(nextDraw)
                ?: throw Exception("Can't find best move from ai result!")
        val bestMove = matchResultsBestMove.groups[1]?.value ?: throw Exception("Best move was empty!")

        val urlDrawResult = URL("${appProperties.aiServerRootUrl}/game?fen=${prepareFENForURLParam(match.matchCode)}&move=$bestMove")
        val drawResult = readFromAIRequest(urlDrawResult)
        val checkmate = drawResult.contains("\"isCheckmate\": true".toRegex())
        val check = drawResult.contains("\"isInCheck\": true".toRegex())

        val fieldInfo = bestMove.split("")
        val startField = Field(column = fieldInfo[1].toCharArray()[0].toInt() % 96, row = fieldInfo[2].toInt())
        val endField = Field(column = fieldInfo[3].toCharArray()[0].toInt() % 96, row = fieldInfo[4].toInt())

        val san = calcSANFromAIMove(startField, endField, checkmate, check, match)

        val draw = Draw(
                color = match.currentColor,
                match = match,
                drawCode = san,
                startField = startField
        )
        draw.setValuesByDrawCode()

        if (!SANUtility.validateSAN(draw, match)) throw Exception("The ai draw isn't valid!")

        if (fieldDao!!.create(draw.startField) != 1) throw SQLException("Can't create start field!")
        if (fieldDao.create(draw.endField) != 1) throw SQLException("Can't create end field!")

        if (drawDao!!.create(draw) != 1) {
            fieldDao.delete(draw.startField)
            fieldDao.delete(draw.endField)
            throw SQLException("Can't create draw!")
        }

        match.updateByDraw(draw)
        matchDao!!.update(match)
    }

    /**
     * Reads and returns the ai response body
     *
     * @author Felix Dimmel
     *
     * @param url Request url for ai engine
     *
     * @return Response as string
     */
    private fun readFromAIRequest(url: URL): String {
        val connection = url.openConnection()
        val bufferedReader = BufferedReader(
                InputStreamReader(
                        connection.getInputStream()))
        var inputLine = bufferedReader.readLine()
        var aiDrawAsJSON = ""

        while (inputLine != null) {
            aiDrawAsJSON += inputLine
            inputLine = bufferedReader.readLine()
        }
        bufferedReader.close()

        return aiDrawAsJSON
    }
}