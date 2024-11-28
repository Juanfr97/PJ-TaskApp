package com.example.taskapp.presentation.events

import com.example.taskapp.domain.dtos.Auth

sealed class AuthEvent{
    data class onLogin(val login: Auth): AuthEvent()
    data object onLogout : AuthEvent()
    data class onRegister(val register: Auth): AuthEvent()
}