package sdu.mobile.xpence.api

data class Group(
    val name: String,
    val description: String
)

data class CreateGroupRequest(
    val name: String,
    val description: String
)