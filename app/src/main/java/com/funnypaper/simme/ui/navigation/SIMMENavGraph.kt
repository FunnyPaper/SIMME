package com.funnypaper.simme.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.funnypaper.simme.ui.screens.projectlist.ProjectListDestination
import com.funnypaper.simme.ui.screens.projectlist.ProjectListScreen

@Composable
fun SIMMENavHost(
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navHostController,
        startDestination = ProjectListDestination.route
    ) {
        composable(route = ProjectListDestination.route) {
            //ProjectListScreen(widthSizeClass = widthSizeClass)
        }
    }
}