package com.vychev.keyvaluestore.domain.executors.transactional

import com.vychev.keyvaluestore.domain.entity.KeyValueParams
import com.vychev.keyvaluestore.domain.executors.CommandExecutor
import com.vychev.keyvaluestore.domain.executors.CountValueCommandExecutor
import com.vychev.keyvaluestore.domain.repositories.KeyValueRepositoryProvider
import com.vychev.keyvaluestore.domain.repositories.TransactionsRepository
import javax.inject.Inject

/**
 * This executor is a wrapper around CountValueCommandExecutor
 * To correct count value in transactional store we need to run throw
 * all child transactions and sum all counted values
 */
class TransactionalCountValueCommandExecutor @Inject constructor(
    private val repositoryProvider: KeyValueRepositoryProvider,
    private val transactionsRepository: TransactionsRepository
) : CommandExecutor<KeyValueParams, Int> {

    override suspend fun invoke(params: KeyValueParams): Result<Int> {
        var sum = 0
        transactionsRepository.iterable().forEach { transaction ->
            val countValueExecutor = CountValueCommandExecutor(
                keyValueRepository = repositoryProvider.get(transaction.id)
            )
            sum += countValueExecutor(params).getOrThrow()
        }
        return Result.success(sum)
    }
}