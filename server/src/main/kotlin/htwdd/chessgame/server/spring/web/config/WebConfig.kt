package htwdd.chessgame.server.spring.web.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

/**
 * Spring web configuration object
 *
 * @author Felix Dimmel
 *
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(AppProperties::class)
class WebConfig : WebMvcConfigurerAdapter() {
    /**
     * Configuration for content negotiation (3 strategies)
     * 1. URL suffix strategy
     * 2. URL parameter strategy
     * 3. Accept header strategy
     *
     * @author Felix Dimmel
     *
     * @since 1.0.0
     */
    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer?) {
        configurer!!.favorPathExtension(true).favorParameter(true).parameterName("mediaType").ignoreAcceptHeader(false).useJaf(false).defaultContentType(MediaType.APPLICATION_JSON).mediaType("xml", MediaType.APPLICATION_XML).mediaType("json", MediaType.APPLICATION_JSON)
    }
}