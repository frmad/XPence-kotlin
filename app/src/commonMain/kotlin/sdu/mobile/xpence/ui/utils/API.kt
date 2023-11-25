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
    @SerialName("currency_code") val currencyCode: String = "DKK")


@Serializable
data class User(@SerialName("full_name") val fullName: String)

// API CALLS
suspend fun getGroups(client: HttpClient): Array<Group> {
    return client.get("https://xpense-api.gredal.dev/groups").body<Array<Group>>()
}

suspend fun getUsers(client: HttpClient): Array<User> {
    return client.get("https://xpense-api.gredal.dev/users").body<Array<User>>()
}



suspend fun createGroup(client: HttpClient, group: NewGroup): Group {
    return client.post("https://xpense-api.gredal.dev/groups") {
        contentType(ContentType.Application.Json)
        setBody(group)
    }.body<Group>()
}

suspend fun addGroupMember(client: HttpClient, id: Int, member: User): HttpStatusCode {
    return client.post("https://xpense-api.gredal.dev/groups/$id/members") {
        contentType(ContentType.Application.Json)
        setBody(member)
    }.status
}
