package com.vychev.keyvaluestore.domain.executors

import com.vychev.keyvaluestore.domain.entity.CommandParams
import com.vychev.keyvaluestore.domain.entity.Transaction
import com.vychev.keyvaluestore.domain.repositories.TransactionsRepository

class BeginTransactionCommandExecutor(
    private val transactionsRepository: TransactionsRepository
) : CommandExecutor<CommandParams, Unit> {

    /**
     * Adding new transaction into repository
     */
    override suspend fun invoke(params: CommandParams): Result<Unit> {
        transactionsRepository.add(transaction = Transaction())
        return Result.success(Unit)
    }
}