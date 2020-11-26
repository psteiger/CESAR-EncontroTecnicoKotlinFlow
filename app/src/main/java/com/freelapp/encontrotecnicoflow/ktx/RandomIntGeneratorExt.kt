package com.freelapp.encontrotecnicoflow.ktx

import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.freelapp.encontrotecnicoflow.components.randomintgerenator.RandomIntGenerator
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun FirebaseDatabase.asFlow() = callbackFlow {
    val listener = addListener {
        onNewValue { send(it) }
        onFinished { cancel(MyException()) }
    }

    awaitClose {
        removeListener(listener)
    }
}.shareIn(
    ...
    ...
...
)

suspend fun RandomIntGenerator.generateRandomIntAwait() = suspendCoroutine<Int> { continuation ->
    generateRandomInt {
        continuation.resume(it)
    }
}
suspend inline fun <reified T> CompletableFuture<T>.await() = suspendCoroutine<T> { continuation ->
    thenAcceptAsync {
        continuation.resume(it)
    }
}






@PublishedApi
internal class ObserverImpl<T> (
    lifecycleOwner: LifecycleOwner,
    private val flow: Flow<T>,
    private val collector: suspend (T) -> Unit
) : DefaultLifecycleObserver {

    private var job: Job? = null

    override fun onStart(owner: LifecycleOwner) {
        job = owner.lifecycleScope.launch {
            flow.collect {
                collector(it)
            }
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        job?.cancel()
        job = null
    }

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }
}

inline fun <reified T> Flow<T>.observe(
    lifecycleOwner: LifecycleOwner,
    noinline collector: suspend (T) -> Unit
) {
    ObserverImpl(lifecycleOwner, this, collector)
}

inline fun <reified T> Flow<T>.observeIn(
    lifecycleOwner: LifecycleOwner
) {
    ObserverImpl(lifecycleOwner, this, {})
}






























//import com.freelapp.encontrotecnicoflow.components.randomintgerenator.RandomIntGenerator
//import kotlin.coroutines.resume
//import kotlin.coroutines.suspendCoroutine
//
//suspend fun RandomIntGenerator.generateRandomInt(): Int = suspendCoroutine { continuation ->
//    generateRandomIntAsync {
//        continuation.resume(it)
//    }
//}