package htwdd.chessgame.client.util

import org.w3c.dom.events.Event
import org.w3c.xhr.XMLHttpRequest
import kotlin.js.Promise

class RequestUtility {
    companion object {
        fun get(url: String, vararg params: Pair<String, Any> = arrayOf(), callback: ((Event) -> dynamic)? = null): Promise<XMLHttpRequest> {
            return Promise { resolve, reject ->
                val request = XMLHttpRequest()

                if (params.isNotEmpty()) request.open("GET", "$url?${parseParams(params)}")
                else request.open("GET", url)

                request.addEventListener("load", callback)
                request.addEventListener("load", { e -> resolve(request) })
                request.send()
            }

        }

        fun post(url: String, vararg params: Pair<String, Any> = arrayOf(), callback: ((Event) -> dynamic)? = null): Promise<XMLHttpRequest> {
            return Promise { resolve, reject ->
                val request = XMLHttpRequest()

                request.open("POST", url)
                request.addEventListener("load", callback)
                request.addEventListener("load", { e -> resolve(request) })
                request.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
                request.send(parseParams(params))
            }
        }

        fun put(url: String, vararg params: Pair<String, Any> = arrayOf(), callback: ((Event) -> dynamic)? = null): Promise<XMLHttpRequest> {
            return Promise { resolve, reject ->
                val request = XMLHttpRequest()

                request.open("PUT", url)
                request.addEventListener("load", callback)
                request.addEventListener("load", { e -> resolve(request) })
                request.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
                request.send(parseParams(params))
            }
        }

        fun patch(url: String, vararg params: Pair<String, Any> = arrayOf(), callback: ((Event) -> dynamic)? = null): Promise<XMLHttpRequest> {
            return Promise { resolve, reject ->
                val request = XMLHttpRequest()

                request.open("PATCH", url)
                request.addEventListener("load", callback)
                request.addEventListener("load", { e -> resolve(request) })
                request.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
                request.send(parseParams(params))
            }
        }

        fun delete(url: String, vararg params: Pair<String, Any> = arrayOf(), callback: ((Event) -> dynamic)? = null): Promise<XMLHttpRequest> {
            return Promise { resolve, reject ->
                val request = XMLHttpRequest()

                request.open("DELETE", url)
                request.addEventListener("load", callback)
                request.addEventListener("load", { e -> resolve(request) })
                request.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
                request.send(parseParams(params))
            }
        }

        fun head(url: String, callback: ((Event) -> dynamic)? = null): Promise<XMLHttpRequest> {
            return Promise { resolve, reject ->
                val request = XMLHttpRequest()

                request.open("HEAD", url)
                request.addEventListener("load", callback)
                request.addEventListener("load", { e -> resolve(request) })
                request.send()
            }
        }

        fun options(url: String, callback: ((Event) -> dynamic)? = null): Promise<XMLHttpRequest> {
            return Promise { resolve, reject ->
                val request = XMLHttpRequest()

                request.open("OPTIONS", url)
                request.addEventListener("load", callback)
                request.addEventListener("load", { e -> resolve(request) })
                request.send()
            }
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