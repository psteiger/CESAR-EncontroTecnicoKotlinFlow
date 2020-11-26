package com.freelapp.encontrotecnicoflow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.freelapp.encontrotecnicoflow.components.randomintgerenator.RandomIntGenerator
import com.freelapp.encontrotecnicoflow.ktx.generateRandomIntAwait
import com.freelapp.encontrotecnicoflow.repository.Repository
import com.freelapp.encontrotecnicoflow.repository.RepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.CompletableFuture

class MainActivityViewModel(app: Application) : AndroidViewModel(app) {

    private val repository: Repository = RepositoryImpl(app.applicationContext, viewModelScope)

    fun generateRandomInt(callback: RandomIntGenerator.Callback) =
        repository.randomIntGenerator.generateRandomInt(callback)

    fun generateRandomIntCompletableFuture(): CompletableFuture<Int> =
        repository.randomIntGenerator.generateRandomIntCompletableFuture()

    suspend fun generateManyRandomInt(howMany: Int): List<Int> =
        repository.randomIntGenerator.generateManyRandomInt(howMany)

    suspend fun generateRandomIntAwait(): Int =
        repository.randomIntGenerator.generateRandomIntAwait()

    fun generateManyRandomIntFlow(howMany: Int): StateFlow<Int> =
        repository.randomIntGenerator.generateManyRandomIntFlow(howMany)
}