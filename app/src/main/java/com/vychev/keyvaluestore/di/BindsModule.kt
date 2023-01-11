package com.vychev.keyvaluestore.di

import com.vychev.keyvaluestore.data.repository.KeyValueRepositoryImpl
import com.vychev.keyvaluestore.data.repository.TransactionSnapshotRepositoryImpl
import com.vychev.keyvaluestore.data.repository.TransactionsRepositoryImpl
import com.vychev.keyvaluestore.data.store.KeyValueStoreImpl
import com.vychev.keyvaluestore.domain.KeyValueStore
import com.vychev.keyvaluestore.domain.repositories.KeyValueRepository
import com.vychev.keyvaluestore.domain.repositories.TransactionSnapshotRepository
import com.vychev.keyvaluestore.domain.repositories.TransactionsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {

    @Binds
    @Singleton
    fun bindKeyValueStore(repository: KeyValueStoreImpl): KeyValueStore

    @Binds
    @Singleton
    fun bindKeyValueRepository(repository: KeyValueRepositoryImpl): KeyValueRepository

    @Binds
    @Singleton
    fun bindTransactionsRepository(repository: TransactionsRepositoryImpl): TransactionsRepository

    @Binds
    @Singleton
    fun bindTransactionSnapshotRepository(repository: TransactionSnapshotRepositoryImpl): TransactionSnapshotRepository

}