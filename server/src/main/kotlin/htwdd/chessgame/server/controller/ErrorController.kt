package htwdd.chessgame.server.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class ErrorController {
    @RequestMapping("*", "**/*", method = [RequestMethod.GET, RequestMethod.POST])
    fun wrongRequestFallback(): String {
        return "Wrong request"
    }
}