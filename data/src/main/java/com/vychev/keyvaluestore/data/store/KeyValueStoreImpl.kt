package com.vychev.keyvaluestore.data.store

import com.vychev.keyvaluestore.domain.KeyValueStore
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class KeyValueStoreImpl @Inject constructor() : KeyValueStore {

    private val store = ConcurrentHashMap<String, String>()

    override fun put(key: String, value: String) {
        store[key] = value
    }

    override fun get(key: String): String? {
        return store[key]
    }

    override fun delete(key: String) {
        store.remove(key)
    }

    override fun count(key: String): Int {
        return store.count { it.value == key }
    }

    override fun clear() {
        store.clear()
    }

    override fun entries(): Iterable<Map.Entry<String, String>> {
        return store.entries
    }
}