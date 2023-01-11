package com.vychev.keyvaluestore.domain.executors

import com.vychev.keyvaluestore.domain.entity.BeginTransactionParam
import com.vychev.keyvaluestore.domain.entity.CommandParams
import com.vychev.keyvaluestore.domain.entity.Transaction
import com.vychev.keyvaluestore.domain.repositories.KeyValueRepository
import com.vychev.keyvaluestore.domain.entity.Snapshot
import com.vychev.keyvaluestore.domain.entity.SnapshotEntry
import com.vychev.keyvaluestore.domain.repositories.TransactionSnapshotRepository
import com.vychev.keyvaluestore.domain.repositories.TransactionsRepository
import javax.inject.Inject

class BeginTransactionCommandExecutor @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val keyValueRepository: KeyValueRepository,
    private val snapshotRepository: TransactionSnapshotRepository
) : CommandExecutor<BeginTransactionParam, Unit> {

    /**
     * Adding new transaction into repository
     */
    override suspend fun invoke(params: BeginTransactionParam): Result<Unit> {
        val transaction = Transaction(isRoot = params.isRoot)
        transactionsRepository.add(transaction = transaction)
        snapshotRepository.addSnapshot(
            transactionId = transaction.id,
            snapshot = Snapshot(entries = keyValueRepository.entries().map { SnapshotEntry(it.key, it.value) })
        )
        return Result.success(Unit)
    }
}