package sdu.mobile.xpence

import io.ktor.client.*

expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient

expect fun Double.formatDecimal(maxFractionDigits: Int = 2): String