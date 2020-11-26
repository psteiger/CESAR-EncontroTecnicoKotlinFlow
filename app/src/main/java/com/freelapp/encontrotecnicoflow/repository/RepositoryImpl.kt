package com.freelapp.encontrotecnicoflow.repository

import android.content.Context
import com.freelapp.encontrotecnicoflow.components.randomintgerenator.RandomIntGenerator
import com.freelapp.encontrotecnicoflow.components.randomintgerenator.RandomIntGeneratorImpl
import kotlinx.coroutines.CoroutineScope

class RepositoryImpl(
    context: Context,
    scope: CoroutineScope,
    override val randomIntGenerator: RandomIntGenerator = RandomIntGeneratorImpl(context, scope)
) : Repository {
    val a: Flow<DataSnapshot> = myFirebaseDatabase
        .asFlow("users")
        .shareIn(...)
    
    val b: Flow<User> = a
        .map { it.toUser() }
        .shareIn(...)
}