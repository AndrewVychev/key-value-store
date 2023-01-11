package com.vychev.keyvaluestore.domain.executors

import com.vychev.keyvaluestore.domain.entity.CommandParams
import com.vychev.keyvaluestore.domain.exception.NoTransactionException
import com.vychev.keyvaluestore.domain.repositories.KeyValueRepository
import com.vychev.keyvaluestore.domain.repositories.TransactionSnapshotRepository
import com.vychev.keyvaluestore.domain.repositories.TransactionsRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject

class RollbackTransactionCommandExecutor @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val snapshotRepository: TransactionSnapshotRepository,
    private val keyValueRepository: KeyValueRepository
) : CommandExecutor<CommandParams, Unit> {

    override suspend operator fun invoke(params: CommandParams): Result<Unit> {
        val topTransaction = transactionsRepository.getTop()
        return if (topTransaction.isRoot) {
            Result.failure(NoTransactionException())
        } else {
            transactionsRepository.remove(topTransaction.id)
            val snapshot = snapshotRepository.getSnapshot(topTransaction.id) ?: throw NullPointerException()
            keyValueRepository.clear()
            snapshot.entries.forEach { entry ->
                keyValueRepository.put(entry.key, entry.value)
            }
            Result.success(Unit)
        }
    }
}