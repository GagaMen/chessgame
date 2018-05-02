package htwdd.chessgame.server.spring.web.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.resource.PathResourceResolver

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurerAdapter() {

    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer?) {
        configurer!!.favorPathExtension(true).favorParameter(true).parameterName("mediaType").ignoreAcceptHeader(false).useJaf(false).defaultContentType(MediaType.APPLICATION_JSON).mediaType("xml", MediaType.APPLICATION_XML).mediaType("json", MediaType.APPLICATION_JSON)
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
        registry!!
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/", "classpath:/META-INF/resources/", "classpath:/static/", "classpath:/public/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(PathResourceResolver())
    }
}