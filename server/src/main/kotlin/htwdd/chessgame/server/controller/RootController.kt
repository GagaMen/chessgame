package htwdd.chessgame.server.controller

import htwdd.chessgame.server.util.HateoasUtility
import org.springframework.hateoas.Link
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

/**
 * Controller to handels the root resource
 *
 * @author Felix Dimmel
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api")
class RootController {
    /**
     * Handles the GET request for the URI /api
     *
     * @author Felix Dimmel
     *
     * @param response Object that contains the response for the http request
     *
     * @since 1.0.0
     */
    @GetMapping
    fun rootRequest(response: HttpServletResponse): ResponseEntity<Unit> {
        val links = HashSet<Pair<Link, String>>()

        val selfLink = linkTo(methodOn(RootController::class.java).rootRequest(response)).withSelfRel()
        links.add(Pair(selfLink, "GET"))
        val playerLink = linkTo(methodOn(PlayerController::class.java).getPlayerList(response)).withRel("next")
        links.add(Pair(playerLink, "GET"))
        val matchLink = linkTo(methodOn(MatchController::class.java).getMatchList(true, true, response)).withRel("next")
        links.add(Pair(matchLink, "GET"))
        val drawLink = linkTo(methodOn(DrawController::class.java).getDrawList(response)).withRel("next")
        links.add(Pair(drawLink, "GET"))

        return ResponseEntity(HateoasUtility.createLinkHeader(links), OK)
    }
}