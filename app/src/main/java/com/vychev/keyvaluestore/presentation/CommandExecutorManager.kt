package com.vychev.keyvaluestore.presentation

import com.vychev.keyvaluestore.domain.entity.Command
import com.vychev.keyvaluestore.domain.usecase.ClearCommandsUseCase
import com.vychev.keyvaluestore.domain.usecase.GetCurrentTransactionUseCase
import com.vychev.keyvaluestore.executors.factory.CommandExecutorFactory
import com.vychev.keyvaluestore.mapper.CommandResultMapper
import javax.inject.Inject

/**
 * This manager are responsible for execute command, return string result and clear commands
 */
class CommandExecutorManager @Inject constructor(
    private val commandExecutorFactory: CommandExecutorFactory,
    private val getCurrentTransactionUseCase: GetCurrentTransactionUseCase,
    private val clearCommandsUseCase: ClearCommandsUseCase,
    private val mapper: CommandResultMapper
) {

    suspend fun execute(command: Command): String {
        val params = mapper.mapParams(command)
        // For every command we need executor with current transaction store/repository dependency
        val result = commandExecutorFactory.get(
            command = command,
            key = getCurrentTransactionUseCase().id
        ).invoke(params)
        return mapper.map(command = command, result = result)
    }

    fun clear() {
        clearCommandsUseCase()
    }
}