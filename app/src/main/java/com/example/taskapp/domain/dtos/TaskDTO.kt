package com.example.taskapp.domain.dtos

data class TaskDTO(
    val description: String,
    val dueDate: String,
    val isDone: Boolean,
    val title: String,
    val userId: Int
)
