package com.funnypaper.simme.ui.screens.globalproperties

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.funnypaper.simme.R
import com.funnypaper.simme.ui.theme.SIMMETheme

@Composable
fun GlobalPropertiesList(
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.list_item_spacing)
        ),
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        GlobalPropertiesListItem(
            onClick = { onItemClick(GlobalPropertiesDestination.TIMING) }
        ) {
            Text(text = GlobalPropertiesDestination.TIMING)
        }

        GlobalPropertiesListItem(
            onClick = { onItemClick(GlobalPropertiesDestination.AUDIO) }
        ) {
            Text(text = GlobalPropertiesDestination.AUDIO)
        }
    }
}

@Preview
@Composable
private fun GlobalPropertiesListPreview() {
    SIMMETheme {
        Surface {
            GlobalPropertiesList()
        }
    }
}

@Composable
private fun GlobalPropertiesListItem(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
    label: @Composable () -> Unit = {},
) {
    val color by rememberUpdatedState(
        newValue = if (selected)
            MaterialTheme.colorScheme.secondaryContainer
        else
            MaterialTheme.colorScheme.surfaceVariant
    )

    CompositionLocalProvider(
        LocalContentColor provides contentColorFor(color)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = color,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onClick() }
                .padding(8.dp)
        ) {
            label()
        }
    }
}