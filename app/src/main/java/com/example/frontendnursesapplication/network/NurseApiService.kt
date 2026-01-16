package com.example.frontendnursesapplication.network

import com.example.frontendnursesapplication.entities.Nurse
import retrofit2.http.GET

interface NurseApiService {
    @GET("nurse/index")
    suspend fun getNurses(): List<Nurse>
}