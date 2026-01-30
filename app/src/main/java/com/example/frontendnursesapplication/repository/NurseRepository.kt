package com.example.frontendnursesapplication.repository

import com.example.frontendnursesapplication.entities.Nurse
import com.example.frontendnursesapplication.network.NurseApiService
import retrofit2.Response

class NurseRepository(private val apiService: NurseApiService) {
    suspend fun getAll(): List<Nurse> {
        return apiService.getAll()
    }
    suspend fun login(nurse: Nurse): Nurse{
        return apiService.login(nurse)
    }

    suspend fun registerNurse(nurse: Nurse): Response<Nurse> {
        return apiService.registerNurse(nurse)
    }
    suspend fun findbyname(name: String): Response<Nurse> {
        return apiService.findNurseByName(name)
    }






}