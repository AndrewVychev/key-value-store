package com.vychev.keyvaluestore.domain.repositories

interface KeyValueRepository {

    fun put(key: String, value: String)

    fun get(key: String): String?

    fun delete(key: String)

    fun count(key: String): Int

    fun clear()

    fun entries(): Iterable<Map.Entry<String, String>>
}

