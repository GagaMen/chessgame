package htwdd.chessgame.server.util

import org.springframework.hateoas.Link
import org.springframework.http.HttpHeaders

class HateoasUtility {
    companion object {
        fun createLinkHeader(linkList: HashSet<Pair<Link, String>>): HttpHeaders {
            val headers = HttpHeaders()
            val sb = StringBuilder()

            linkList.forEach { (link, verb) ->
                sb.append("<${link.href}>;rel=${link.rel};verb='$verb'")
            }

            headers.set("Link", sb.toString())

            return headers
        }
    }
}