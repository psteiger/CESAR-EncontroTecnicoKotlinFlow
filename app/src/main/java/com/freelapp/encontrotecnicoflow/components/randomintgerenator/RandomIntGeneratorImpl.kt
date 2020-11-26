package com.freelapp.encontrotecnicoflow.components.randomintgerenator

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.util.concurrent.CompletableFuture
import kotlin.random.Random

class RandomIntGeneratorImpl(
    private val context: Context,
    private val scope: CoroutineScope
) : RandomIntGenerator {

    override fun generateRandomInt(callback: RandomIntGenerator.Callback) {
        generateRandomIntCompletableFuture()
            .thenAcceptAsync(callback, ContextCompat.getMainExecutor(context))
    }

    override fun generateRandomIntCompletableFuture(): CompletableFuture<Int> =
        CompletableFuture.supplyAsync {
            val number = Random.nextInt(0, 10)
            Log.d(TAG, "generateRandomInt: emitting number $number on ${Thread.currentThread().name}")
            number
        }


    override fun generateManyRandomIntFlow(howMany: Int): StateFlow<Int> = flow {
        (0..howMany).onEach { emit(generateRandomInt()) }
    }.stateIn(
        scope,
        SharingStarted.WhileSubscribed(),
        -1000
    )

    override suspend fun generateManyRandomInt(howMany: Int): List<Int> = withContext(Dispatchers.Default) {
        (0..howMany)
            .onEach { Log.d(TAG, "generateManyRandomInt: ${Thread.currentThread().name}") }
            .onEach {
                withContext(Dispatchers.Main) {
                    Log.d(TAG, "generateManyRandomInt now on: ${Thread.currentThread().name}")
                }
            }
            .map { generateRandomInt() }
    }

    private suspend fun generateRandomInt(): Int = withContext(Dispatchers.IO) {
        val number = Random.nextInt(0, 10)
        Log.d(TAG, "generateRandomInt: emitting number $number on ${Thread.currentThread().name}")
        delay(200L) // processamento ocorrendo
        number
    }

    companion object {
        private const val TAG = "RandomIntGeneratorImpl"
    }
}
































//
//override fun generateRandomIntAsync(callback: RandomIntGenerator.Callback) {
//    supplyAsync()
//        .thenAcceptAsync(callback, ContextCompat.getMainExecutor(context))
//}
//
//override fun generateRandomIntAsync(): CompletableFuture<Int> =
//    supplyAsync()
//
//private fun supplyAsync(): CompletableFuture<Int> =
//    CompletableFuture
//        .supplyAsync {
//            val number = Random.nextInt(0, 10)
//            Log.d(TAG, "generateRandomInt: Supplying $number on thread ${Thread.currentThread().name}")
//            number
//        }
//
//companion object {
//    private const val TAG = "RandomIntGeneratorImpl"
//}