package com.vychev.keyvaluestore.data.repository

import com.vychev.keyvaluestore.domain.entity.Transaction
import com.vychev.keyvaluestore.domain.repositories.TransactionStore
import com.vychev.keyvaluestore.domain.repositories.TransactionsRepository
import java.util.LinkedList
import javax.inject.Inject

/**
 * Class to work with transactions (add/remove/commit/etc)
 * Every transaction has their own scoped store.
 * We have access to this store throw TransactionStore interface
 */
class TransactionsRepositoryImpl @Inject constructor(
    private val transactionStore: TransactionStore
) : TransactionsRepository {

    private val transactions = LinkedList<Transaction>()

    init {
        add(Transaction(isRoot = true))
    }

    override fun add(transaction: Transaction) {
        transactions.addFirst(transaction)
        transactionStore.add(transaction.id)
    }

    override fun remove(transactionId: String) {
        transactions.removeAll { it.id == transactionId }
        transactionStore.remove(transactionId)
    }

    override fun commit() {
        val store = transactionStore.get(getTop().id) ?: throw NullPointerException()
        remove(getTop().id)
        transactionStore.get(getTop().id)?.merge(store)
    }

    override fun getTop() = transactions.first

    override fun size() = transactions.size

    override fun iterable() = transactions.asIterable()
}