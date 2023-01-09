package com.vychev.keyvaluestore.di

import com.vychev.keyvaluestore.data.repository.KeyValueRepositoryImpl
import com.vychev.keyvaluestore.data.repository.TransactionsRepositoryImpl
import com.vychev.keyvaluestore.data.store.TransactionStoreImpl
import com.vychev.keyvaluestore.domain.repositories.KeyValueRepository
import com.vychev.keyvaluestore.domain.repositories.KeyValueRepositoryProvider
import com.vychev.keyvaluestore.domain.repositories.TransactionStore
import com.vychev.keyvaluestore.domain.repositories.TransactionsRepository
import com.vychev.keyvaluestore.executors.factory.KeyValueRepositoryProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {

    @Binds
    fun bindKeyValueRepository(repository: KeyValueRepositoryImpl): KeyValueRepository

    @Binds
    @Singleton
    fun bindTransactionsRepository(repository: TransactionsRepositoryImpl): TransactionsRepository

    @Binds
    @Singleton
    fun bindTransactionStore(store: TransactionStoreImpl): TransactionStore

    @Binds
    fun bindKeyValueRepositoryProvider(repository: KeyValueRepositoryProviderImpl): KeyValueRepositoryProvider

}