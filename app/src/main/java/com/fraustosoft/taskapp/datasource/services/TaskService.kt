package com.fraustosoft.taskapp.datasource.services

import com.fraustosoft.taskapp.domain.dtos.TaskDTO
import com.fraustosoft.taskapp.domain.dtos.TaskResponse
import com.fraustosoft.taskapp.domain.models.Task
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TaskService {
    @GET("tasks")
    suspend fun getTasks(@Query("userId") userId : Int) : Response<TaskResponse>
    @GET("tasks/{id}")
    suspend fun getTaskById(@Path("id") id : Int) : Response<Task>

    @POST("task")
    suspend fun createTask(@Body task : TaskDTO) : Response<Task>

    @DELETE("task/{id}")
    suspend fun deleteTask(@Path("id") id : Int)

    @PUT("task/{id}")
    suspend fun updateTask(@Path("id") id : Int, @Body task : TaskDTO) : Response<Task>
}