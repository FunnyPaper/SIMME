package com.funnypaper.simme.ui.screens.projectlist

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.funnypaper.simme.R
import com.funnypaper.simme.ui.theme.SIMMETheme

@Composable
fun ProjectList(
    selectedItem: ProjectItemUIState?,
    items: List<ProjectItemUIState>,
    modifier: Modifier = Modifier,
    onListItemClick: (ProjectItemUIState) -> Unit = {},
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.list_item_spacing)
        ),
        contentPadding = PaddingValues(
            dimensionResource(id = R.dimen.list_item_spacing)
        ),
        modifier = modifier
    ) {
        item {
            ProjectListTopBar()
            Divider()
        }

        items(items, { it.id }) {
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally(
                    animationSpec = tween(500, 0, EaseInOutCubic),
                    initialOffsetX = { -100 }
                ),
            ) {
                ProjectListItem(
                    item = it,
                    selected = selectedItem == it,
                    onListItemClick = onListItemClick
                )
            }
        }
    }
}

@Composable
fun ProjectListTopBar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = "Projects",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(device = "id:pixel_5")
@Composable
fun ProjectListPreview() {
    SIMMETheme {
        Surface {
            ProjectList(
                selectedItem = null,
                items = List(5) {
                    ProjectItemUIState(it, Uri.EMPTY, "project_$it")
                }
            )
        }
    }
}