package com.funnypaper.simme.ui.screens.mapeditor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.funnypaper.simme.ui.navigation.SIMMENavigationWrapper
import com.funnypaper.simme.ui.theme.SIMMETheme

@Composable
fun MapEditorScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
) {
    SIMMENavigationWrapper(
        title = {
            Text(text = stringResource(id = it.routeTitleRes))
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null
                )
            }
        },
        navHostController = navHostController,
        modifier = modifier
    ) {
        MapEditorBody(modifier = Modifier.fillMaxSize())
    }
}

@Preview
@Composable
fun MapEditorScreenPreview() {
    SIMMETheme {
        Surface {
            MapEditorScreen()
        }
    }
}