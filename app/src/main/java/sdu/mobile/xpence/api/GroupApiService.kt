package sdu.mobile.xpence.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GroupApiService {
    @GET("/groups")
    fun getGroups(): Call<List<Group>>

    @POST("/groups")
    fun createGroup(@Body createGroupRequest: CreateGroupRequest): Call<Group>
}