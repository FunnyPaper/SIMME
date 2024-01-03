package com.funnypaper.simme.ui.screens.projectlist

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.funnypaper.simme.R
import com.funnypaper.simme.domain.extensions.formatMillis
import com.funnypaper.simme.domain.model.AudioFileModel
import com.funnypaper.simme.domain.model.BoardModel
import com.funnypaper.simme.domain.model.MetaDataModel
import com.funnypaper.simme.domain.model.PointModel
import com.funnypaper.simme.domain.model.RankModel
import com.funnypaper.simme.domain.model.TimingModel
import com.funnypaper.simme.ui.shared.audiovisualizer.AudioVisualizer
import com.funnypaper.simme.ui.shared.rank.RankCard
import com.funnypaper.simme.ui.shared.treelist.Expander
import com.funnypaper.simme.ui.theme.SIMMETheme
import kotlin.math.min

@Composable
fun ProjectListItemDetails(
    item: ProjectItemDetailsUIState,
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false,
    onBackPressed: () -> Unit = {},
) {
    val container = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .18f)
    val content = contentColorFor(backgroundColor = container)
    val spacing = dimensionResource(id = R.dimen.card_spacing)

    BackHandler {
        onBackPressed()
    }

    Column(
        modifier = modifier.padding(spacing),
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        if (isFullScreen) {
            ProjectListItemDetailsTopBar(onBackPressed = onBackPressed)
        }

        Card(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            colors = CardDefaults.cardColors(
                containerColor = container,
                contentColor = content
            )
        ) {
            DetailsHeader(
                thumbnailUri = item.thumbnailUri,
                title = item.title,
                author = item.author,
                spacing = spacing
            )

            DescriptionSection(description = item.description)
            BoardSection(board = item.board, contentColor = content, spacing = spacing)
            TimingSection(timing = item.timing, spacing = spacing)
            AudioSection(audio = item.audio, spacing = spacing)
            RanksSection(ranks = item.ranks, spacing = spacing)
            MetaDataSection(metaData = item.metaData)
        }
    }
}

@Composable
fun ProjectListItemDetailsTopBar(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = onBackPressed,
            modifier = Modifier.background(MaterialTheme.colorScheme.surface, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.navigation_back)
            )
        }
        Text(
            text = stringResource(id = R.string.screen_list_item_details),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun DescriptionSection(
    description: String,
    modifier: Modifier = Modifier,
) {
    Expander(stringResource(id = R.string.description_section), modifier = modifier) {
        Text(text = description, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun BoardSection(
    board: BoardModel,
    contentColor: Color,
    spacing: Dp,
    modifier: Modifier = Modifier,
) {
    Expander(stringResource(id = R.string.board_section), modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            BoardControl(
                board = board,
                contentColor = contentColor,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
            )
            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(spacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.CenterFocusStrong,
                        contentDescription = null
                    )
                    Text(text = "${board.origin.x}")
                    Canvas(modifier = Modifier.size(spacing)) {
                        size.toRect().let {
                            drawLine(contentColor, it.topLeft, it.bottomRight, 4f)
                            drawLine(contentColor, it.bottomLeft, it.topRight, 4f)
                        }
                    }
                    Text(text = "${board.origin.y}")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
                    Icon(
                        imageVector = Icons.Filled.Height,
                        contentDescription = null,
                        modifier = Modifier.rotate(90f)
                    )
                    Text(text = "${board.width}")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
                    Icon(imageVector = Icons.Filled.Height, contentDescription = null)
                    Text(text = "${board.height}")
                }
            }
        }
    }
}

@Composable
private fun BoardControl(
    board: BoardModel,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier
            .background(Color.White)
            .border(1.dp, contentColor)
    ) {
        size.toRect().deflate(size.height / 6f).let {
            // Fit ratio
            val ratio = min(
                it.size.width / board.width,
                it.size.height / board.height
            )
            val height = ratio * board.height
            val width = ratio * board.width

            val boardRect = Rect(
                Offset(it.center.x - width / 2, it.center.y - height / 2),
                Size(width, height)
            )

            // Projected board space
            drawRect(
                color = Color.LightGray,
                topLeft = boardRect.topLeft,
                size = boardRect.size,
            )

            // Boundaries for projected board
            drawRect(
                color = Color.Black,
                topLeft = it.topLeft,
                size = it.size,
                style = Stroke(
                    4f, pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(10f, 20f), 5f
                    )
                )
            )

            val x = it.center.x + board.origin.x * ratio
            val y = it.center.y - board.origin.y * ratio
            // Origin point
            drawCircle(color = Color.Black, radius = 12f, center = Offset(x, y))
            // X axis
            drawLine(Color.Black, start = Offset(x, 0f), end = Offset(x, size.height))
            // Y axis
            drawLine(Color.Black, start = Offset(0f, y), end = Offset(size.width, y))
        }
    }
}

@Composable
private fun TimingSection(
    timing: TimingModel,
    spacing: Dp,
    modifier: Modifier = Modifier,
) {
    Expander(stringResource(id = R.string.timing_section), modifier = modifier) {
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing)
            ) {
                Icon(imageVector = Icons.Filled.Adjust, contentDescription = null)
                Text(
                    text = stringResource(id = R.string.time_bpm_label, timing.bpm),
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing)
            ) {
                Icon(imageVector = Icons.Filled.Start, contentDescription = null)
                Text(
                    text = timing.offset.formatMillis(),
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing)
            ) {
                Icon(imageVector = Icons.Filled.AccessTime, contentDescription = null)
                Text(
                    text = timing.duration.formatMillis(),
                )
            }
        }
    }
}

@Composable
private fun AudioSection(
    audio: AudioFileModel?,
    spacing: Dp,
    modifier: Modifier = Modifier,
) {
    Expander(stringResource(id = R.string.audio_section), modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(spacing),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (audio != null) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = audio.name,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = audio.millis.formatMillis(),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                AudioVisualizer(
                    data = audio.pcm,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(text = stringResource(id = R.string.null_audio))
            }
        }
    }
}

@Composable
private fun MetaDataSection(
    metaData: List<MetaDataModel>,
    modifier: Modifier = Modifier,
) {
    Expander(stringResource(id = R.string.meta_section), modifier = modifier) {
        Column(
            horizontalAlignment = if (metaData.isNotEmpty()) Alignment.Start else Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (metaData.isNotEmpty()) {
                metaData.forEachIndexed { index, entry ->
                    Text(
                        text = "${index + 1}. ${entry.data}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                Text(text = stringResource(id = R.string.empty_metadata))
            }
        }
    }
}

@Composable
private fun DetailsHeader(
    thumbnailUri: Uri,
    title: String,
    author: String,
    spacing: Dp,
    modifier: Modifier = Modifier,
    pendingPainter: Painter = rememberVectorPainter(image = Icons.Filled.Pending),
    errorPainter: Painter = rememberVectorPainter(image = Icons.Filled.BrokenImage),
) {
    Row(
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnailUri)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(id = R.string.project_image),
            contentScale = ContentScale.Crop,
            placeholder = pendingPainter,
            error = errorPainter,
            modifier = Modifier
                .width(96.dp)
                .aspectRatio(1f)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(spacing)
        ) {
            Text(text = title, style = MaterialTheme.typography.headlineMedium)
            Text(text = author)
        }
    }
}

@Composable
private fun RanksSection(
    ranks: List<RankModel>,
    spacing: Dp,
    modifier: Modifier = Modifier,
) {
    Expander(stringResource(id = R.string.ranks_section), modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing, Alignment.CenterHorizontally),
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            if (ranks.isNotEmpty()) {
                ranks.forEach {
                    RankCard(
                        uri = it.thumbnailUri,
                        points = it.requiredPoint.toString(),
                        name = it.name,
                        modifier = Modifier.width(64.dp)
                    )
                }
            } else {
                Text(text = stringResource(id = R.string.empty_ranks))
            }
        }
    }
}

@Preview(device = "id:pixel_5")
@Composable
fun ProjectListItemDetailsPreview() {
    SIMMETheme {
        Surface {
            ProjectListItemDetails(
                isFullScreen = true,
                item = ProjectItemDetailsUIState(
                    id = 0,
                    thumbnailUri = Uri.EMPTY,
                    title = "title",
                    description = "description",
                    author = "author",
                    timing = TimingModel(
                        bpm = 152,
                        millis = (3 * 60 + 23) * 1000,
                        offset = 0
                    ),
                    audio = null,
                    board = BoardModel(PointModel(-500f, -220f), 1920f, 1080f),
                    metaData = listOf(MetaDataModel("META 1"), MetaDataModel("META 2")),
                    ranks = List(3) { RankModel("name $it", it, Uri.EMPTY) }
                )
            )
        }
    }
}