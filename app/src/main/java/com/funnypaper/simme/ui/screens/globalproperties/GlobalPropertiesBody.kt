package com.funnypaper.simme.ui.screens.globalproperties

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.funnypaper.simme.ui.theme.SIMMETheme

@Composable
internal fun GlobalPropertiesBody(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
) {
    GlobalPropertiesBodyCompact(
        navHostController = navHostController,
        modifier = modifier
    )
}

@Preview
@Composable
private fun GlobalPropertiesBodyPreview() {
    SIMMETheme {
        Surface {
            GlobalPropertiesBody()
        }
    }
}

@Composable
private fun GlobalPropertiesBodyCompact(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
) {
    NavHost(
        startDestination = "Properties",
        navController = navHostController,
        modifier = modifier
    ) {
        composable("Properties") {
            GlobalPropertiesList {
                navHostController.navigate(it)
            }
        }

        composable("Audio") {
            AudioProperties()
        }

        composable("Header") {
            Text(text = "Header")
        }
    }
}