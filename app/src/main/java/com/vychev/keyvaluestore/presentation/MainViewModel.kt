package com.vychev.keyvaluestore.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vychev.keyvaluestore.mapper.CommandMapper
import com.vychev.keyvaluestore.state.CommandLineState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val commandExecutorManager: CommandExecutorManager,
    private val commandMapper: CommandMapper
) : ViewModel() {

    private val _commandLineState = MutableStateFlow(CommandLineState())
    private val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
        throwable.message?.let { message ->
            _commandLineState.update { state ->
                state.addLine(message, false)
            }
        }
        Log.e(null, "Something went wrong", throwable)
    }

    val commandLineState = _commandLineState.asStateFlow()

    fun onValueChanged(value: String) {
        _commandLineState.update { it.copy(currentCommand = value) }
    }

    fun onClearClicked() {
        _commandLineState.update { it.clear() }
        commandExecutorManager.clear()
    }

    fun onCommandEntered() = viewModelScope.launch(coroutineExceptionHandler) {
        val commandText = _commandLineState.value.currentCommand
        // Add command to the history
        _commandLineState.update { it.addLine(commandText, true) }
        // Map command into model
        val command = commandMapper.map(commandText)
        // Execute command
        val result = commandExecutorManager.execute(command)
        // Add result to the history
        _commandLineState.update { it.addLine(result, false) }
    }
}