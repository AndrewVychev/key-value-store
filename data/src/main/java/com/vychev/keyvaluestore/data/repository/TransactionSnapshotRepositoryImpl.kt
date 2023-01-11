package com.vychev.keyvaluestore.data.repository

import com.vychev.keyvaluestore.domain.entity.Snapshot
import com.vychev.keyvaluestore.domain.repositories.TransactionSnapshotRepository
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class TransactionSnapshotRepositoryImpl @Inject constructor(): TransactionSnapshotRepository {

    private val snapshots = ConcurrentHashMap<String, Snapshot>()

    override fun addSnapshot(transactionId: String, snapshot: Snapshot) {
        snapshots[transactionId] = snapshot
    }

    override fun removeSnapshot(transactionId: String) {
        snapshots.remove(transactionId)
    }

    override fun getSnapshot(transactionId: String): Snapshot? {
        return snapshots[transactionId]
    }
}