package sdu.mobile.xpence.ui.utils

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.abs

// TODO: Autogenerate these from the openapi schema

// MODELS
@Serializable
data class TokenInfo(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String
)

@Serializable
data class Group(
    val id: Int,
    val name: String,
    val description: String = "",
    val currency_code: String = "DKK"
)

@Serializable
data class User(
    val username: String,
    val email: String,
    @SerialName("full_name")
    val fullName: String,
)

@Serializable
data class Balance(
    @SerialName("balance_amount_cents") val balanceAmountCents: Int
)

@Serializable
data class GroupMember(
    val username: String,
    @SerialName("is_owner") val isOwner: Boolean,
    @SerialName("balance_amount_cents") val balanceAmountCents: Int,
)

@Serializable
data class Expenses(
    val id: Int,
    val name: String,
    val description: String,
    @SerialName("amount_in_cents")
    val amountInCents: Int,
    @SerialName("group_id")
    val groupId: Int,
    val date: String,
    @SerialName("payer_username")
    val payerUsername: String
)

@Serializable
data class PostExpenseMember(
    val username: String,
    @SerialName("amount_in_cents")
    val amountInCents: Int,
)

@Serializable
data class PostExpense(
    val name: String,
    val description: String,
    @SerialName("amount_in_cents")
    val amountInCents: Int,
    @SerialName("payer_username")
    val payerUsername: String,
    val members: List<PostExpenseMember>
)

data class Transaction(
    val id: Int,
    val groupID: Int,
    val username: String,
    val amount_in_cents: Int,
    val date: String
)

enum class TransactionType {
    DEPOSIT,
    WITHDRAWAL
}

@Serializable
data class Member(
    @SerialName("group_id") val groupId: Int,
    val username: String,
    @SerialName("is_owner") val isOwner: Boolean,
)
@Serializable
data class NewGroup(
    val id: Int,
    val name: String,
    val description: String,
    @SerialName("currency_code") val currencyCode: String
)

// API CALLS

//Users
suspend fun getUsers(client: HttpClient): Array<User> {
    return client.get("https://xpense-api.gredal.dev/users").body<Array<User>>()
}

suspend fun getCurrentUser(client: HttpClient): User {
    return client.get("https://xpense-api.gredal.dev/current_user").body<User>()
}

//Groups
suspend fun getGroups(client: HttpClient): Array<Group> {
    return client.get("https://xpense-api.gredal.dev/groups").body<Array<Group>>()
}

suspend fun getGroup(client: HttpClient, id: Int): Group {
    return client.get("https://xpense-api.gredal.dev/groups/$id").body<Group>()
}


suspend fun getGroupMembers(client: HttpClient, id: Int): Array<GroupMember> {
    return client.get("https://xpense-api.gredal.dev/groups/$id/members").body<Array<GroupMember>>()
}

suspend fun createGroup(
    client: HttpClient,
    name: String,
    description: String,
    currencyCode: String
): NewGroup {
    return client.post("https://xpense-api.gredal.dev/groups") {
        parameter("name", name)
        parameter("description", description)
        parameter("currency_code", currencyCode)

    }.body<NewGroup>()
}

suspend fun addGroupMember(
    client: HttpClient,
    groupId: Int,
    username: String,
    isOwner: Boolean
): Array<Member> {
    return client.post("https://xpense-api.gredal.dev/groups/$groupId/members") {
        contentType(ContentType.Application.Json)
        setBody(Member(groupId, username, isOwner))
        parameter("username", username)
        parameter("is_owner", isOwner)
    }.body<Array<Member>>()
}

suspend fun deleteGroupMember(client: HttpClient, id: Int, username: String): Array<User> {
    return client.get("https://xpense-api.gredal.dev/groups/$id/members/$username")
        .body<Array<User>>()
}

suspend fun getGroupBalance(client: HttpClient, id: Int): Balance {
    return client.get("https://xpense-api.gredal.dev/groups/$id/balance").body<Balance>()
}

// Expenses
suspend fun getExpenses(client: HttpClient, groupID: Int): Array<Expenses> {
    return client.get("https://xpense-api.gredal.dev/groups/$groupID/expenses")
        .body<Array<Expenses>>()
}

suspend fun createExpense(client: HttpClient, groupID: Int, expense: PostExpense): HttpStatusCode {
    return client.post("https://xpense-api.gredal.dev/groups/$groupID/expenses") {
        contentType(ContentType.Application.Json)
        setBody(expense)
    }.status
}

suspend fun getExpense(client: HttpClient, groupID: Int, expensesID: Int): Array<Expenses> {
    return client.get("https://xpense-api.gredal.dev/groups/$groupID/expenses/$expensesID")
        .body<Array<Expenses>>()
}

//Transactions
suspend fun getTransactions(client: HttpClient, groupID: Int): Array<Transaction> {
    return client.get("https://xpense-api.gredal.dev/groups/$groupID/transactions")
        .body<Array<Transaction>>()
}

suspend fun createTransaction(
    client: HttpClient,
    groupID: Int,
    amountInCents: Int,
    type: TransactionType
) {
    val amount = when (type) {
        TransactionType.DEPOSIT -> abs(amountInCents)
        TransactionType.WITHDRAWAL -> abs(amountInCents) * -1
    }

    client.post("https://xpense-api.gredal.dev/groups/$groupID/transactions") {
        contentType(ContentType.Application.Json)
        parameter("amount_in_cents", amount)
    }
}
suspend fun editUser(email: String, name: String) {
    val localClient = getHttpClient(authenticationState)

    val username = localClient?.let { getCurrentUser(it).username }

    localClient?.put("https://xpense-api.gredal.dev/users/$username") {
        setBody(
            FormDataContent(
                Parameters.build {
                    if (username != null) {
                        append("username", username)
                    }
                    append("email", email)
                    append("full_name", name)
                    append("profile_image", "admin.png")
                }
            )
        )
    }
}