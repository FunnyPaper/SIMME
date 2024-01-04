package com.funnypaper.simme.ui.screens.projectlist

import android.net.Uri
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.funnypaper.simme.R
import com.funnypaper.simme.ui.theme.SIMMETheme
import com.funnypaper.simme.ui.theme.drawable

private object AnimationLabels {
    const val CARD = "outline animation"
    const val OUTLINE = "outline animation"
    const val CONTAINER_COLOR = "container color animation"
    const val CONTENT_COLOR = "content color animation"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectListItem(
    item: ProjectItemUIState,
    selected: Boolean,
    onListItemClick: (ProjectItemUIState) -> Unit,
    modifier: Modifier = Modifier,
) {
    val transition = updateTransition(
        label = AnimationLabels.CARD,
        targetState = selected
    )

    val outlineWidthTransition by transition.animateDp(
        label = AnimationLabels.OUTLINE,
        transitionSpec = { tween(1000, easing = EaseInOutCubic) },
        targetValueByState = {
            if (it) 2.dp else 0.dp
        }
    )

    val containerColorTransition by transition.animateColor(
        label = AnimationLabels.CONTAINER_COLOR,
        transitionSpec = { tween(1000, easing = EaseInOutCubic) },
        targetValueByState = {
            if (it) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
        }
    )

    val contentColorTransition by transition.animateColor(
        label = AnimationLabels.CONTENT_COLOR,
        transitionSpec = { tween(1000, easing = EaseInOutCubic) },
        targetValueByState = {
            if (it) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
        }
    )

    Card(
        onClick = { onListItemClick(item) },
        colors = CardDefaults.cardColors(
            containerColor = containerColorTransition,
            contentColor = contentColorTransition
        ),
        border = if (outlineWidthTransition > 0.dp)
            BorderStroke(outlineWidthTransition, MaterialTheme.colorScheme.primary)
        else
            null,
        modifier = modifier
            .height(dimensionResource(id = R.dimen.list_item_height))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.card_spacing)
                ),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.thumbnailUri.takeUnless { it == Uri.EMPTY }
                            ?: MaterialTheme.drawable.defaultProjectThumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(id = R.string.project_image),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = MaterialTheme.drawable.loading),
                    error = painterResource(id = MaterialTheme.drawable.error),
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                )

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.labelLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview
@Composable
fun ProjectListItemPreview() {
    SIMMETheme {
        Surface {
            var selected by remember {
                mutableStateOf(false)
            }

            ProjectListItem(
                item = ProjectItemUIState(
                    id = 0,
                    thumbnailUri = Uri.EMPTY,
                    title = "project_title"
                ),
                selected = selected,
                onListItemClick = { selected = !selected }
            )
        }
    }
}