package htwdd.chessgame.server.spring.web.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Custom spring configuration object
 *
 * @author Felix Dimmel
 *
 * @property aiServerRootUrl Contains the ai server root url (default: http://chess-ai:5000 -> for docker)
 *
 * @since 1.0.0
 */
@Component
@ConfigurationProperties(prefix = "app")
class AppProperties(var aiServerRootUrl: String = "")