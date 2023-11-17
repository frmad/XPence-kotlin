package sdu.mobile.xpence.ui.utils

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
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
data class Group(val id: Int, val name: String, val description: String = "", val currency_code: String = "DKK")
data class User(val username: String)
data class Balance(val centes: Int)
data class Expenses(
    val id: Int,
    val name: String,
    val description: String,
    val amount_in_cents: Int,
    val groupID: Int,
    val date: String
)

data class Transactions(val id: Int, val groupID: Int, val username: String, val amount_in_cents: Int, val date: String)
// API CALLS

//Users

suspend fun getCurrentUser(client: HttpClient): Array<User> {
    return client.get("https://xpense-api.gredal.dev/users").body<Array<User>>()
}

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

suspend fun createGroup(client: HttpClient, group: Group): HttpStatusCode {
    return client.post("https://xpense-api.gredal.dev/groups") {
        contentType(ContentType.Application.Json)
        setBody(group)
    }.status
}

suspend fun addGroupMember(client: HttpClient, id: Int, member: User): HttpStatusCode {
    return client.post("https://xpense-api.gredal.dev/groups/$id/members") {
        contentType(ContentType.Application.Json)
        setBody(member)
    }.status
}

suspend fun deleteGroupMember(client: HttpClient, id: Int, username: String): Array<User> {
    return client.get("https://xpense-api.gredal.dev/groups/$id/members/$username").body<Array<User>>()
}

suspend fun getGroupBalance(client: HttpClient, id: Int): Array<Balance> {
    return client.get("https://xpense-api.gredal.dev/groups/$id/balance").body<Array<Balance>>()
}

// Expenses

suspend fun getExpenses(client: HttpClient, groupID: Int): Array<Expenses> {
    return client.get("https://xpense-api.gredal.dev/groups/$groupID/expenses").body<Array<Expenses>>()
}

suspend fun createExpense(client: HttpClient, groupID: Int, expenses: Expenses): HttpStatusCode {
    return client.post("https://xpense-api.gredal.dev/groups/$groupID/expenses") {
        contentType(ContentType.Application.Json)
        setBody(expenses)
    }.status
}

suspend fun getExpense(client: HttpClient, groupID: Int, expensesID: Int): Array<Expenses> {
    return client.get("https://xpense-api.gredal.dev/groups/$groupID/expenses/$expensesID").body<Array<Expenses>>()
}

//Transactions
suspend fun getTransactions(client: HttpClient, groupID: Int): Array<Transactions> {
    return client.get("https://xpense-api.gredal.dev/groups/$groupID/transactions").body<Array<Transactions>>()
}

suspend fun createExpense(client: HttpClient, groupID: Int, transactions: Transactions): HttpStatusCode {
    return client.post("https://xpense-api.gredal.dev/groups/$groupID/transactions") {
        contentType(ContentType.Application.Json)
        setBody(transactions)
    }.status
}
