package com.fraustosoft.taskapp.presentation.events

import com.fraustosoft.taskapp.domain.dtos.Auth

sealed class AuthEvent{
    data class onLogin(val login: Auth): AuthEvent()
    data object onLogout : AuthEvent()
    data class onRegister(val register: Auth): AuthEvent()
}