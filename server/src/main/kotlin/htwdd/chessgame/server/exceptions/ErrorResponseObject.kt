package htwdd.chessgame.server.exceptions

import org.springframework.http.HttpStatus
import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class ErrorResponseObject(
        @XmlElement
        val timestamp: Date = Date(),
        @XmlElement
        val statusCode: Int = 500,
        @XmlElement
        val error: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
        @XmlElement
        val exception: String = "",
        @XmlElement
        val message: String = "No message available",
        @XmlElement
        val path: String = ""
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