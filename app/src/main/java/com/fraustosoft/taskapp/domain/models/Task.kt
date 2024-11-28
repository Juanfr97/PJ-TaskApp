package com.fraustosoft.taskapp.domain.models

import com.fraustosoft.taskapp.domain.dtos.TaskDTO
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Task(
    val creationDate: String,
    val description: String,
    val dueDate: String,
    val id: Int,
    var isDone: Boolean,
    val title: String,
    val userId: Int
){
    val computedStatus: String
        get() {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val today = Date()
            val dueDateParsed = formatter.parse(dueDate)

            return when {
                isDone -> "Terminada"
                dueDateParsed != null && today.after(dueDateParsed) -> "Vencida"
                else -> "Pendiente"
            }
        }
    fun toDTO() : TaskDTO{
        return TaskDTO(
            description = description,
            dueDate = dueDate,
            isDone = isDone,
            title = title,
            userId = userId
        )
    }
}