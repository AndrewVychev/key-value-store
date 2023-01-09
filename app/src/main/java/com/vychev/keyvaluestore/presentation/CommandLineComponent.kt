package com.vychev.keyvaluestore.presentation

import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.vychev.keyvaluestore.state.CommandLineUiState

@Composable
fun CommandLineComponent(
    modifier: Modifier,
    state: CommandLineUiState,
    onCommandEntered: () -> Unit,
    onValueChanged: (String) -> Unit
) {
    TextField(
        modifier = modifier
            .background(Color.Black)
            .onKeyEvent { keyEvent ->
                if (keyEvent.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                    onCommandEntered()
                }
                false
            },
        value = state.currentCommand,
        onValueChange = {
            if (it.lastOrNull() != '\n') {
                onValueChanged(it)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Send
        ),
        enabled = state.inputEnabled,
        keyboardActions = KeyboardActions(onSend = { onCommandEntered() })
    )
}