package com.vychev.keyvaluestore.data.store

import com.vychev.keyvaluestore.domain.KeyValueStore
import javax.inject.Inject

class KeyValueStoreImpl @Inject constructor() : KeyValueStore {

    private val store = mutableMapOf<String, String>()

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

    override fun merge(keyValueStore: KeyValueStore) {
        keyValueStore.keys().forEach { key ->
            val value = requireNotNull(keyValueStore.get(key))
            store[key] = value
        }
    }

    override fun keys(): Set<String> {
        return store.keys
    }
}