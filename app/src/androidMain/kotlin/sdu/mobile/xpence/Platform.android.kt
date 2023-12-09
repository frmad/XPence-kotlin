package sdu.mobile.xpence

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import java.text.DecimalFormat

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp, config)
actual fun Double.formatDecimal(maxFractionDigits: Int): String = DecimalFormat().apply {
    isGroupingUsed = false
    minimumFractionDigits = 0
    maximumFractionDigits = maxFractionDigits
    isDecimalSeparatorAlwaysShown = false
}.format(this)

actual fun isIphoneWeb(): Boolean {
    return false
}

actual fun getUnixTime(): Long {
    return System.currentTimeMillis()
}

private val settings = Settings()

actual fun setNotificationTopic(topic: String) {
    val oldTopic: String? = settings["notificationTopic"]
    if (oldTopic != null) {
        Log.d("Notifications", "Unsubscribing from topic: $oldTopic")
        FirebaseMessaging.getInstance().unsubscribeFromTopic(oldTopic)
    }

    Log.d("Notifications", "Subscribing to topic: $topic")
    FirebaseMessaging.getInstance().subscribeToTopic(topic)
    settings["notificationTopic"] = topic
}