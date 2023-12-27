package com.funnypaper.simme.ui.screens.projectlist

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.funnypaper.simme.R
import com.funnypaper.simme.domain.model.AudioFileModel
import com.funnypaper.simme.domain.model.BoardModel
import com.funnypaper.simme.domain.model.MetaDataModel
import com.funnypaper.simme.domain.model.PointModel
import com.funnypaper.simme.domain.model.RankModel
import com.funnypaper.simme.ui.shared.audiovisualizer.AudioVisualizer
import com.funnypaper.simme.ui.shared.extensions.formatMillis
import com.funnypaper.simme.ui.shared.extensions.getFilename
import com.funnypaper.simme.ui.shared.treelist.TreeList
import com.funnypaper.simme.ui.shared.treelist.TreeListItem
import com.funnypaper.simme.ui.theme.SIMMETheme
import kotlin.math.min
import kotlin.random.Random

@Composable
fun ProjectListItemDetails(
    item: ProjectItemDetailsUIState,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        DetailsHeader(
            thumbnailUri = item.thumbnailUri,
            title = item.title,
            author = item.author,
        )

        DescriptionSection(description = item.description)
        BoardSection(board = item.board)
        AudioSection(bmp = item.bmp, startOffset = item.startOffset, audio = item.audio)
        RanksSection(ranks = item.ranks)
        MetaDataSection(metaData = item.metaData)
    }
}

@Composable
private fun DescriptionSection(
    description: String
) {
    DetailsSection(stringResource(id = R.string.description_section)) {
        Text(text = description, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun BoardSection(
    board: BoardModel
) {
    DetailsSection(stringResource(id = R.string.board_section)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BoardVisualizer(
                board = board,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
            )
            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.CenterFocusStrong,
                        contentDescription = null
                    )
                    Text(text = "${board.origin.x}")
                    Canvas(modifier = Modifier.size(8.dp)) {
                        size.toRect().let {
                            drawLine(Color.Black, it.topLeft, it.bottomRight, 4f)
                            drawLine(Color.Black, it.bottomLeft, it.topRight, 4f)
                        }
                    }
                    Text(text = "${board.origin.y}")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Height,
                        contentDescription = null,
                        modifier = Modifier.rotate(90f)
                    )
                    Text(text = "${board.width}")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Filled.Height, contentDescription = null)
                    Text(text = "${board.height}")
                }
            }
        }
    }
}

@Composable
fun BoardVisualizer(
    board: BoardModel,
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier
            .background(Color.White)
            .border(1.dp, Color.Black)
    ) {
        size.toRect().deflate(size.height / 6f).let {
            val maxX = min(it.size.width, board.width)
            val maxY = min(it.size.height, board.height)
            // Fit ratio
            val ratio = min(maxX / board.width, maxY / board.height)
            val height = ratio * board.height
            val width = ratio * board.width

            val boardRect = Rect(
                Offset(it.center.x - width / 2, it.center.y - height / 2),
                Size(width, height)
            )

            drawRect(
                color = Color.LightGray,
                topLeft = boardRect.topLeft,
                size = boardRect.size,
            )

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
            drawCircle(color = Color.Black, radius = 12f, center = Offset(x, y))
            drawLine(Color.Black, start = Offset(x, 0f), end = Offset(x, size.height))
            drawLine(Color.Black, start = Offset(0f, y), end = Offset(size.width, y))
        }
    }
}

@Composable
private fun AudioSection(
    bmp: Int,
    startOffset: Int,
    audio: AudioFileModel
) {
    DetailsSection(stringResource(id = R.string.audio_section)) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(text = "BMP: $bmp", style = MaterialTheme.typography.labelMedium)
                    Text(
                        text = stringResource(id = R.string.time_offset_label, startOffset),
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                Text(text = audio.millis.formatMillis(), style = MaterialTheme.typography.labelMedium)
            }
            AudioVisualizer(
                data = audio.pcm,
                modifier = Modifier.fillMaxWidth()
            )
            audio.audioUri.getFilename(LocalContext.current.contentResolver)?.let {
                Text(text = it)
            }
        }
    }
}

@Composable
private fun RanksSection(
    ranks: List<RankModel>
) {
    DetailsSection(stringResource(id = R.string.ranks_section)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            ranks.forEach {
                RankCard(
                    uri = it.thumbnailUri,
                    points = it.requiredPoint.toString(),
                    name = it.name
                )
            }
        }
    }
}

@Composable
private fun MetaDataSection(
    metaData: List<MetaDataModel>
) {
    DetailsSection(stringResource(id = R.string.meta_section)) {
        Column {
            metaData.forEachIndexed { index, entry ->
                Text(
                    text = "${index + 1}. ${entry.data}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun DetailsHeader(
    thumbnailUri: Uri,
    title: String,
    author: String,
    pendingPainter: Painter = rememberVectorPainter(image = Icons.Filled.Pending),
    errorPainter: Painter = rememberVectorPainter(image = Icons.Filled.BrokenImage),
) {
    Row {
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
                .padding(8.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.headlineMedium)
            Text(text = author)
        }
    }
}

@Composable
private fun DetailsSection(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    TreeList(
        treeListItem = TreeListItem(
            content = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelLarge
                )
            },
            children = listOf(
                TreeListItem(content = content)
            )
        ),
        childPadding = 8.dp,
        modifier = modifier.height(IntrinsicSize.Max)
    )
}

@Composable
private fun RankCard(
    uri: Uri,
    points: String,
    name: String,
    pendingPainter: Painter = rememberVectorPainter(image = Icons.Filled.Pending),
    errorPainter: Painter = rememberVectorPainter(image = Icons.Filled.BrokenImage),
) {
    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uri)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(id = R.string.rank_image),
                contentScale = ContentScale.Crop,
                placeholder = pendingPainter,
                error = errorPainter,
                modifier = Modifier
                    .width(48.dp)
                    .aspectRatio(1f)
            )
            Text(
                text = points,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = name,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
fun ProjectListItemDetailsPreview() {
    SIMMETheme {
        Surface {
            ProjectListItemDetails(
                item = ProjectItemDetailsUIState(
                    thumbnailUri = Uri.EMPTY,
                    title = "title",
                    description = "description",
                    author = "author",
                    startOffset = 0,
                    bmp = 152,
                    audio = AudioFileModel(
                        audioUri = Uri.EMPTY,
                        pcm = List(1024) { Random.nextFloat() * 1000 },
                        millis = ((1 * 60 + 3) * 60 + 23) * 1000,
                    ),
                    board = BoardModel(PointModel(-500f, -220f), 1920f, 1080f),
                    metaData = listOf(MetaDataModel("META 1"), MetaDataModel("META 2")),
                    ranks = List(3) { RankModel("name $it", it, Uri.EMPTY) }
                )
            )
        }
    }
}