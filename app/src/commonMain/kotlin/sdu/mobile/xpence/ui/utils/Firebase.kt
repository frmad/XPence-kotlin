package sdu.mobile.xpence.ui.utils

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import sdu.mobile.xpence.httpClient

val FCM_KEY =
    "AAAAcUsxjkw:APA91bFp_5NKnXC7eZhbFyTJjtuNbzndsFWbe1g8AA5MJlWeSpI1pTZYQSUjJk5dCjHEBvmiq39IWnIojT5Yh74Zdyd03xMLWaLq3PztiwnwY1ePCTDAH2EAFfz2jEOiInFNkAunlX6r"


@Serializable
data class Notification(
    val title: String,
    val body: String,
)

@Serializable
data class Message(
    val to: String,
    val notification: Notification,
)

suspend fun sendMessage(username: String, title: String, description: String) {
    val localClient = httpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    val message = Message(
        to = "/topics/$username",
        notification = Notification(
            title = title,
            body = description
        )
    )

    localClient.post("https://fcm.googleapis.com/fcm/send") {
        contentType(ContentType.Application.Json)
        header("Authorization", "key=$FCM_KEY")
        setBody(message)
    }
}