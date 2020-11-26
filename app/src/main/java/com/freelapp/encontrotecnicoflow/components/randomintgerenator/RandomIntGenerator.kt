package com.freelapp.encontrotecnicoflow.components.randomintgerenator

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

interface RandomIntGenerator {
    fun interface Callback : Consumer<Int>

    fun generateRandomInt(callback: Callback)
    fun generateRandomIntCompletableFuture(): CompletableFuture<Int>

    suspend fun generateManyRandomInt(howMany: Int): List<Int>
    fun generateManyRandomIntFlow(howMany: Int): StateFlow<Int>
}































//fun interface Callback : Consumer<Int>
//
//fun generateRandomIntAsync(callback: Callback)
//fun generateRandomIntAsync(): CompletableFuture<Int>
////    fun generateRandomIntAsync(): Deferred<Int>
