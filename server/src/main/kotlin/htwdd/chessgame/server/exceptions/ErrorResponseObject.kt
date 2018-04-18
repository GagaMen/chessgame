package htwdd.chessgame.server.exceptions

import org.springframework.http.HttpStatus
import java.util.*

class ErrorResponseObject(
        val timestamp: Date,
        val statusCode: Int,
        val error: HttpStatus,
        val exception: String,
        val message: String,
        val path: String
) {

    override fun toString(): String {
        return "ErrorResponseObject{" +
                "timestamp=" + timestamp +
                ", status=" + statusCode +
                ", error=" + error +
                ", exception=" + exception +
                ", message=" + message +
                ", path=" + path +
                '}'.toString()
    }
}