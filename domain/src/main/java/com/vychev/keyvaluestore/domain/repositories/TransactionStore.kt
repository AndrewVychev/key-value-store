package com.vychev.keyvaluestore.domain.repositories

import com.vychev.keyvaluestore.domain.KeyValueStore

interface TransactionStore {

    fun add(transactionId: String)

    fun remove(transactionId: String)

    fun get(transactionId: String): KeyValueStore?
}