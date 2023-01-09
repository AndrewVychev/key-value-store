package com.vychev.keyvaluestore.domain.executors

import com.vychev.keyvaluestore.domain.entity.CommandParams
import com.vychev.keyvaluestore.domain.exception.NoTransactionException
import com.vychev.keyvaluestore.domain.repositories.TransactionsRepository
import java.lang.IllegalArgumentException

class CommitTransactionCommandExecutor(
    private val transactionsRepository: TransactionsRepository
) : CommandExecutor<CommandParams, Unit> {

    /**
     * To commit transaction we need to merge current and previous transaction stores
     * and remove current transaction
     */
    override suspend fun invoke(params: CommandParams): Result<Unit> {
        val topTransaction = transactionsRepository.getTop()
        if (topTransaction.isRoot) {
            return Result.failure(NoTransactionException())
        }
        transactionsRepository.commit()
        return Result.success(Unit)
    }
}