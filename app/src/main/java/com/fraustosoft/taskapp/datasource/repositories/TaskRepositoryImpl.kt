package com.fraustosoft.taskapp.datasource.repositories

import com.fraustosoft.taskapp.datasource.services.TaskService
import com.fraustosoft.taskapp.domain.dtos.TaskDTO
import com.fraustosoft.taskapp.domain.dtos.TaskResponse
import com.fraustosoft.taskapp.domain.models.Task
import com.fraustosoft.taskapp.domain.repositories.TaskRepository

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