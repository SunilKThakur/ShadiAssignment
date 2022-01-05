package com.shadiassignment.network

import com.shadiassignment.models.ShadiMatchResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ShadiApi {
    @GET("/api/")
    suspend fun getRates(
        @Query("results") result:Int
    ):Response<ShadiMatchResponseModel>
}