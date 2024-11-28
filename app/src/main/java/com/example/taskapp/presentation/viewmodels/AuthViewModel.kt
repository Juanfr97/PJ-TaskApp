package com.example.taskapp.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskapp.domain.dtos.ApiResponse
import com.example.taskapp.domain.dtos.Auth
import com.example.taskapp.domain.repositories.AuthRepository
import com.example.taskapp.domain.use_cases.SharedPref
import com.example.taskapp.presentation.events.AuthEvent
import com.example.taskapp.presentation.states.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val saveSharedPref: SharedPref
) : ViewModel() {

    private val _loginState = mutableStateOf(AuthState())
    val loginState: State<AuthState> = _loginState

    private val _snackbarMessage = mutableStateOf<String?>(null)
    val snackbarMessage: State<String?> = _snackbarMessage

    init {
        val userId = saveSharedPref.getUserIdSharedPref()
        val isLogged = saveSharedPref.getIsLoggedSharedPref()
        _loginState.value = AuthState(userId = userId, isLogged = isLogged)
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.onLogin ->{
                if (event.login.email.isBlank() || event.login.password.isBlank()) {
                    _snackbarMessage.value = "Correo o contraseña no pueden estar vacíos"
                    return
                }
                handleAuthFlow(
                    action = { authRepository.login(event.login) },
                    onSuccess = { response ->
                        saveSharedPref.saveUserSharedPref(response.userId, response.isLogged)
                        _loginState.value = AuthState(userId = response.userId, isLogged = response.isLogged)
                    },
                    onError = { message -> _snackbarMessage.value = message }
                )
            }
            AuthEvent.onLogout -> {
                saveSharedPref.removeUserSharedPref()
                _loginState.value = AuthState()
            }
            is AuthEvent.onRegister ->{
                if (event.register.email.isBlank() || event.register.password.isBlank()) {
                    _snackbarMessage.value = "Correo o contraseña no pueden estar vacíos"
                    return
                }
                handleAuthFlow(
                    action = { authRepository.register(event.register) },
                    onSuccess = { response ->
                        saveSharedPref.saveUserSharedPref(response.userId, response.isLogged)
                        _loginState.value = AuthState(userId = response.userId, isLogged = response.isLogged)
                    },
                    onError = { message -> _snackbarMessage.value = message }
                )
            }
        }
    }

    private fun <T> handleAuthFlow(
        action: suspend () -> Flow<ApiResponse<T>>,
        onSuccess: (T) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            action().collect { result ->
                when (result) {
                    is ApiResponse.Loading -> _loginState.value = AuthState(isLoading = true)
                    is ApiResponse.Success -> {
                        _loginState.value = AuthState(isLoading = false)
                        result.data?.let(onSuccess)
                    }
                    is ApiResponse.Error -> {
                        _loginState.value = AuthState(isLoading = false)
                        onError(result.message ?: "Ocurrió un error")
                    }
                }
            }
        }
    }

    fun clearSnackbar() {
        _snackbarMessage.value = null
    }
}