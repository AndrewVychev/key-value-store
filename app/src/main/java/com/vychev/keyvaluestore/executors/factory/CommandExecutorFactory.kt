package com.vychev.keyvaluestore.executors.factory

import com.vychev.keyvaluestore.domain.entity.Command
import com.vychev.keyvaluestore.domain.entity.CommandParams
import com.vychev.keyvaluestore.domain.executors.BeginTransactionCommandExecutor
import com.vychev.keyvaluestore.domain.executors.CommandExecutor
import com.vychev.keyvaluestore.domain.executors.CommitTransactionCommandExecutor
import com.vychev.keyvaluestore.domain.executors.CountValueCommandExecutor
import com.vychev.keyvaluestore.domain.executors.DeleteValueCommandExecutor
import com.vychev.keyvaluestore.domain.executors.GetValueCommandExecutor
import com.vychev.keyvaluestore.domain.executors.RollbackTransactionCommandExecutor
import com.vychev.keyvaluestore.domain.executors.SetValueCommandExecutor
import javax.inject.Inject

class CommandExecutorFactory @Inject constructor(
    private val beginTransactionCommandExecutor: BeginTransactionCommandExecutor,
    private val commitTransactionCommandExecutor: CommitTransactionCommandExecutor,
    private val countValueCommandExecutor: CountValueCommandExecutor,
    private val deleteValueCommandExecutor: DeleteValueCommandExecutor,
    private val getValueCommandExecutor: GetValueCommandExecutor,
    private val rollbackTransactionCommandExecutor: RollbackTransactionCommandExecutor,
    private val setValueCommandExecutor: SetValueCommandExecutor
) {

    fun get(command: Command): CommandExecutor<CommandParams, Any> {
        return when (command) {
            is Command.BeginTransaction -> beginTransactionCommandExecutor
            is Command.CommitTransaction -> commitTransactionCommandExecutor
            is Command.Count -> countValueCommandExecutor
            is Command.Delete -> deleteValueCommandExecutor
            is Command.Get -> getValueCommandExecutor
            is Command.RollbackTransaction -> rollbackTransactionCommandExecutor
            is Command.Set -> setValueCommandExecutor
        } as CommandExecutor<CommandParams, Any>
    }
}