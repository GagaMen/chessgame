package htwdd.chessgame.server.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.util.*

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(value = [BadRequestException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(ex: Exception, request: WebRequest): ErrorResponseObject {
        return generateErrorResponseObject(ex, request, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [IllegalArgumentException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleIllegalArgumentException(ex: Exception, request: WebRequest): ErrorResponseObject {
        return generateErrorResponseObject(ex, request, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [RuntimeException::class])
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleRuntimeException(ex: Exception, request: WebRequest): ErrorResponseObject {
        return generateErrorResponseObject(ex, request, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleUnknownException(ex: Exception, request: WebRequest): ErrorResponseObject {
        return generateErrorResponseObject(ex, request, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun generateErrorResponseObject(ex: Exception, request: WebRequest, statusCode: HttpStatus): ErrorResponseObject {
        return ErrorResponseObject(
                Date(),
                statusCode.value(),
                statusCode,
                ex::class.simpleName ?: "",
                ex.message ?: "No message available",
                request.getDescription(false)
        )
    }
}