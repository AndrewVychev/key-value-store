package com.vychev.keyvaluestore.state

/**
 * ViewModel state
 */
data class CommandLineState(
    val history: List<HistoryState> = emptyList(),
    val currentCommand: String = "",
    val isLoading: Boolean = false
) {

    fun addLine(line: String, isCommand: Boolean): CommandLineState {
        return copy(
            history = history.plusElement(
                HistoryState(
                    value = line,
                    isCommand = isCommand
                )
            ),
            currentCommand = "",
            isLoading = isCommand
        )
    }

    fun clear(): CommandLineState {
        return copy(
            history = emptyList(),
            currentCommand = "",
            isLoading = false
        )
    }
}

data class HistoryState(
    val value: String,
    val isCommand: Boolean
)
