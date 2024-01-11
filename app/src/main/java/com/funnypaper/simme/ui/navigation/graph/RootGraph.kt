package com.funnypaper.simme.ui.navigation.graph

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.funnypaper.simme.ui.navigation.SIMMENavDestinationLeaf.ProjectListDestination
import com.funnypaper.simme.ui.navigation.SIMMENavDestinationNode.ProjectDestination
import com.funnypaper.simme.ui.screens.projectlist.ProjectListScreen

@Composable
fun SIMMENavHost(
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = ProjectListDestination.route,
        modifier = modifier
    ) {
        composable(route = ProjectListDestination.route) {
            ProjectListScreen(
                onEditProject = {
                    navHostController.navigate(ProjectDestination.constructRoute(it))
                },
                navHostController = navHostController,
                widthSizeClass = widthSizeClass
            )
        }

        projectGraph(navHostController = navHostController)
    }
}