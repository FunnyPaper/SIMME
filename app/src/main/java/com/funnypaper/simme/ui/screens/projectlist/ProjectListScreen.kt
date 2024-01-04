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
    if(openAlertDialog && detailsUIState != null) {
        ProjectListScreenAlertDialog(
            title = detailsUIState!!.title,
            onDismiss = { openAlertDialog = false },
            onConfirm = {
                projectListViewModel.deleteProject(detailsUIState!!.id)
                openAlertDialog = false
            },
        )
    }

    Scaffold(
        modifier = modifier.padding(8.dp),
        floatingActionButton = {
            ProjectListScreenActionButton(
                selectedProject = detailsUIState,
                onEditProject = onEditProject,
                onExportProject = { exportLauncher.launch(it) },
                onDuplicateProject = { projectListViewModel.duplicateProject(it) },
                onDeleteProject = { openAlertDialog = true },
                onCreateProject = { projectListViewModel.createProject() },
                onImportProject = { importLauncher.launch(arrayOf("application/json"))}
            )
        }
    ) {
        when (widthSizeClass) {
            WindowWidthSizeClass.Compact -> ProjectListScreenCompact(
                item = detailsUIState,
                items = listUIState,
                onBackPressed = { projectListViewModel.previewProject(null) },
                onListItemClick = { projectListViewModel.previewProject(it.id) },
                modifier = Modifier.padding(it)
            )

            else -> ProjectListScreenExpanded(
                item = detailsUIState,
                items = listUIState,
                onBackPressed = { projectListViewModel.previewProject(null) },
                onListItemClick = { projectListViewModel.previewProject(it.id) },
                modifier = Modifier.padding(it)
            )
        }
    }
}

@Composable
private fun ProjectListScreenActionButton(
    selectedProject: ProjectItemDetailsUIState?,
    onEditProject: (Int) -> Unit,
    onExportProject: (String) -> Unit,
    onDuplicateProject: (Int) -> Unit,
    onDeleteProject: (Int) -> Unit,
    onCreateProject: () -> Unit,
    onImportProject: () -> Unit
) {
    CornerFloatingActionButton(
        horizontalButtons = selectedProject?.let {
            listOf(
                {
                    IconButton(onClick = { onExportProject(it.title) }) {
                        Icon(imageVector = Icons.Filled.Upload, contentDescription = null)
                    }
                },
                {
                    IconButton(onClick = { onDuplicateProject(it.id) }) {
                        Icon(imageVector = Icons.Filled.FileCopy, contentDescription = null)
                    }
                },
                {
                    IconButton(onClick = { onDeleteProject(it.id) }) {
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
                IconButton(onClick = { onCreateProject() }) {
                    Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
                }
            },
            {
                IconButton(onClick = { onImportProject() }) {
                    Icon(imageVector = Icons.Filled.SaveAlt, contentDescription = null)
                }
            },
        )
    )
}

@Composable
fun ProjectListScreenAlertDialog(
    title: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        icon = { Icon(imageVector = Icons.Filled.DeleteForever, contentDescription = null) },
        title = { Text(text = stringResource(id = R.string.dialog_delete_project_title, title))},
        text = { Text(text = stringResource(id = R.string.dialog_delete_project_text, title)) },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(id = R.string.dialog_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.dialog_dismiss))
            }
        }
    )
}

@Composable
private fun ProjectListScreenCompact(
    onListItemClick: (ProjectItemUIState) -> Unit,
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
            selectedItem = null,
            items = items,
            onListItemClick = onListItemClick,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
private fun ProjectListScreenExpanded(
    onListItemClick: (ProjectItemUIState) -> Unit,
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
            selectedItem = items.find { it.id == item?.id },
            items = items,
            onListItemClick = onListItemClick,
            modifier = Modifier.fillMaxWidth(.5f)
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