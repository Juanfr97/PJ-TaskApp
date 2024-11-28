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
import com.example.taskapp.presentation.states.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val saveSharedPref: SharedPref
):ViewModel() {
    private var loginJob : Job? = null

    private val _loginState = mutableStateOf(LoginState())
    val loginState : State<LoginState> = _loginState

    init {
        val userId = saveSharedPref.getUserIdSharedPref()
        val isLogged = saveSharedPref.getIsLoggedSharedPref()
        _loginState.value = LoginState(userId = userId, isLogged = isLogged)
    }

    fun onEvent(event : AuthEvent){
        when(event){
            is AuthEvent.onLogin -> {
                viewModelScope.launch {
                    login(event.login)
                }
            }
            AuthEvent.onLogout -> {
                saveSharedPref.removeUserSharedPref()
                _loginState.value = LoginState()
            }
            is AuthEvent.onRegister -> TODO()
        }
    }

    private suspend fun login(login: Auth){
        loginJob?.cancel()
        loginJob = authRepository.login(login).onEach { result->
            when (result) {
                is ApiResponse.Loading -> {
                    _loginState.value = LoginState(isLoading = true)
                }
                is ApiResponse.Success -> {
                    _loginState.value = LoginState(userId = result.data?.userId!!, isLogged = result.data.isLogged, isLoading = false)
                    saveSharedPref.saveUserSharedPref(result.data.userId, result.data.isLogged)
                }
                is ApiResponse.Error -> {
                    _loginState.value = LoginState(message = result.message ?: "Ocurrió un error", isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }


}