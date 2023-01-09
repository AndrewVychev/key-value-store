package com.vychev.keyvaluestore.mapper

import com.vychev.keyvaluestore.domain.entity.Command
import com.vychev.keyvaluestore.domain.entity.CommandParams
import com.vychev.keyvaluestore.domain.entity.KeyValueParams
import javax.inject.Inject

class CommandResultMapper @Inject constructor(){

    /**
     * Map command params
     */
    fun mapParams(command: Command): CommandParams {
        return when (command) {
            is Command.BeginTransaction,
            is Command.CommitTransaction,
            is Command.RollbackTransaction -> CommandParams()
            is Command.Delete -> KeyValueParams(key = command.key)
            is Command.Count -> KeyValueParams(key = command.key)
            is Command.Get -> KeyValueParams(key = command.key)
            is Command.Set -> KeyValueParams(key = command.key, value = command.value)
        }
    }

    /**
     * Map command result into string
     */
    fun map(command: Command, result: Result<Any>): String {
        return when (command) {
            is Command.BeginTransaction -> ""
            is Command.CommitTransaction,
            is Command.RollbackTransaction-> {
                if (result.isFailure) {
                    "no transaction"
                } else {
                    ""
                }
            }
            is Command.Count -> {
                if (result.isFailure) {
                    "0"
                } else {
                    result.getOrThrow().toString()
                }
            }
            is Command.Delete -> ""
            is Command.Get -> {
                if (result.isFailure) {
                    "key not set"
                } else {
                    result.getOrThrow().toString()
                }
            }
            is Command.Set -> ""
        }
    }
}