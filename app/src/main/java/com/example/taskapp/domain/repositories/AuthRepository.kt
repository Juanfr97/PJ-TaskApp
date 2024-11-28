package com.example.taskapp.domain.repositories

import com.example.taskapp.domain.dtos.ApiResponse
import com.example.taskapp.domain.dtos.Auth
import com.example.taskapp.domain.dtos.AuthResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(login: Auth) : Flow<ApiResponse<AuthResponse>>

}