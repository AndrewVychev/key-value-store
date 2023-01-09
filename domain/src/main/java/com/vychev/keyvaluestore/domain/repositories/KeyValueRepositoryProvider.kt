package com.vychev.keyvaluestore.domain.repositories

interface KeyValueRepositoryProvider {

    fun get(storeId: String): KeyValueRepository

}