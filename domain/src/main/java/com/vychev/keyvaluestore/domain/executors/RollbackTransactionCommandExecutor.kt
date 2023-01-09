package com.vychev.keyvaluestore.domain.executors

import com.vychev.keyvaluestore.domain.entity.CommandParams
import com.vychev.keyvaluestore.domain.exception.NoTransactionException
import com.vychev.keyvaluestore.domain.repositories.TransactionsRepository
import java.lang.IllegalArgumentException

class RollbackTransactionCommandExecutor(
    private val transactionsRepository: TransactionsRepository
) : CommandExecutor<CommandParams, Unit> {

    override suspend operator fun invoke(params: CommandParams): Result<Unit> {
        val topTransaction = transactionsRepository.getTop()
        return if (topTransaction.isRoot) {
            Result.failure(NoTransactionException())
        } else {
            transactionsRepository.remove(topTransaction.id)
            Result.success(Unit)
        }
    }
}