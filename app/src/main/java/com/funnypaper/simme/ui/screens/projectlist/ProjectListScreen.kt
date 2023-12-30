package com.funnypaper.simme.ui.screens.projectlist

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.RemoveDone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.funnypaper.simme.R
import com.funnypaper.simme.ui.navigation.SIMMENavDestination
import com.funnypaper.simme.ui.theme.SIMMETheme

object ProjectListDestination : SIMMENavDestination {
    override val route = "ProjectList"
    override val routeTitleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectListScreen(
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = { ProjectListFloatingActionButton() }
    ) {
        when (widthSizeClass) {
            WindowWidthSizeClass.Compact -> ProjectListScreenCompact(
                item = null,
                items = emptyList(),
                modifier = Modifier.padding(it)
            )

            else -> ProjectListScreenExpanded(
                item = null,
                items = emptyList(),
                modifier = Modifier.padding(it)
            )
        }
    }
}

@Composable
private fun ProjectListScreenCompact(
    item: ProjectItemDetailsUIState?,
    items: List<ProjectItemUIState>,
    modifier: Modifier = Modifier,
) {
    if (item != null) {
        ProjectListItemDetails(
            item = item,
            isFullScreen = true,
            modifier = modifier.fillMaxSize()
        )
    } else {
        ProjectList(
            items = items,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
private fun ProjectListScreenExpanded(
    item: ProjectItemDetailsUIState?,
    items: List<ProjectItemUIState>,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.pane_spacing)
        ),
        modifier = modifier
    ) {
        ProjectList(
            items = items,
            modifier = Modifier.fillMaxWidth(.7f)
        )
        item?.let {
            ProjectListItemDetails(
                item = it,
                isFullScreen = true,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ProjectListFloatingActionButton(
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .horizontalScroll(rememberScrollState())
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddBox, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddBox, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddBox, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddBox, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddBox, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddBox, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddBox, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.AddBox, contentDescription = null)
            }
        }

        Column(
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Remove, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.RemoveCircle, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.RemoveDone, contentDescription = null)
            }
        }

        SmallFloatingActionButton(
            onClick = { expanded = !expanded },
            modifier = modifier.align(Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
        }
    }
}

@Preview(device = "id:pixel_5")
@Composable
fun ProjectListScreenCompactPreview() {
    SIMMETheme {
        Surface {
            ProjectListScreen(
                widthSizeClass = WindowWidthSizeClass.Compact
            )
        }
    }
}

@Preview(device = "spec:parent=Nexus 7 2013,orientation=landscape")
@Composable
fun ProjectListScreenExpandedPreview() {
    SIMMETheme {
        Surface {
            ProjectListScreen(
                widthSizeClass = WindowWidthSizeClass.Expanded
            )
        }
    }
}