package com.funnypaper.simme.ui.screens.globalproperties

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.funnypaper.simme.R

@Composable
internal fun GlobalPropertiesBody(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.global_properties_title),
        )
    }
}