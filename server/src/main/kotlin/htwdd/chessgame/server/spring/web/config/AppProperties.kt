package htwdd.chessgame.server.spring.web.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app")
class AppProperties(var aiServerRootUrl: String = "")