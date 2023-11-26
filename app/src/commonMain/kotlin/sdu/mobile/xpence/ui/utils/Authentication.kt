package sdu.mobile.xpence.ui.utils

import androidx.compose.runtime.*
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import sdu.mobile.xpence.httpClient

/**
 * Authentication data is persisted through the native storage of the platform, using a 3rd-party package
 */
private val settings = Settings()

/**
 * AuthenticationData is responsible for storing all session related data. If the session token is null,
 * then that means the session is invalid and the authentication data should be regenerated with a
 * new login request.
 *
 * TODO: Override the getter and parse the session token, and check if it has expired.
 */
class AuthenticationData(val sessionToken: String? = settings["sessionToken"]) {
    init {
        settings["sessionToken"] = sessionToken
    }

    fun isLoggedIn(): Boolean {
        return sessionToken != null
    }
}

/**
 * This stores a global state for the authentication data. The navigator should be setup to get recomposed when this
 * changes.
 */
var authenticationState: AuthenticationData by mutableStateOf(AuthenticationData(), structuralEqualityPolicy())

/**
 * This function is used to generate new authentication data,
 * by requesting an access token using the provided credentials.
 * @return If the login failed, the session token inside the returned object with be null.
 */
suspend fun login(username: String, password: String): AuthenticationData {
    val localClient = httpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    val response = localClient.submitForm(url = "https://xpense-api.gredal.dev/token",
        formParameters = parameters {
            append("grant_type", "password")
            append("username", username)
            append("password", password)
        }
    )

    if (response.status.isSuccess()) {
        val tokens: TokenInfo = response.body()
        return AuthenticationData(tokens.accessToken)
    }

    return AuthenticationData()
}

/**
 * This function should be called to perform logout
 */
fun logout(): AuthenticationData {
    return AuthenticationData(null)
}

/**
 * This function makes a new HttpClient from the provided authentication data.
 *
 * @return If the authenticationData session token is invalid, then null is returned.
 */
fun getHttpClient(authenticationData: AuthenticationData?): HttpClient? {
    authenticationData ?: return null
    val sessionToken = authenticationData.sessionToken ?: return null

    return httpClient {
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(
                        sessionToken,
                        ""
                    )
                }
            }
        }

        install(HttpCache)

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
}

/**
 * This is a high level wrapper for authenticated access to APIs, that also handles loading and error states
 */
@Composable
fun <T> usingAPI(query: suspend CoroutineScope.(HttpClient) -> T): State<QueryState<T>> {
    return produceState<QueryState<T>>(initialValue = QueryState.Loading) {
        value = withContext(Dispatchers.Default) {
            val client = getHttpClient(authenticationState)
            if (client == null) {
                QueryState.Error("Not signed in")
            } else {
                try {
                    QueryState.Success(query(client))
                } catch (e: Exception) {
                    QueryState.Error("Exception: " + e.message.toString())
                }
            }
        }
    }
}


suspend fun createUser(client: HttpClient, email: String, name: String, username: String, password: String): AuthenticationData {
    val localClient = httpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    val response = localClient.submitForm(url = "https://xpense-api.gredal.dev/signup",
        formParameters = parameters {
            append("grant_type", "password")
            append("username", username)
            append("email", email)
            append("name", name)
            append("password", password)
        }
    )

    if (response.status.isSuccess()) {
        val tokens: TokenInfo = response.body()
        return AuthenticationData(tokens.accessToken)
    }

    return AuthenticationData()
}

/**
 * This interface represents the loading, error and success states
 */
sealed interface QueryState<out T> {
    data object Loading : QueryState<Nothing>
    data class Error(val message: String) : QueryState<Nothing>
    data class Success<T>(val data: T) : QueryState<T>
}