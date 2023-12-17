package com.funnypaper.simme.ui.screens.projectlist

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.funnypaper.simme.R

@Composable
fun ProjectList(
    onExportButtonClick: (Int) -> Unit,
    onDiscardButtonClick: (Int) -> Unit,
    onListItemClick: (Int) -> Unit,
    items: List<ProjectItemUIState>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.list_item_spacing)
        ),
        modifier = modifier
    ) {
        items(items, { it.id }) {
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally(
                    animationSpec = tween(500, 0, EaseInOutCubic),
                    initialOffsetX = { -100 }
                )
            ) {
                ProjectListItem(
                    item = it,
                    onExportButtonClick = onExportButtonClick,
                    onDiscardButtonClick = onDiscardButtonClick,
                    onListItemClick = onListItemClick
                )
            }
        }
    }
}

@Preview
@Composable
fun ProjectListPreview() {
    var selected by remember {
        mutableStateOf<Int?>(null)
    }

    ProjectList(
        onExportButtonClick = {},
        onDiscardButtonClick = {},
        onListItemClick = { selected = it },
        items = List(5) {
            ProjectItemUIState(it, Uri.EMPTY, "projectc_$it", selected == it)
        }
    )
}