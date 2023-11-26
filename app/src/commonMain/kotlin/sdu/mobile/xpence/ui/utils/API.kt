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

@Serializable
data class NewGroup(
    val id: Int,
    val name: String,
    val description: String,
    @SerialName("currency_code") val currencyCode: String
)

@Serializable
data class Member(
    @SerialName("group_id") val groupId: Int,
    val username: String,
    @SerialName("is_owner") val isOwner: Boolean,
)


@Serializable
data class User(
    val username: String,
    @SerialName("full_name") val fullName: String
)

// API CALLS
suspend fun getGroups(client: HttpClient): Array<Group> {
    return client.get("https://xpense-api.gredal.dev/groups").body<Array<Group>>()
}

suspend fun getUsers(client: HttpClient): Array<User> {
    return client.get("https://xpense-api.gredal.dev/users").body<Array<User>>()
}

suspend fun getCurrentUser(client: HttpClient): User {
    return client.get("https://xpense-api.gredal.dev/current_user").body<User>()
}

suspend fun createGroup(client: HttpClient, name: String, description: String, currencyCode: String): NewGroup {
    return client.post("https://xpense-api.gredal.dev/groups") {
        parameter("name", name)
        parameter("description", description)
        parameter("currency_code", currencyCode)
    }.body<NewGroup>()
}

suspend fun addGroupMember(client: HttpClient, groupId: Int, username: String, isOwner: Boolean): Array<Member> {
    return client.post("https://xpense-api.gredal.dev/groups/$groupId/members") {
        parameter("username", username)
        parameter("is_owner", isOwner)
    }.body<Array<Member>>()
}