package com.vychev.keyvaluestore.domain.repositories

import com.vychev.keyvaluestore.domain.entity.Transaction

interface TransactionsRepository {

    fun add(transaction: Transaction)

    fun remove(transactionId: String)

    fun commit()

    fun getTop(): Transaction

    fun size() : Int

    fun iterable() : Iterable<Transaction>
}