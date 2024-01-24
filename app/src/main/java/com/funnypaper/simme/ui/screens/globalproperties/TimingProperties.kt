package com.funnypaper.simme.ui.screens.globalproperties

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.funnypaper.simme.R
import com.funnypaper.simme.domain.extensions.formatMillis
import com.funnypaper.simme.domain.extensions.toMillis
import com.funnypaper.simme.domain.utility.format.TimeFormat
import com.funnypaper.simme.ui.shared.field.NumberField
import com.funnypaper.simme.ui.shared.field.TimeField
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimingProperties(
    modifier: Modifier = Modifier,
    timingViewModel: TimingPropertiesViewModel = hiltViewModel<TimingPropertiesViewModel>(),
) {
    val timingState by timingViewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        when (val timing = timingState) {
            is TimingUIState.Loading -> {
                Text(text = stringResource(id = R.string.loading_timing_tip))
            }

            is TimingUIState.Loaded -> {
                var bpm by rememberSaveable {
                    mutableStateOf(timing.bpm.toString())
                }
                var isBpmValid by rememberSaveable {
                    mutableStateOf(true)
                }

                var duration by rememberSaveable {
                    mutableStateOf(timing.millis.formatMillis())
                }
                var isDurationValid by rememberSaveable {
                    mutableStateOf(true)
                }

                var offset by rememberSaveable {
                    mutableStateOf(timing.offset.formatMillis())
                }
                var isOffsetValid by rememberSaveable {
                    mutableStateOf(true)
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(.8f)
                ) {
                    NumberField(
                        value = bpm,
                        onValueChange = {
                            bpm = it.input
                            isBpmValid = it.isValid
                        },
                        label = { Text(text = stringResource(id = R.string.timing_bpm_label)) },
                        imeAction = ImeAction.Next
                    )
                    TimeField(
                        value = duration,
                        onValueChange = {
                            duration = it.input
                            isDurationValid = it.isValid
                        },
                        label = { Text(text = stringResource(id = R.string.timing_duration_label)) },
                        imeAction = ImeAction.Next
                    )
                    TimeField(
                        value = offset,
                        onValueChange = {
                            offset = it.input
                            isOffsetValid = it.isValid
                        },
                        label = { Text(text = stringResource(id = R.string.timing_offset_label)) }
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    val dur = LocalTime.parse(
                                        duration,
                                        DateTimeFormatter.ofPattern(TimeFormat.Full.pattern)
                                    ).toMillis()
                                    val off = LocalTime.parse(
                                        offset,
                                        DateTimeFormatter.ofPattern(TimeFormat.Full.pattern)
                                    ).toMillis()
                                    timingViewModel.updateTiming(bpm.toInt(), dur, off)
                                }
                            },
                            enabled = isBpmValid && isDurationValid && isOffsetValid
                        ) {
                            Text(text = stringResource(id = R.string.save_button))
                        }
                        Button(
                            onClick = {
                                bpm = timing.bpm.toString()
                                isBpmValid = true
                                duration = timing.millis.formatMillis()
                                isDurationValid = true
                                offset = timing.offset.formatMillis()
                                isOffsetValid = true
                            }
                        ) {
                            Text(text = stringResource(id = R.string.discard_button))
                        }
                    }
                }
            }
        }
    }
}