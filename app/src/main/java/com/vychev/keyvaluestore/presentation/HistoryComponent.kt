package com.vychev.keyvaluestore.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HistoryComponent(modifier: Modifier, text: String) {
    Text(
        modifier = modifier
            .padding(16.dp),
        text = text
    )
}