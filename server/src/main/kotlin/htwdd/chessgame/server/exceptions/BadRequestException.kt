package htwdd.chessgame.server.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Exception class for bad requests
 * Thrown when requesting an undefined entry point
 *
 * @author Felix Dimmel
 *
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class BadRequestException(message: String = "No message available") : Exception(message)