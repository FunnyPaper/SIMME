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
import com.funnypaper.simme.domain.utility.validator.RegexValidator
import com.funnypaper.simme.domain.utility.validator.ValidationResult
import com.funnypaper.simme.domain.utility.validator.validateWith
import com.funnypaper.simme.ui.theme.SIMMETheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberField(
    value: String,
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit = {},
    onValueChange: (ValidationResult<String>) -> Unit = {},
    imeAction: ImeAction = ImeAction.Done,
) {
    val validator = remember {
        RegexValidator<String>(
            regex = Regex("""^\d+$"""),
            pattern = "d+",
            patternTip = "Min number length is 1"
        )
    }

    val validationResult = remember(value) {
        value validateWith validator
    }

    OutlinedTextField(
        label = label,
        placeholder = {
            Text(text = validator.pattern)
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
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        modifier = modifier
    )
}

@Preview
@Composable
private fun NumberFieldPreview() {
    SIMMETheme {
        Surface {
            NumberField(
                value = "",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}