package com.fraustosoft.taskapp.domain.dtos

import com.fraustosoft.taskapp.domain.models.Task

data class TaskResponse(
    val tasks : List<Task>,
    val completedTasks : Int,
    val pendingTasks : Int,
    val expiredTasks : Int
)
