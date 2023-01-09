package com.vychev.keyvaluestore.mapper

import com.vychev.keyvaluestore.state.CommandLineState
import com.vychev.keyvaluestore.state.CommandLineUiState
import javax.inject.Inject

private const val START_SYMBOL = "> "

/**
 * Map from ViewModel state to the ui state
 */
class CommandLineUiStateMapper @Inject constructor(){

    fun map(state: CommandLineState): CommandLineUiState {
        val commands = if (state.history.isEmpty()) {
            START_SYMBOL
        } else {
            state.history.map { state ->
                if (state.isCommand) {
                    "$START_SYMBOL${state.value}"
                } else {
                    state.value
                }
            }.filter { it.isNotEmpty() }
                .joinToString("\n")
        }
        return CommandLineUiState(
            history = commands,
            inputEnabled = !state.isLoading,
            currentCommand = state.currentCommand
        )
    }
}