package com.vychev.keyvaluestore.executors.factory

import com.vychev.keyvaluestore.data.repository.KeyValueRepositoryImpl
import com.vychev.keyvaluestore.domain.repositories.KeyValueRepository
import com.vychev.keyvaluestore.domain.repositories.KeyValueRepositoryProvider
import com.vychev.keyvaluestore.domain.repositories.TransactionStore
import javax.inject.Inject

class KeyValueRepositoryProviderImpl @Inject constructor(
    private val transactionStore: TransactionStore
) : KeyValueRepositoryProvider{

    override fun get(storeId: String): KeyValueRepository {
        return KeyValueRepositoryImpl(requireNotNull(transactionStore.get(storeId)))
    }
}