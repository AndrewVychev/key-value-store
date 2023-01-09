package com.vychev.keyvaluestore.data.store

import com.vychev.keyvaluestore.domain.KeyValueStore
import com.vychev.keyvaluestore.domain.repositories.TransactionStore
import javax.inject.Inject

class TransactionStoreImpl @Inject constructor(): TransactionStore {

    private val stores = mutableMapOf<String, KeyValueStore>()

    override fun add(transactionId: String) {
        stores[transactionId] = KeyValueStoreImpl()
    }

    override fun remove(transactionId: String) {
        stores.remove(transactionId)
    }

    override fun get(transactionId: String): KeyValueStore? {
        return stores[transactionId]
    }
}