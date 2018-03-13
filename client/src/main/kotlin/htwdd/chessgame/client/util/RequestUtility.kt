package htwdd.chessgame.client.util

import org.w3c.dom.events.Event
import org.w3c.xhr.XMLHttpRequest

class RequestUtility {
    companion object {
        fun get(url: String, callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("GET", url)
            request.send()
        }

        fun post(url: String, vararg params: Pair<String, Any> = arrayOf(), callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("POST", url)
            var paramsAsJson = ""
            params.forEach { (key, value) ->
                if (paramsAsJson != "") paramsAsJson += "&"
                paramsAsJson += "$key=$value"
            }
            request.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
            request.send(paramsAsJson)
        }

        fun put(url: String, callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("PUT", url)
            request.send()
        }

        fun patch(url: String, callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("PATCH", url)
            request.send()
        }

        fun delete(url: String, callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("DELETE", url)
            request.send()
        }

        fun head(url: String, callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("HEAD", url)
            request.send()
        }

        fun options(url: String, callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("OPTIONS", url)
            request.send()
        }
    }
}