package id.co.kasrt

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

fun sendNotification(apiKey: String, deviceToken: String) {
    val client = OkHttpClient()

    val json = """
        {
            "to": "$deviceToken",
            "notification": {
                "title": "Hello",
                "body": "Yellow"
            }
        }
    """.trimIndent()

    val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)
    val request = Request.Builder()
        .url("https://fcm.googleapis.com/fcm/send")
        .post(body)
        .addHeader("Authorization", "key=$apiKey")
        .addHeader("Content-Type", "application/json")
        .build()

    client.newCall(request).enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: okhttp3.Call, response: Response) {
            response.use {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                println(response.body?.string())
            }
        }
    })
}
