package com.funnypaper.simme.ui.shared.field

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.funnypaper.simme.domain.extensions.toMillis
import com.funnypaper.simme.domain.utility.format.TimeFormat
import com.funnypaper.simme.domain.utility.validator.TimeValidator
import com.funnypaper.simme.domain.utility.validator.validateWith
import com.funnypaper.simme.ui.theme.SIMMETheme
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeField(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit = {},
    timeFormat: TimeFormat = TimeFormat.Full,
    onSubmit: (Long) -> Unit = {},
    imeAction: ImeAction = ImeAction.Done,
) {
    var input by rememberSaveable {
        mutableStateOf(timeFormat.placeholder)
    }

    val validator = remember {
        TimeValidator(
            timeFormat.regex,
            timeFormat.pattern,
            timeFormat.patternTip
        )
    }

    val validationResult = remember(input) {
        input validateWith validator
    }

    OutlinedTextField(
        label = label,
        placeholder = {
            Text(text = timeFormat.pattern)
        },
        supportingText = {
            Column(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                validationResult.errors.forEach { error -> Text(text = error.message) }
            }
        },
        value = input,
        onValueChange = { input = it },
        isError = !validationResult.isValid,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Ascii,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions {
            if (validationResult.isValid) {
                val date = LocalTime.parse(input, DateTimeFormatter.ofPattern(timeFormat.pattern))
                onSubmit(date.toMillis())
            }
        },
        modifier = modifier
    )
}

@Preview
@Composable
fun TimeFieldPreview() {
    SIMMETheme {
        Surface {
            TimeField(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}