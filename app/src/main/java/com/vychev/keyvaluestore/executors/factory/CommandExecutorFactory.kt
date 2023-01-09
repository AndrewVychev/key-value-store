package com.vychev.keyvaluestore.executors.factory

import com.vychev.keyvaluestore.domain.executors.transactional.TransactionalCountValueCommandExecutor
import com.vychev.keyvaluestore.domain.entity.Command
import com.vychev.keyvaluestore.domain.entity.CommandParams
import com.vychev.keyvaluestore.domain.executors.BeginTransactionCommandExecutor
import com.vychev.keyvaluestore.domain.executors.CommandExecutor
import com.vychev.keyvaluestore.domain.executors.CommitTransactionCommandExecutor
import com.vychev.keyvaluestore.domain.executors.DeleteValueCommandExecutor
import com.vychev.keyvaluestore.domain.executors.RollbackTransactionCommandExecutor
import com.vychev.keyvaluestore.domain.executors.SetValueCommandExecutor
import com.vychev.keyvaluestore.domain.repositories.TransactionsRepository
import com.vychev.keyvaluestore.domain.executors.transactional.TransactionalGetValueCommandExecutor
import com.vychev.keyvaluestore.domain.repositories.KeyValueRepositoryProvider
import javax.inject.Inject

class CommandExecutorFactory @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val keyValueRepositoryProvider: KeyValueRepositoryProvider
) {

    fun get(command: Command, key: String): CommandExecutor<CommandParams, Any> {
        return when (command) {
            is Command.BeginTransaction -> BeginTransactionCommandExecutor(transactionsRepository)
            is Command.CommitTransaction -> CommitTransactionCommandExecutor(transactionsRepository)
            is Command.Count -> TransactionalCountValueCommandExecutor(
                repositoryProvider = keyValueRepositoryProvider,
                transactionsRepository = transactionsRepository
            )
            is Command.Delete -> DeleteValueCommandExecutor(
                keyValueRepository = keyValueRepositoryProvider.get(key)
            )
            is Command.Get -> TransactionalGetValueCommandExecutor(
                repositoryProvider = keyValueRepositoryProvider,
                transactionsRepository = transactionsRepository
            )
            is Command.RollbackTransaction -> RollbackTransactionCommandExecutor(
                transactionsRepository
            )
            is Command.Set -> SetValueCommandExecutor(keyValueRepositoryProvider.get(key))
        } as CommandExecutor<CommandParams, Any>
    }
}