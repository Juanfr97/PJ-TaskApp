package com.fraustosoft.taskapp.domain.repositories

import com.fraustosoft.taskapp.domain.dtos.ApiResponse
import com.fraustosoft.taskapp.domain.dtos.Auth
import com.fraustosoft.taskapp.domain.dtos.AuthResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(login: Auth) : Flow<ApiResponse<AuthResponse>>
    suspend fun register(register:Auth) : Flow<ApiResponse<AuthResponse>>
}