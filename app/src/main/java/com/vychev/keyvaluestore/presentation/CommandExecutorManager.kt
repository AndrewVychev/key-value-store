package com.vychev.keyvaluestore.presentation

import com.vychev.keyvaluestore.domain.entity.BeginTransactionParam
import com.vychev.keyvaluestore.domain.entity.Command
import com.vychev.keyvaluestore.domain.usecase.ClearCommandsUseCase
import com.vychev.keyvaluestore.executors.factory.CommandExecutorFactory
import com.vychev.keyvaluestore.mapper.CommandResultMapper
import com.vychev.keyvaluestore.utils.withReentrantLock
import javax.inject.Inject
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * This manager are responsible for execute command, return string result and clear commands
 */
class CommandExecutorManager @Inject constructor(
    private val commandExecutorFactory: CommandExecutorFactory,
    private val clearCommandsUseCase: ClearCommandsUseCase,
    private val mapper: CommandResultMapper
) {

    private val mutex = Mutex()

    suspend fun addRootTransaction() = mutex.withReentrantLock {
        commandExecutorFactory.get(Command.BeginTransaction())
            .invoke(BeginTransactionParam(isRoot = true))
    }

    suspend fun execute(command: Command): String {
        val params = mapper.mapParams(command)
        return mutex.withReentrantLock {
            // For every command we need executor with current transaction store/repository dependency
            val result = commandExecutorFactory.get(command = command).invoke(params)
            mapper.map(command = command, result = result)
        }
    }

    fun clear() {
        clearCommandsUseCase()
    }
}