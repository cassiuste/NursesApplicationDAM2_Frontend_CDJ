package com.example.frontendnursesapplication.repository

import com.example.frontendnursesapplication.entities.Nurse
import com.example.frontendnursesapplication.network.NurseApiService

class NurseRepository(private val apiService: NurseApiService) {
    suspend fun getNurses(): List<Nurse> {
        return apiService.getNurses()
    }

    suspend fun login(nurse: Nurse): Nurse{
        return apiService.login(nurse)
    }
}