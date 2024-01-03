package com.funnypaper.simme.ui.screens.projectlist

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.funnypaper.simme.R
import com.funnypaper.simme.ui.navigation.SIMMENavDestination
import com.funnypaper.simme.ui.shared.fab.CornerFloatingActionButton
import com.funnypaper.simme.ui.theme.SIMMETheme

object ProjectListDestination : SIMMENavDestination {
    override val route = "ProjectList"
    override val routeTitleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectListScreen(
    onEditProject: (Int) -> Unit,
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    projectListViewModel: ProjectListViewModel = hiltViewModel(),
) {
    val listUIState by projectListViewModel.listUIState.collectAsState()
    val detailsUIState by projectListViewModel.detailsUIState.collectAsState()

    val exportLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/json")) {
            it?.let {
                projectListViewModel.exportProject(detailsUIState!!.id, it)
            }
        }

    val importLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        it?.let {
            projectListViewModel.importProject(it)
        }
    }

    var openAlertDialog by remember { mutableStateOf(false) }
    if(openAlertDialog) {
        AlertDialog(
            icon = { Icon(imageVector = Icons.Filled.DeleteForever, contentDescription = null) },
            title = { Text(text = stringResource(id = R.string.dialog_delete_project_title, detailsUIState!!.title))},
            text = { Text(text = stringResource(id = R.string.dialog_delete_project_text, detailsUIState!!.title)) },
            onDismissRequest = { openAlertDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    projectListViewModel.deleteProject(detailsUIState!!.id)
                    openAlertDialog = false
                }) {
                    Text(text = stringResource(id = R.string.dialog_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { openAlertDialog = false }) {
                    Text(text = stringResource(id = R.string.dialog_dismiss))
                }
            }
        )
    }

    Scaffold(
        modifier = modifier.padding(8.dp),
        floatingActionButton = {
            CornerFloatingActionButton(
                horizontalButtons = detailsUIState?.let {
                    listOf(
                        {
                            IconButton(onClick = { exportLauncher.launch(it.title) }) {
                                Icon(imageVector = Icons.Filled.Upload, contentDescription = null)
                            }
                        },
                        {
                            IconButton(onClick = { projectListViewModel.duplicateProject(it.id) }) {
                                Icon(imageVector = Icons.Filled.FileCopy, contentDescription = null)
                            }
                        },
                        {
                            IconButton(onClick = { openAlertDialog = true }) {
                                Icon(
                                    imageVector = Icons.Filled.DeleteForever,
                                    contentDescription = null
                                )
                            }
                        },
                        {
                            IconButton(onClick = { onEditProject(it.id) }) {
                                Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
                            }
                        },
                    )
                } ?: emptyList(),
                verticalButtons = listOf(
                    {
                        IconButton(onClick = { projectListViewModel.createProject() }) {
                            Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
                        }
                    },
                    {
                        IconButton(onClick = { importLauncher.launch(arrayOf("application/json")) }) {
                            Icon(imageVector = Icons.Filled.SaveAlt, contentDescription = null)
                        }
                    },
                )
            )
        }
    ) {
        when (widthSizeClass) {
            WindowWidthSizeClass.Compact -> ProjectListScreenCompact(
                item = detailsUIState,
                items = listUIState,
                onBackPressed = { projectListViewModel.previewProject(null) },
                onListItemClick = { projectListViewModel.previewProject(it) },
                modifier = Modifier.padding(it)
            )

            else -> ProjectListScreenExpanded(
                item = detailsUIState,
                items = listUIState,
                onBackPressed = { projectListViewModel.previewProject(null) },
                onListItemClick = { projectListViewModel.previewProject(it) },
                modifier = Modifier.padding(it)
            )
        }
    }
}

@Composable
private fun ProjectListScreenCompact(
    onListItemClick: (Int) -> Unit,
    onBackPressed: () -> Unit,
    item: ProjectItemDetailsUIState?,
    items: List<ProjectItemUIState>,
    modifier: Modifier = Modifier,
) {
    if (item != null) {
        ProjectListItemDetails(
            item = item,
            isFullScreen = true,
            onBackPressed = onBackPressed,
            modifier = modifier.fillMaxSize()
        )
    } else {
        ProjectList(
            items = items,
            onListItemClick = onListItemClick,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
private fun ProjectListScreenExpanded(
    onListItemClick: (Int) -> Unit,
    onBackPressed: () -> Unit,
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
            onListItemClick = onListItemClick,
            modifier = Modifier.fillMaxWidth(.6f)
        )
        item?.let {
            ProjectListItemDetails(
                item = it,
                onBackPressed = onBackPressed,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(device = "id:pixel_5")
@Composable
fun ProjectListScreenCompactPreview() {
    SIMMETheme {
        Surface {
            ProjectListScreen(
                onEditProject = {},
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
                onEditProject = {},
                widthSizeClass = WindowWidthSizeClass.Expanded
            )
        }
    }
}