package sdu.mobile.xpence

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