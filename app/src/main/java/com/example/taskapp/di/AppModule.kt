package com.example.taskapp.di

import android.content.Context
import com.example.taskapp.datasource.repositories.AuthRepositoryImpl
import com.example.taskapp.datasource.repositories.TaskRepositoryImpl
import com.example.taskapp.datasource.services.AuthService
import com.example.taskapp.datasource.services.TaskService
import com.example.taskapp.domain.repositories.AuthRepository
import com.example.taskapp.domain.repositories.TaskRepository
import com.example.taskapp.domain.use_cases.SharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://taskapi.juanfrausto.com/api/"


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthService() : AuthService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideTaskService() : TaskService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TaskService::class.java)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskService: TaskService) : TaskRepository {
        return TaskRepositoryImpl(taskService)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService) : AuthRepository {
        return AuthRepositoryImpl(authService)
    }

    @Provides
    @Singleton
    fun provideSaveSharedPref(@ApplicationContext context: Context): SharedPref {
        return SharedPref(context)
    }

}