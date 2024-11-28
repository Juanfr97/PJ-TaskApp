package com.fraustosoft.taskapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fraustosoft.taskapp.datasource.services.TaskService
import com.fraustosoft.taskapp.domain.dtos.TaskDTO
import com.fraustosoft.taskapp.domain.models.Task
import com.fraustosoft.taskapp.domain.use_cases.SharedPref
import com.fraustosoft.taskapp.presentation.components.TaskBox
import com.fraustosoft.taskapp.presentation.components.TaskItem
import com.fraustosoft.taskapp.presentation.ui.theme.TaskAppTheme
import com.fraustosoft.taskapp.presentation.ui.theme.yellow
import com.fraustosoft.taskapp.presentation.utils.Description
import com.fraustosoft.taskapp.presentation.utils.Logout
import com.fraustosoft.taskapp.presentation.utils.Title
import com.fraustosoft.taskapp.utils.Screens
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController) {
    val sharedPref = SharedPref(LocalContext.current)
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    var tasks by remember {
        mutableStateOf(emptyList<Task>())
    }
    var completedTasks by remember {
        mutableStateOf(0)
    }
    var pendingTasks by remember {
        mutableStateOf(0)
    }
    var expiredTasks by remember {
        mutableStateOf(0)
    }
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var isLoading by remember {
        mutableStateOf(true)
    }
    val datePickerState = rememberDatePickerState()
    val scope = rememberCoroutineScope()
    val userId = sharedPref.getUserIdSharedPref()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""
    val isValidTask = title.isNotBlank() && description.isNotBlank() && selectedDate.isNotEmpty()
    var refreshKey by remember { mutableStateOf(0) }
    LaunchedEffect(key1 = refreshKey) {
        scope.launch(Dispatchers.IO) {
            try {
                val taskService = Retrofit.Builder()
                    .baseUrl("https://taskapi.juanfrausto.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TaskService::class.java)
                val response = taskService.getTasks(userId = userId)
                Log.i("HomeScreen", response.toString())
                if (response.code() == 200) {
                    withContext(Dispatchers.Main) {
                        tasks = response.body()?.tasks ?: emptyList()
                        completedTasks = response.body()?.completedTasks ?: 0
                        pendingTasks = response.body()?.pendingTasks ?: 0
                        expiredTasks = response.body()?.expiredTasks ?: 0
                        isLoading = false
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeScreenError", e.toString())
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Tareas",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Hola",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            IconButton(onClick = {
                sharedPref.removeUserSharedPref()
                navController.navigate(Screens.Login.route) {
                    popUpTo(Screens.Login.route) { inclusive = true }
                }
            }) {
                Icon(
                    imageVector = Logout,
                    contentDescription = "logout",
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TaskBox(
                    value = completedTasks,
                    title = "Completadas",
                    color = MaterialTheme.colorScheme.secondary
                )
                TaskBox(value = pendingTasks, title = "Pendientes", color = yellow)
                TaskBox(value = expiredTasks, title = "Vencidas", color = Color.Red)

            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Lista de tareas",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { showBottomSheet = true }) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "add",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn {
                items(tasks) { task ->
                    TaskItem(task = task) { isChecked ->
                        scope.launch(Dispatchers.IO) {
                            try {
                                task.isDone = isChecked
                                val taskDTO = task.toDTO()

                                val taskService = Retrofit.Builder()
                                    .baseUrl("https://taskapi.juanfrausto.com/api/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()
                                    .create(TaskService::class.java)

                                val response = taskService.updateTask(task = taskDTO, id = task.id)
                                Log.i("HomeScreen", response.toString())
                                val taskResponse = response.body()!!
                                withContext(Dispatchers.Main) {
                                    tasks = tasks.map { existingTask ->
                                        if (existingTask.id == task.id) taskResponse else existingTask
                                    }
                                    refreshKey++ // Incrementa la clave para actualizar LaunchedEffect
                                }
                            } catch (e: Exception) {
                                Log.e("TaskUpdateError", e.toString())
                            }
                        }

                    }
                }
            }
        }
    }

    // Bottom Modal
    if (showBottomSheet) {
        ModalBottomSheet(onDismissRequest = { showBottomSheet = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                Text(text = "Nueva tarea", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text(text = "Titulo") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Gray
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Title,
                            contentDescription = "email",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text(text = "Descripcion") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Gray
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Description,
                            contentDescription = "description",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                DatePicker(state = datePickerState)
                Button(
                    enabled = isValidTask,
                    onClick = {
                    showBottomSheet = false
                    scope.launch(Dispatchers.IO) {
                        val taskService = Retrofit.Builder()
                            .baseUrl("https://taskapi.juanfrausto.com/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(TaskService::class.java)
                        val taskDTO = TaskDTO(
                            userId = userId,
                            title = title,
                            description = description,
                            isDone = false,
                            dueDate = selectedDate
                        )
                        isLoading = true
                        val newTask = taskService.createTask(taskDTO)
                        isLoading = false
                        withContext(Dispatchers.Main) {
                            refreshKey++
                        }
                    }

                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Guardar")
                }
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val oneDayInMillis = 24 * 60 * 60 * 1000
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    formatter.timeZone = TimeZone.getDefault()
    return formatter.format(Date(millis + oneDayInMillis))
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {
    TaskAppTheme {
        HomeScreen(innerPadding = PaddingValues(0.dp), navController = rememberNavController())
    }
}