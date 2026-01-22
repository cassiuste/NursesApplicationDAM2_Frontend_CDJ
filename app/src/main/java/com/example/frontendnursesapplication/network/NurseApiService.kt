package com.example.frontendnursesapplication.network

import com.example.frontendnursesapplication.entities.Nurse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface NurseApiService {
    @GET("nurse/index")
    suspend fun getNurses(): List<Nurse>

    @Headers("Accept: application/json","Content-Type: application/json")
    @POST("nurse/login")
    suspend fun login(@Body nurse: Nurse): Nurse
}