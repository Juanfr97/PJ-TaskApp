package com.example.taskapp.datasource.repositories

import com.example.taskapp.datasource.services.TaskService
import com.example.taskapp.domain.dtos.TaskDTO
import com.example.taskapp.domain.dtos.TaskResponse
import com.example.taskapp.domain.models.Task
import com.example.taskapp.domain.repositories.TaskRepository

class TaskRepositoryImpl(taskService: TaskService) : TaskRepository {
    override suspend fun getTasks(userId: Int): TaskResponse {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: TaskDTO, id: Int): Task {
        TODO("Not yet implemented")
    }

    override suspend fun createTask(task: TaskDTO): Task {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(id: Int) {
        TODO("Not yet implemented")
    }
}