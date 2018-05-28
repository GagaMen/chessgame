package htwdd.chessgame.server.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.util.*

/**
 * Handler object to manage exceptions
 */
@RestControllerAdvice
class ExceptionHandler {

    /**
     * Handles BadRequestException
     *
     * @author Felix Dimmel
     *
     * @param ex BadRequestException
     * @param request Http request object
     *
     * @return ErrorResponseObject
     *
     * @since 1.0.0
     */
    @ExceptionHandler(value = [BadRequestException::class])
    @ResponseStatus(BAD_REQUEST)
    fun handleBadRequestException(ex: Exception, request: WebRequest): ErrorResponseObject {
        return generateErrorResponseObject(ex, request, BAD_REQUEST)
    }

    /**
     * Handles IllegalArgumentException
     *
     * @author Felix Dimmel
     *
     * @param ex IllegalArgumentException
     * @param request Http request object
     *
     * @return ErrorResponseObject
     *
     * @since 1.0.0
     */
    @ExceptionHandler(value = [IllegalArgumentException::class])
    @ResponseStatus(NOT_FOUND)
    fun handleIllegalArgumentException(ex: Exception, request: WebRequest): ErrorResponseObject {
        return generateErrorResponseObject(ex, request, NOT_FOUND)
    }

    /**
     * Handles RuntimeException
     *
     * @author Felix Dimmel
     *
     * @param ex RuntimeException
     * @param request Http request object
     *
     * @return ErrorResponseObject
     *
     * @since 1.0.0
     */
    @ExceptionHandler(value = [RuntimeException::class])
    @ResponseStatus(CONFLICT)
    fun handleRuntimeException(ex: Exception, request: WebRequest): ErrorResponseObject {
        return generateErrorResponseObject(ex, request, CONFLICT)
    }

    /**
     * Handles all unforeseen exception
     *
     * @author Felix Dimmel
     *
     * @param ex InternalServerError
     * @param request Http request object
     *
     * @return ErrorResponseObject
     *
     * @since 1.0.0
     */
    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun handleUnknownException(ex: Exception, request: WebRequest): ErrorResponseObject {
        return generateErrorResponseObject(ex, request, INTERNAL_SERVER_ERROR)
    }

    /**
     * Creates an ErrorResponseObject  by given exception, request and HTTP status
     *
     * @author Felix Dimmel
     *
     * @param ex thrown exception
     * @param request Http request object
     * @param statusCode Http status code
     *
     * @return created error response object
     *
     * @since 1.0.0
     */
    private fun generateErrorResponseObject(
            ex: Exception,
            request: WebRequest,
            statusCode: HttpStatus
    ): ErrorResponseObject {
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