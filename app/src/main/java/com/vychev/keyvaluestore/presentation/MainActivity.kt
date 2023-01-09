package com.vychev.keyvaluestore.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vychev.keyvaluestore.R
import com.vychev.keyvaluestore.mapper.CommandLineUiStateMapper
import com.vychev.keyvaluestore.ui.theme.KeyValueStoreTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mapper: CommandLineUiStateMapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeyValueStoreTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel = viewModel<MainViewModel>()
                    val state = mapper.map(viewModel.commandLineState.collectAsState().value)
                    Column(modifier = Modifier.fillMaxSize()) {
                        HistoryComponent(modifier = Modifier.weight(1f), text = state.history)
                        Row {
                            CommandLineComponent(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp),
                                state = state,
                                onCommandEntered = viewModel::onCommandEntered,
                                onValueChanged = viewModel::onValueChanged
                            )
                            Button(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                onClick = { viewModel.onCommandEntered() }) {
                                Text(text = stringResource(R.string.done))
                            }
                            Button(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                onClick = { viewModel.onClearClicked() }) {
                                Text(text = stringResource(R.string.clear))
                            }
                        }
                    }
                }
            }
        }
    }
}