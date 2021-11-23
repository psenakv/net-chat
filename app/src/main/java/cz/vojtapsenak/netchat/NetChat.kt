package cz.vojtapsenak.netchat

import kotlinx.coroutines.*
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader


class NetChat {
    private suspend fun httpGet(request: String): String? = coroutineScope {

        val requestUrl = "http://10.0.2.2:23"
        var result : String = "Errors"
        println("Got to here")
        val parsedUrl = URL(requestUrl)
        launch {
            with(parsedUrl.openConnection() as HttpURLConnection) {
                requestMethod = "POST"  // optional default is GET
                val wr = OutputStreamWriter(getOutputStream());
                wr.write(request);
                wr.flush();

                println("\nSent 'POST' request to URL : $url; Response Code : $responseCode")

                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    println("Response : $response")
                }
            }
        }
        return@coroutineScope result
    }

    public fun sendRequest(length: String?, type: String?, uniqueId: String?, Data: String?, Code: String?) : Boolean = runBlocking {
        val request = prepareRequest(length, type, uniqueId, Data, Code)
        launch {
            val result = httpGet(request)
            print(result)
        }

        return@runBlocking true
    }

    private fun prepareRequest(length: String?, type: String?, uniqueId: String?, Data: String?, Code: String?) : String {
        return "$length|$type|$uniqueId|$Data|$Code"
    }
}