package com.vychev.keyvaluestore.data.repository

import com.vychev.keyvaluestore.domain.entity.Transaction
import com.vychev.keyvaluestore.domain.repositories.TransactionsRepository
import java.util.LinkedList
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.ConcurrentLinkedQueue
import javax.inject.Inject

/**
 * Class to work with transactions (add/remove/commit/etc)
 * Every transaction has their own scoped store.
 * We have access to this store throw TransactionStore interface
 */
class TransactionsRepositoryImpl @Inject constructor() : TransactionsRepository {

    private val transactions = ConcurrentLinkedDeque<Transaction>()

    override fun add(transaction: Transaction) {
        transactions.addFirst(transaction)
    }

    override fun remove(transactionId: String) {
        transactions.removeAll { it.id == transactionId }
    }

    override fun getTop() = transactions.first

    override fun size() = transactions.size

    override fun iterable() = transactions.asIterable()

}