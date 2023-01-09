package com.vychev.keyvaluestore.domain.executors.transactional

import com.vychev.keyvaluestore.domain.entity.KeyValueParams
import com.vychev.keyvaluestore.domain.executors.CommandExecutor
import com.vychev.keyvaluestore.domain.executors.GetValueCommandExecutor
import com.vychev.keyvaluestore.domain.repositories.KeyValueRepositoryProvider
import com.vychev.keyvaluestore.domain.repositories.TransactionsRepository
import javax.inject.Inject

/**
 * This executor is a wrapper around GetValueCommandExecutor
 * In case when we can't find value inside current transaction
 * we have to go throw another transactions and try to find value in their scopes
 */
class TransactionalGetValueCommandExecutor @Inject constructor(
    private val repositoryProvider: KeyValueRepositoryProvider,
    private val transactionsRepository: TransactionsRepository
) : CommandExecutor<KeyValueParams, String> {

    override suspend fun invoke(params: KeyValueParams): Result<String> {
        transactionsRepository.iterable().forEach { transaction ->
            val getValueExecutor = GetValueCommandExecutor(
                keyValueRepository = repositoryProvider.get(transaction.id)
            )
            val result = getValueExecutor(params)
            if (result.isSuccess) {
                return result
            }
        }
        return Result.failure(NullPointerException())
    }
}