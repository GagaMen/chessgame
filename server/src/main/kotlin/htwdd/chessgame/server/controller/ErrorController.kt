package htwdd.chessgame.server.controller

import htwdd.chessgame.server.exceptions.BadRequestException
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.APPLICATION_XML_VALUE
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.*
import org.springframework.web.bind.annotation.RestController

/**
 * Controller to handle all undefined entry points
 *
 * @author Felix Dimmel
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api")
class ErrorController {
    /**
     * Handles all request on undefined entry points
     *
     * @author Felix Dimmel
     *
     * @throws BadRequestException
     *
     * @since 1.0.0
     */
    @RequestMapping(
            value = ["*", "**/*"],
            method = [
                GET,
                POST,
                PUT,
                PATCH,
                DELETE,
                HEAD,
                OPTIONS
            ],
            produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE]
    )
    fun wrongRequestFallback() {
        throw BadRequestException("The request method isn't defined on this entry point.")
    }
}