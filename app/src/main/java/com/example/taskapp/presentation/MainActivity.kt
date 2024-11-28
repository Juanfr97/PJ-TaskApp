package com.example.taskapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskapp.domain.use_cases.SharedPref
import com.example.taskapp.presentation.ui.screens.HomeScreen
import com.example.taskapp.presentation.ui.screens.LoginScreen
import com.example.taskapp.presentation.ui.screens.RegisterScreen
import com.example.taskapp.presentation.ui.theme.TaskAppTheme
import com.example.taskapp.utils.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskAppTheme {
                val navController = rememberNavController()
                val sharedPref = SharedPref(LocalContext.current)
                val isLogged = sharedPref.getIsLoggedSharedPref()
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (isLogged) Screens.Home.route else Screens.Login.route
                    ) {
                        composable(route = Screens.Login.route) {
                            LoginScreen(
                                innerPadding = innerPadding,
                                navController = navController,
                                snackbarHostState = snackbarHostState
                            )
                        }
                        composable(route = Screens.Home.route) {
                            HomeScreen(innerPadding = innerPadding, navController = navController)
                        }
                        composable(route = Screens.Register.route) {
                            RegisterScreen(
                                innerPadding = innerPadding,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}