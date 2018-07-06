package htwdd.chessgame.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer

/**
 * Spring application class
 *
 * @author Felix Dimmel
 *
 * @since 1.0.0
 */
@SpringBootApplication
class Application : SpringBootServletInitializer() {
    /**
     * Overrides the configure function of the spring framework
     */
    override fun configure(builder: SpringApplicationBuilder?): SpringApplicationBuilder {
        return builder!!.sources(Application::class.java)
    }
}

/**
 * Starts the spring application
 *
 * @author Felix Dimmel
 *
 * @since 1.0.0
 */
fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}