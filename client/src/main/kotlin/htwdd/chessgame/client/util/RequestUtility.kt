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
            request.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
            request.send(parseParams(params))
        }

        fun put(url: String, vararg params: Pair<String, Any> = arrayOf(), callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("PUT", url)
            request.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
            request.send(parseParams(params))
        }

        fun patch(url: String, vararg params: Pair<String, Any> = arrayOf(), callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("PATCH", url)
            request.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
            request.send(parseParams(params))
        }

        fun delete(url: String, vararg params: Pair<String, Any> = arrayOf(), callback: ((Event) -> dynamic)? = null) {
            val request = XMLHttpRequest()

            if (callback != null) request.onload = callback
            request.open("DELETE", url)
            request.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
            request.send(parseParams(params))
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

        private fun parseParams(params: Array<out Pair<String, Any>>): String {
            var paramsAsJSONString = ""

            params.forEach { (key, value) ->
                if (paramsAsJSONString != "") paramsAsJSONString += "&"
                paramsAsJSONString += "$key=$value"
            }

            return paramsAsJSONString
        }
    }
}