package sdu.mobile.xpence

import io.ktor.client.*

expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient

expect fun Double.formatDecimal(maxFractionDigits: Int = 2): String

expect fun isIphoneWeb(): Boolean

expect fun getUnixTime(): Long

expect fun setNotificationTopic(topic: String)