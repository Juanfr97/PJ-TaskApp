package com.example.taskapp.datasource.repositories

import android.util.Log
import com.example.taskapp.datasource.services.AuthService
import com.example.taskapp.domain.dtos.ApiResponse
import com.example.taskapp.domain.dtos.Auth
import com.example.taskapp.domain.dtos.AuthResponse
import com.example.taskapp.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun login(login: Auth): Flow<ApiResponse<AuthResponse>> =flow {
        try {
            emit(ApiResponse.Loading())
            val loginResponse = authService.login(login)

            if(loginResponse.isSuccessful){
                emit(ApiResponse.Success(loginResponse.body()!!))
            }
            else if(loginResponse.code() == 404){
                emit(ApiResponse.Error("Usuario o contraseña incorrectos"))
            }
            else{
                emit(ApiResponse.Error("Ocurrió un error"))
            }
        } catch (e: Exception) {
            Log.i("AuthRepository", "invoke: ${e.message}")
            emit(ApiResponse.Error("Error al iniciar sesion"))
        }
    }
}