package com.vychev.keyvaluestore.domain.repositories

import com.vychev.keyvaluestore.domain.entity.Snapshot

interface TransactionSnapshotRepository {

    fun addSnapshot(transactionId: String, snapshot: Snapshot)

    fun removeSnapshot(transactionId: String)

    fun getSnapshot(transactionId: String): Snapshot?
}