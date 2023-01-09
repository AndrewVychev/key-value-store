package com.vychev.keyvaluestore.state

/**
 * Model with information only to show smth on the ui
 */
data class CommandLineUiState(
    val history: String = "",
    val currentCommand: String,
    val inputEnabled: Boolean = true
)