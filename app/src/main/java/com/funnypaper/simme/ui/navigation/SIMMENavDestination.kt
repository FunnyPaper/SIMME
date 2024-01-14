package com.funnypaper.simme.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.ModeEdit
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material.icons.rounded.SettingsOverscan
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.funnypaper.simme.R

import com.funnypaper.simme.ui.navigation.SIMMENavDestinationLeaf.*

fun NavBackStackEntry?.routeToSIMMEDestination() =
    when (this?.destination?.route) {
        ProjectListDestination.route -> ProjectListDestination
        GlobalPropertiesDestination.route -> GlobalPropertiesDestination
        MapEditorDestination.route -> MapEditorDestination
        MapSimulationDestination.route -> MapSimulationDestination
        else -> ProjectListDestination
    }

sealed class SIMMENavDestination(
    val route: String,
    val args: List<String> = emptyList(),
) {
    val routeWithArgs: String
        get() = "$route${args.joinToString("") { "/{$it}" }}"

    open val navArgs: List<NamedNavArgument> = emptyList()
    fun constructRoute(vararg args: Any): String =
        "$route${args.joinToString("") { "/$it" }}"

    companion object {
        fun routing(from: SIMMENavDestination) = when(from) {
            is ProjectListDestination -> listOf(listOf(ProjectListDestination))
            else -> listOf(
                listOf(ProjectListDestination),
                listOf(
                    GlobalPropertiesDestination,
                    MapEditorDestination,
                    MapSimulationDestination
                )
            )
        }
    }
}

sealed class SIMMENavDestinationLeaf(
    val routeTitleRes: Int,
    val icon: ImageVector,
    route: String,
    args: List<String> = emptyList(),
) : SIMMENavDestination(route, args) {
    data object GlobalPropertiesDestination : SIMMENavDestinationLeaf(
        routeTitleRes = R.string.global_properties_title,
        icon = Icons.Rounded.SettingsOverscan,
        route = "GlobalProperties",
    )

    data object MapEditorDestination : SIMMENavDestinationLeaf(
        routeTitleRes = R.string.map_editor_title,
        icon = Icons.Rounded.ModeEdit,
        route = "MapEditor"
    )

    data object MapSimulationDestination : SIMMENavDestinationLeaf(
        routeTitleRes = R.string.map_simulation_title,
        icon = Icons.Rounded.PlayCircle,
        route = "MapSimulationDestination"
    )

    data object ProjectListDestination : SIMMENavDestinationLeaf(
        routeTitleRes = R.string.project_list_title,
        icon = Icons.Rounded.List,
        route = "ProjectList"
    )
}

sealed class SIMMENavDestinationNode(
    route: String,
    args: List<String> = emptyList(),
) : SIMMENavDestination(route, args) {
    data object ProjectDestination : SIMMENavDestinationNode(
        route = "EditorRoot",
        args = listOf("projectId")
    ) {
        override val navArgs: List<NamedNavArgument>
            get() = listOf(navArgument(args[0]) { type = NavType.IntType })
    }
}