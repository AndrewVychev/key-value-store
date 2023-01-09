package com.vychev.keyvaluestore.mapper

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.vychev.keyvaluestore.domain.entity.Command
import javax.inject.Inject

class CommandMapper @Inject constructor() {

    fun map(command: String?): Command {
        command ?: throw NullPointerException()
        val splits = command.split(" ")
        return when (splits[0].toUpperCase(Locale.current)) {
            "SET" -> Command.Set(splits[1], splits[2])
            "GET" -> Command.Get(splits[1])
            "DELETE" -> Command.Delete(splits[1])
            "COUNT" -> Command.Count(splits[1])
            "BEGIN" -> Command.BeginTransaction()
            "COMMIT" -> Command.CommitTransaction()
            "ROLLBACK" -> Command.RollbackTransaction()
            else -> throw IllegalArgumentException(
                "Command ${splits[0]} is not supported"
            )
        }
    }
}