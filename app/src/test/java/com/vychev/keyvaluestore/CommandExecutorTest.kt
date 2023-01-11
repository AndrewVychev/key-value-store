package com.vychev.keyvaluestore

import com.vychev.keyvaluestore.data.repository.KeyValueRepositoryImpl
import com.vychev.keyvaluestore.data.repository.TransactionSnapshotRepositoryImpl
import com.vychev.keyvaluestore.data.repository.TransactionsRepositoryImpl
import com.vychev.keyvaluestore.data.store.KeyValueStoreImpl
import com.vychev.keyvaluestore.domain.entity.Command
import com.vychev.keyvaluestore.domain.executors.BeginTransactionCommandExecutor
import com.vychev.keyvaluestore.domain.executors.CommitTransactionCommandExecutor
import com.vychev.keyvaluestore.domain.executors.CountValueCommandExecutor
import com.vychev.keyvaluestore.domain.executors.DeleteValueCommandExecutor
import com.vychev.keyvaluestore.domain.executors.GetValueCommandExecutor
import com.vychev.keyvaluestore.domain.executors.RollbackTransactionCommandExecutor
import com.vychev.keyvaluestore.domain.executors.SetValueCommandExecutor
import com.vychev.keyvaluestore.domain.usecase.ClearCommandsUseCase
import com.vychev.keyvaluestore.executors.factory.CommandExecutorFactory
import com.vychev.keyvaluestore.mapper.CommandResultMapper
import com.vychev.keyvaluestore.presentation.CommandExecutorManager
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

class CommandExecutorTest {

    private val transactionsRepository = TransactionsRepositoryImpl()
    private val keyValueRepository = KeyValueRepositoryImpl(KeyValueStoreImpl())
    private val snapshotRepository = TransactionSnapshotRepositoryImpl()

    private val commandExecutorManager = CommandExecutorManager(
        commandExecutorFactory = CommandExecutorFactory(
            BeginTransactionCommandExecutor(transactionsRepository, keyValueRepository, snapshotRepository),
            CommitTransactionCommandExecutor(transactionsRepository, snapshotRepository),
            CountValueCommandExecutor(keyValueRepository),
            DeleteValueCommandExecutor(keyValueRepository),
            GetValueCommandExecutor(keyValueRepository),
            RollbackTransactionCommandExecutor(transactionsRepository, snapshotRepository, keyValueRepository),
            SetValueCommandExecutor(keyValueRepository)
        ),
        clearCommandsUseCase = ClearCommandsUseCase(transactionsRepository = transactionsRepository),
        mapper = CommandResultMapper()
    )

    init {
        runBlocking { commandExecutorManager.addRootTransaction() }
    }

    @Test
    fun `Set and get a value`() = runBlocking {
        commandExecutorManager.execute(Command.Set("foo", "123"))
        val result = commandExecutorManager.execute(Command.Get("foo"))
        assertEquals("123", result)
    }

    @Test
    fun `Delete a value`() = runBlocking {
        commandExecutorManager.execute(Command.Delete("foo"))
        val result = commandExecutorManager.execute(Command.Get("foo"))
        assertEquals("key not set", result)
    }

    @Test
    fun `Count the number of occurrences of a value`() = runBlocking {
        commandExecutorManager.apply {
            execute(Command.Set("foo", "123"))
            execute(Command.Set("bar", "456"))
            execute(Command.Set("baz", "123"))
            assertEquals("2", execute(Command.Count("123")))
            assertEquals("1", execute(Command.Count("456")))
        }
        Unit
    }

    @Test
    fun `Commit a transaction`() = runBlocking {
        commandExecutorManager.apply {
            execute(Command.BeginTransaction())
            execute(Command.Set("foo", "456"))
            execute(Command.CommitTransaction())
            assertEquals("no transaction", execute(Command.RollbackTransaction()))
            assertEquals("456", execute(Command.Get("foo")))
        }
        Unit
    }

    @Test
    fun `Rollback a transaction`() = runBlocking {
        commandExecutorManager.apply {
            execute(Command.Set("foo", "123"))
            execute(Command.Set("bar", "abc"))
            execute(Command.BeginTransaction())
            execute(Command.Set("foo", "456"))
            assertEquals("456", execute(Command.Get("foo")))
            execute(Command.Set("bar", "def"))
            assertEquals("def", execute(Command.Get("bar")))
            execute(Command.RollbackTransaction())
            assertEquals("123", execute(Command.Get("foo")))
            assertEquals("abc", execute(Command.Get("bar")))
            assertEquals("no transaction", execute(Command.CommitTransaction()))
        }
        Unit
    }

    @Test
    fun `Nested transactions`() = runBlocking {
        commandExecutorManager.apply {
            execute(Command.Set("foo", "123"))
            execute(Command.BeginTransaction())
            execute(Command.Set("bar", "456"))
            execute(Command.Set("foo", "456"))
            execute(Command.BeginTransaction())
            assertEquals("2", execute(Command.Count("456")))
            assertEquals("456", execute(Command.Get("foo")))
            execute(Command.Set("foo", "789"))
            assertEquals("789", execute(Command.Get("foo")))
            execute(Command.RollbackTransaction())
            assertEquals("456", execute(Command.Get("foo")))
            execute(Command.RollbackTransaction())
            assertEquals("123", execute(Command.Get("foo")))
        }
        Unit
    }
}