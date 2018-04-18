package htwdd.chessgame.server.controller

import htwdd.chessgame.server.exceptions.BadRequestException
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class ErrorController {

    @CrossOrigin(origins = ["http://localhost:63342"])
    @RequestMapping("*", "**/*", method = [
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.PATCH,
        RequestMethod.DELETE,
        RequestMethod.HEAD,
        RequestMethod.OPTIONS]
    )
    fun wrongRequestFallback() {
        throw BadRequestException("The request method isn't defined on this entry point.")
    }
}