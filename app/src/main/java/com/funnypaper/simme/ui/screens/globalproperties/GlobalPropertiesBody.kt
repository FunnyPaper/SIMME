package com.funnypaper.simme.ui.screens.globalproperties

import androidx.compose.material3.Surface
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

internal object GlobalPropertiesDestination {
    const val PROPERTIES = "Properties"
    const val AUDIO = "Audio"
    const val TIMING = "Timing"
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
        composable(GlobalPropertiesDestination.PROPERTIES) {
            GlobalPropertiesList {
                navHostController.navigate(it)
            }
        }

        composable(GlobalPropertiesDestination.TIMING) {
            TimingProperties()
        }

        composable(GlobalPropertiesDestination.AUDIO) {
            AudioProperties()
        }
    }
}