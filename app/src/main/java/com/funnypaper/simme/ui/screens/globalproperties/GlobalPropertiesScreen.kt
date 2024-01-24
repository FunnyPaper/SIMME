package com.funnypaper.simme.ui.screens.globalproperties

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.funnypaper.simme.ui.navigation.SIMMENavigationWrapper
import com.funnypaper.simme.ui.theme.SIMMETheme

@Composable
fun GlobalPropertiesScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
) {
    val childNavHostController = rememberNavController()
    val currentBackStackEntry by childNavHostController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    SIMMENavigationWrapper(
        title = {
            Text(text = currentRoute.toString())
        },
        navigationIcon = {
            IconButton(onClick = { childNavHostController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null
                )
            }
        },
        isNavigationRoot = currentRoute == GlobalPropertiesDestination.PROPERTIES,
        navHostController = navHostController,
        modifier = modifier
    ) {
        GlobalPropertiesBody(
            navHostController = childNavHostController,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
fun GlobalPropertiesScreenPreview() {
    SIMMETheme {
        Surface {
            GlobalPropertiesScreen()
        }
    }
}