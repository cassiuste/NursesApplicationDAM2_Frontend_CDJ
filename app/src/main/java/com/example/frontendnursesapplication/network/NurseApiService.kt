package com.example.frontendnursesapplication.network

import com.example.frontendnursesapplication.entities.Nurse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface NurseApiService {
    @GET("nurse/index")
    suspend fun getAll(): List<Nurse>

    @Headers("Accept: application/json","Content-Type: application/json")
    @POST("nurse/login")
    suspend fun login(@Body nurse: Nurse): Nurse

    @GET("nurse/{id}")
    suspend fun getNurse(@Path("id") id: Long): Response<Nurse>

    @PUT("nurse/{id}")
    suspend fun updateNurse(@Path("id") id: Long, @Body nurse: Nurse): Response<Void>

    @DELETE("nurse/{id}")
    suspend fun deleteNurse(@Path("id") id: Long): Response<Void>

    @POST("nurse")
    suspend fun registerNurse(
        @Body nurse: Nurse
    ): Response<Nurse>

    @GET("nurse/name")
    suspend fun findNurseByName(
        @Query("name") name: String
    ): Response<Nurse>


}