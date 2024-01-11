package com.funnypaper.simme.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.funnypaper.simme.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SIMMEDrawerContent(
    drawerState: DrawerState,
    currentDestination: SIMMENavDestination,
    destinationGroups: List<List<SIMMENavDestinationLeaf>>,
    modifier: Modifier = Modifier,
    onDestinationClick: (SIMMENavDestinationLeaf) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()

    ModalDrawerSheet(
        drawerShape = RectangleShape,
        modifier = modifier.width(260.dp)
    ) {
        Surface {
            Column {
                Box(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.headlineLarge
                    )
                }

                destinationGroups.forEach { group ->
                    Divider()
                    group.forEach {
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    imageVector = it.icon,
                                    contentDescription = null
                                )
                            },
                            shape = RectangleShape,
                            label = {
                                Text(text = stringResource(id = it.routeTitleRes))
                            },
                            selected = currentDestination == it,
                            onClick = {
                                if (currentDestination == it) {
                                    return@NavigationDrawerItem
                                }

                                coroutineScope.launch {
                                    drawerState.close()
                                    onDestinationClick(it)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SIMMENavigationWrapper(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    title: @Composable (SIMMENavDestinationLeaf) -> Unit = {},
    isNavigationRoot: Boolean = true,
    navigationIcon: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit = {},
) {
    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val currentBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination: SIMMENavDestinationLeaf = remember(currentBackStackEntry) {
        currentBackStackEntry.routeToSIMMEDestination()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SIMMEDrawerContent(
                drawerState = drawerState,
                destinationGroups = SIMMENavDestination.routing(currentDestination),
                currentDestination = currentDestination,
                onDestinationClick = {
                    navHostController.navigate(it.constructRoute()) {
                        popUpTo(-1) { inclusive = true }
                    }
                }
            )
        },
        modifier = modifier
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { title(currentDestination) },
                    navigationIcon = {
                        if (isNavigationRoot) {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Menu,
                                    contentDescription = null
                                )
                            }
                        } else {
                            navigationIcon()
                        }
                    }
                )
            },
            floatingActionButton = { floatingActionButton() }
        ) {
            content(it)
        }
    }
}