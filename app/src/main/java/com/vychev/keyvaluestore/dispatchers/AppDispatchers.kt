package com.vychev.keyvaluestore.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

data class AppDispatchers(

    /**
     * A [CoroutineDispatcher] that is used for CPU intensive operations.
     */
    val default: CoroutineDispatcher,

    /**
     * A [CoroutineDispatcher] that is used for IO operations: file, networks, etc.
     */
    val io: CoroutineDispatcher,

    /**
     * A [CoroutineDispatcher] hat is confined to the Main thread operating with UI objects
     */
    val main: CoroutineDispatcher,

    /**
     * A [CoroutineDispatcher] that is not confined to any specific thread
     */
    val unconfined: CoroutineDispatcher
)