package com.funnypaper.simme.ui.screens.projectlist

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material3.Card
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.funnypaper.simme.R

@Composable
fun ProjectListItemDetails(
    item: ProjectItemDetailsUIState,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
    ) {
        val pendingPainter = rememberVectorPainter(image = Icons.Filled.Pending)
        val errorPainter = rememberVectorPainter(image = Icons.Filled.BrokenImage)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.thumbnailUri)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(id = R.string.project_image),
            contentScale = ContentScale.Crop,
            placeholder = pendingPainter,
            error = errorPainter,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
    }
}

@Preview
@Composable
fun ProjectListItemDetailsPreview() {
    //ProjectListItemDetails()
}