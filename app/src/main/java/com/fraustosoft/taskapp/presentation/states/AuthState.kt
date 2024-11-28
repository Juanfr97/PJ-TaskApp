package com.fraustosoft.taskapp.presentation.states

data class AuthState(
    val isLoading: Boolean = false,
    val message: String = "",
    val userId : Int = 0,
    val isLogged : Boolean = false
)