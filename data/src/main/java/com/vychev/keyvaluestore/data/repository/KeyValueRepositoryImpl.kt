package com.vychev.keyvaluestore.data.repository

import com.vychev.keyvaluestore.domain.KeyValueStore
import com.vychev.keyvaluestore.domain.repositories.KeyValueRepository
import javax.inject.Inject

class KeyValueRepositoryImpl @Inject constructor(private val store: KeyValueStore) : KeyValueRepository {

    override fun put(key: String, value: String) {
        store.put(key, value)
    }

    override fun get(key: String): String? {
        return store.get(key)
    }

    override fun delete(key: String) {
        store.delete(key)
    }

    override fun count(key: String): Int {
        return store.count(key)
    }

    override fun clear() {
        store.clear()
    }

    override fun entries(): Iterable<Map.Entry<String, String>> {
        return store.entries()
    }
}