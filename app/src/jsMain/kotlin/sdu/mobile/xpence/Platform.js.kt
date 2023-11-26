package sdu.mobile.xpence

import io.ktor.client.*
import io.ktor.client.engine.js.*
import kotlinx.browser.window

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(Js, config)
actual fun Double.formatDecimal(maxFractionDigits: Int): String = this.asDynamic().toFixed(maxFractionDigits) as String

actual fun isIphoneWeb(): Boolean {
    val ua = window.navigator.userAgent
    return ua.contains("iPhone") || ua.contains("iPad")
}