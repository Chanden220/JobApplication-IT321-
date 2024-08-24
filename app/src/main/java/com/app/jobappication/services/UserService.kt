package com.app.jobappication.services

import com.app.jobappication.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {
    @GET("api/")
    fun getUser(@Query("id") id: Int): Call<ApiResponse>
}