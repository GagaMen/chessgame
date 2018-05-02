package htwdd.chessgame.server.spring.web.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
//@EnableWebMvc
class WebConfig : WebMvcConfigurerAdapter() {

    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer?) {
        configurer!!.favorPathExtension(true).favorParameter(true).parameterName("mediaType").ignoreAcceptHeader(false).useJaf(false).defaultContentType(MediaType.APPLICATION_JSON).mediaType("xml", MediaType.APPLICATION_XML).mediaType("json", MediaType.APPLICATION_JSON)
    }
}