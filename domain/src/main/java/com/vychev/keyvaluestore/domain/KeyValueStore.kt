package com.vychev.keyvaluestore.domain

interface KeyValueStore {

    fun put(key: String, value: String)

    fun get(key: String): String?

    fun delete(key: String)

    fun count(key: String) : Int

    /**
     * Merge one store into another. It can be useful when we need to commit transaction.
     */
    fun merge(keyValueStore: KeyValueStore)

    fun keys(): Set<String>

}