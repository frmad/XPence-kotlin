package sdu.mobile.xpence.ui.utils

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO: Autogenerate these from the openapi schema

// MODELS
@Serializable
data class TokenInfo(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String,
)

@Serializable
data class Group(val id: Int, val name: String)

// API CALLS
suspend fun getGroups(client: HttpClient): Result<Array<Group>> {
    return Result.Success(client.get("https://xpense-api.gredal.dev/groups").body<Array<Group>>())
}