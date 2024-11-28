package com.example.taskapp.presentation.states

data class LoginState(
    val isLoading: Boolean = false,
    val message: String = "",
    val userId : Int = 0,
    val isLogged : Boolean = false
)