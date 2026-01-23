package com.example.frontendnursesapplication.network

import com.example.frontendnursesapplication.entities.Nurse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NurseApiService {
    @GET("nurse/index")
    suspend fun getAll(): List<Nurse>

    @Headers("Accept: application/json","Content-Type: application/json")
    @POST("nurse/login")
    suspend fun login(@Body nurse: Nurse): Nurse
    @PUT("nurse/{id}")
    suspend fun updateNurse(
        @Path("id") id: Long,
        @Body nurse: Nurse
    ): Response<Unit>

    @POST("nurse")
    suspend fun registerNurse(
        @Body nurse: Nurse
    ): Response<Nurse>

}