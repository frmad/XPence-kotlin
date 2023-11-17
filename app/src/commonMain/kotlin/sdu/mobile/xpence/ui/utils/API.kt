package sdu.mobile.xpence.ui.utils

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// TODO: Autogenerate these from the openapi schema

// MODELS
@Serializable
data class TokenInfo(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String,
)

@Serializable
data class Group(val id: Int, val name: String)
data class User( val username: String )

// API CALLS


//Groups
suspend fun getGroups(client: HttpClient): Array<Group> {
    return client.get("https://xpense-api.gredal.dev/groups").body<Array<Group>>()
}
suspend fun getGroup(client: HttpClient, id: Int): Array<Group> {
    return client.get("https://xpense-api.gredal.dev/groups/$id").body<Array<Group>>()
}

suspend fun getGroupMembers(client: HttpClient, id: Int): Array<User> {
    return client.get("https://xpense-api.gredal.dev/groups/$id/members").body<Array<User>>()
}
suspend fun createGroup(client: HttpClient, group: Json):HttpStatusCode {
        return client.post("https://xpense-api.gredal.dev/groups") {
            contentType(ContentType.Application.Json)
            setBody(group)
        }.status
    }
suspend fun addGroupMember(client: HttpClient, id:Int, member: Json):HttpStatusCode {
    return client.post("https://xpense-api.gredal.dev/groups/$id/members") {
        contentType(ContentType.Application.Json)
        setBody(member)
    }.status
}
suspend fun deleteGroupMember(client: HttpClient, id: Int, username: String): Array<User> {
    return client.get("https://xpense-api.gredal.dev/groups/$id/members/$username").body<Array<User>>()
}
suspend fun getGroupBalance(client: HttpClient, id: Int): Array<User> {
    return client.get("https://xpense-api.gredal.dev/groups/$id/balance").body<Array<User>>()
}
