package com.funnypaper.simme.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.funnypaper.simme.ui.navigation.SIMMENavDestinationLeaf
import com.funnypaper.simme.ui.navigation.SIMMENavDestinationNode
import com.funnypaper.simme.ui.screens.globalproperties.GlobalPropertiesScreen
import com.funnypaper.simme.ui.screens.mapeditor.MapEditorScreen
import com.funnypaper.simme.ui.screens.mapsimulation.MapSimulationScreen

fun NavGraphBuilder.projectGraph(
    navHostController: NavHostController
) {
    navigation(
        route = SIMMENavDestinationNode.ProjectDestination.routeWithArgs,
        startDestination = SIMMENavDestinationLeaf.GlobalPropertiesDestination.route,
        arguments = SIMMENavDestinationNode.ProjectDestination.navArgs
    ) {
        composable(route = SIMMENavDestinationLeaf.GlobalPropertiesDestination.route) {
            GlobalPropertiesScreen(
                navHostController = navHostController
            )
        }

        composable(route = SIMMENavDestinationLeaf.MapEditorDestination.route) {
            MapEditorScreen(
                navHostController = navHostController
            )
        }

        composable(route = SIMMENavDestinationLeaf.MapSimulationDestination.route) {
            MapSimulationScreen(
                navHostController = navHostController
            )
        }
    }
}