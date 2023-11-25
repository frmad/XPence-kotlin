package sdu.mobile.xpence.ui.utils

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO: Autogenerate these from the openapi schema

// MODELS
@Serializable
data class TokenInfo(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String,)

@Serializable
data class Group(val id: Int, val name: String)

@Serializable
data class NewGroup(
    val name: String,
    val description: String,
    @SerialName("currency_code") val currencyCode: String)

@Serializable
data class Member(
    @SerialName("group_id") val groupId: Int,
    val username: String,
    @SerialName("is_owner") val isOwner: Boolean, )


@Serializable
data class User(@SerialName("full_name") val fullName: String)

// API CALLS
suspend fun getGroups(client: HttpClient): Array<Group> {
    return client.get("https://xpense-api.gredal.dev/groups").body<Array<Group>>()
}

suspend fun getUsers(client: HttpClient): Array<User> {
    return client.get("https://xpense-api.gredal.dev/users").body<Array<User>>()
}

suspend fun createGroup(client: HttpClient, name: String, description: String, currencyCode: String): Group {
    return client.post("https://xpense-api.gredal.dev/groups") {
        contentType(ContentType.Application.Json)

        parameter("name",name)
        parameter("description",description)
        parameter("currency_code",currencyCode)

    }.body<Group>()
}


suspend fun addGroupMember(client: HttpClient, groupId: Int, username: String, isOwner: Boolean): Member {
    return client.post("https://xpense-api.gredal.dev/groups/$groupId/members") {
        contentType(ContentType.Application.Json)
        parameter("group_id",groupId)
        parameter("username",username)
        parameter("is_owner",isOwner)
    }.body<Member>()
}
