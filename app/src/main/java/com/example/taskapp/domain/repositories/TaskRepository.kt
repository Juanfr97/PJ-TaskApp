package com.example.taskapp.domain.repositories

import com.example.taskapp.domain.dtos.TaskDTO
import com.example.taskapp.domain.dtos.TaskResponse
import com.example.taskapp.domain.models.Task

interface TaskRepository {
    suspend fun getTasks(userId:Int) : TaskResponse

    suspend fun updateTask(task: TaskDTO, id:Int): Task

    suspend fun createTask(task: TaskDTO): Task

    suspend fun deleteTask(id:Int)
}