package com.fraustosoft.taskapp.domain.repositories

import com.fraustosoft.taskapp.domain.dtos.TaskDTO
import com.fraustosoft.taskapp.domain.dtos.TaskResponse
import com.fraustosoft.taskapp.domain.models.Task

interface TaskRepository {
    suspend fun getTasks(userId:Int) : TaskResponse

    suspend fun updateTask(task: TaskDTO, id:Int): Task

    suspend fun createTask(task: TaskDTO): Task

    suspend fun deleteTask(id:Int)
}