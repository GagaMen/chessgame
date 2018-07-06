package htwdd.chessgame.server.util

import org.springframework.hateoas.Link
import org.springframework.http.HttpHeaders

/**
 * Utility class to handle creation of link header for HATEOAS support
 *
 * @author Felix Dimmel
 *
 * @since 1.0.0
 */
class HateoasUtility {
    /**
     * Static HateoasUtility object
     */
    companion object {
        /**
         * Creates the link header by a list of links
         *
         * @param linkList Contains a list of links
         *
         * @return HttpHeaders object with set link header
         */
        fun createLinkHeader(linkList: HashSet<Pair<Link, String>>): HttpHeaders {
            val headers = HttpHeaders()
            val sb = StringBuilder()

            linkList.forEach { (link, verb) ->
                sb.append("<${link.href}>,rel=${link.rel},verb='$verb'; ")
            }

            headers.set("Link", sb.toString())

            return headers
        }
    }
}