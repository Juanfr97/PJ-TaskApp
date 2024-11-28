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

    companion object {
        private const val TAG = "AuthRepository"
        private const val ERROR_LOGIN = "Error al iniciar sesión"
        private const val ERROR_REGISTER = "Error al registrarse"
        private const val ERROR_GENERIC = "Ocurrió un error"
        private const val ERROR_INVALID_CREDENTIALS = "Usuario o contraseña incorrectos"
    }

    override suspend fun login(login: Auth): Flow<ApiResponse<AuthResponse>> =
        performRequest(
            request = { authService.login(login) },
            errorMessage = ERROR_LOGIN
        )

    override suspend fun register(register: Auth): Flow<ApiResponse<AuthResponse>> =
        performRequest(
            request = { authService.createUser(register) },
            errorMessage = ERROR_REGISTER
        )

    private suspend fun performRequest(
        request: suspend () -> retrofit2.Response<AuthResponse>,
        errorMessage: String
    ): Flow<ApiResponse<AuthResponse>> = flow {
        try {
            emit(ApiResponse.Loading())
            val response = request()

            when {
                response.isSuccessful -> {
                    response.body()?.let {
                        emit(ApiResponse.Success(it))
                    } ?: emit(ApiResponse.Error(ERROR_GENERIC))
                }
                response.code() == 404 -> emit(ApiResponse.Error(ERROR_INVALID_CREDENTIALS))
                else -> emit(ApiResponse.Error(ERROR_GENERIC))
            }
        } catch (e: Exception) {
            Log.e(TAG, "$errorMessage: ${e.message}", e)
            emit(ApiResponse.Error(errorMessage))
        }
    }
}