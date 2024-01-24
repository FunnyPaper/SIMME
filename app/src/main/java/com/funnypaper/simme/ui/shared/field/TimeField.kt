package com.funnypaper.simme.ui.shared.field

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.funnypaper.simme.domain.utility.format.TimeFormat
import com.funnypaper.simme.domain.utility.validator.TimeValidator
import com.funnypaper.simme.domain.utility.validator.ValidationResult
import com.funnypaper.simme.domain.utility.validator.validateWith
import com.funnypaper.simme.ui.theme.SIMMETheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (ValidationResult<String>) -> Unit = {},
    label: @Composable () -> Unit = {},
    timeFormat: TimeFormat = TimeFormat.Full,
    imeAction: ImeAction = ImeAction.Done,
) {
    val validator = remember {
        TimeValidator(
            timeFormat.regex,
            timeFormat.pattern,
            timeFormat.patternTip
        )
    }

    val validationResult = remember(value) {
        value validateWith validator
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
        value = value,
        onValueChange = {
            onValueChange(it validateWith validator)
        },
        isError = !validationResult.isValid,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Ascii,
            imeAction = imeAction
        ),
        modifier = modifier
    )
}

@Preview
@Composable
private fun TimeFieldPreview() {
    SIMMETheme {
        Surface {
            TimeField(
                value = "",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}