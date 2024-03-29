package com.funnypaper.simme.ui.shared.audiovisualizer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.funnypaper.simme.domain.extensions.scaleNumber
import com.funnypaper.simme.domain.utility.audio.shrinkArray

@Composable
fun AudioVisualizer(
    data: List<Float>,
    modifier: Modifier = Modifier,
    onHorizontalDragStart: (Float) -> Unit = {},
    onHorizontalDragEnd: () -> Unit = {},
    onHorizontalDrag: (Float) -> Unit = {},
    outlineWidth: Dp = AudioVisualizerDefaults.OutlineWidth,
    binCount: Int = AudioVisualizerDefaults.BinCount,
    binWidth: Float = AudioVisualizerDefaults.BinWidth,
    progressIndicatorWidth: Float = AudioVisualizerDefaults.ProgressIndicatorWidth,
    progression: Float = 0.0f,
    paused: Boolean = true,
    shape: Shape = AudioVisualizerDefaults.Shape,
    contentPadding: PaddingValues = AudioVisualizerDefaults.ContentPadding,
    colors: AudioVisualizerColors = AudioVisualizerDefaults.colors(),
) {
    // Colors
    val containerColor = colors.containerColor(paused = paused).value
    val binColor = colors.binColor(paused = paused).value
    val outlineColor = colors.outlineColor(paused = paused).value
    val progressedContainerColor = colors.progressedContainerColor(paused = paused).value
    val progressedBinColor = colors.progressedBinColor(paused = paused).value
    val progressIndicatorColor = colors.progressIndicatorColor(paused = paused).value

    // Bins
    val binData = remember(data) { shrinkArray(data, binCount) }

    // Canvas
    var size by remember { mutableStateOf(Size.Zero) }
    val gapSize = remember(size, binData) { size.width / (binData.size + 1) }
    val halfHeight = remember(size) { size.height / 2 }
    val transformation = remember(halfHeight) {
        // Flip by X axis and move origin to middle left
        Matrix(
            floatArrayOf(
                1f, 0f, 0f, 0f,
                0f, -1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, halfHeight, 0f, 1f
            )
        )
    }

    // Cache transformations
    val binPaths = remember(binData, gapSize, halfHeight) {
        binData.mapIndexed { index, value ->
            val x = (index + 1) * gapSize
            val y = value.scaleNumber(binData.min(), binData.max(), 1f, halfHeight)
            val tt1 = transformation.map(Offset(x, y))
            val tt2 = transformation.map(Offset(x, -y))

            Path().apply {
                moveTo(tt1.x, tt1.y)
                lineTo(tt2.x, tt2.y)
            }
        }
    }

    Canvas(
        modifier = Modifier
            .widthIn(AudioVisualizerDefaults.MinWidth)
            .height(AudioVisualizerDefaults.Height)
            .border(outlineWidth, outlineColor, shape)
            .clip(shape)
            .clipToBounds()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = { onHorizontalDragStart(it.x / size.width) },
                    onDragEnd = { onHorizontalDragEnd() }
                ) { change, dragAmount ->
                    change.consume()
                    onHorizontalDrag(dragAmount / size.width)
                }
            }
            .drawWithContent {
                val size = this.size

                // Draw under padding
                drawRect(containerColor, size = size)
                drawRect(
                    progressedContainerColor,
                    size = size.copy(width = progression * size.width)
                )

                drawContent()

                // Progress Indicator
                Path().apply {
                    moveTo(progression * size.width, 0f)
                    lineTo(progression * size.width, size.height)

                    drawPath(
                        this,
                        color = progressIndicatorColor,
                        style = Stroke(width = progressIndicatorWidth)
                    )
                }
            }
            .padding(contentPadding)
            .onGloballyPositioned { size = it.size.toSize() }
            .then(modifier)
    ) {
        // Sample bins
        binPaths.forEach {
            drawPath(
                it,
                color = if (it.getBounds().right < progression * size.width)
                    progressedBinColor
                else
                    binColor,
                style = Stroke(width = binWidth)
            )
        }
    }
}

@Immutable
class AudioVisualizerColors internal constructor(
    private val resumedContainerColor: Color,
    private val resumedBinColor: Color,
    private val resumedOutlineColor: Color,
    private val resumedProgressedContainerColor: Color,
    private val resumedProgressedBinColor: Color,
    private val resumedProgressIndicatorColor: Color,
    private val pausedContainerColor: Color,
    private val pausedBinColor: Color,
    private val pausedOutlineColor: Color,
    private val pausedProgressedContainerColor: Color,
    private val pausedProgressedBinColor: Color,
    private val pausedProgressIndicatorColor: Color,
) {
    @Composable
    internal fun containerColor(paused: Boolean): State<Color> {
        return rememberUpdatedState(if (paused) pausedContainerColor else resumedContainerColor)
    }

    @Composable
    internal fun binColor(paused: Boolean): State<Color> {
        return rememberUpdatedState(if (paused) pausedBinColor else resumedBinColor)
    }

    @Composable
    internal fun outlineColor(paused: Boolean): State<Color> {
        return rememberUpdatedState(if (paused) pausedOutlineColor else resumedOutlineColor)
    }

    @Composable
    internal fun progressedContainerColor(paused: Boolean): State<Color> {
        return rememberUpdatedState(if (paused) pausedProgressedContainerColor else resumedProgressedContainerColor)
    }

    @Composable
    internal fun progressedBinColor(paused: Boolean): State<Color> {
        return rememberUpdatedState(if (paused) pausedProgressedBinColor else resumedProgressedBinColor)
    }

    @Composable
    internal fun progressIndicatorColor(paused: Boolean): State<Color> {
        return rememberUpdatedState(if (paused) pausedProgressIndicatorColor else resumedProgressIndicatorColor)
    }
}

object AudioVisualizerDefaults {
    val ContentPadding =
        PaddingValues(vertical = 4.dp)

    val Shape: Shape @Composable get() = RoundedCornerShape(50)
    val MinWidth = 58.dp
    val Height = 40.dp
    val BinCount = 256
    val BinWidth = 2f
    val ProgressIndicatorWidth = 4f
    val OutlineWidth = 1.dp

    @Composable
    fun colors(
        resumedContainerColor: Color = MaterialTheme.colorScheme.primary,
        resumedBinColor: Color = MaterialTheme.colorScheme.onPrimary,
        resumedOutlineColor: Color = MaterialTheme.colorScheme.primary,
        resumedProgressedContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
        resumedProgressedBinColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        resumedProgressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
        pausedContainerColor: Color = MaterialTheme.colorScheme.secondary,
        pausedBinColor: Color = MaterialTheme.colorScheme.onSecondary,
        pausedOutlineColor: Color = MaterialTheme.colorScheme.secondary,
        pausedProgressedContainerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
        pausedProgressedBinColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
        pausedProgressIndicatorColor: Color = MaterialTheme.colorScheme.secondary,

        ): AudioVisualizerColors = AudioVisualizerColors(
        resumedContainerColor = resumedContainerColor,
        resumedBinColor = resumedBinColor,
        resumedOutlineColor = resumedOutlineColor,
        resumedProgressedContainerColor = resumedProgressedContainerColor,
        resumedProgressedBinColor = resumedProgressedBinColor,
        resumedProgressIndicatorColor = resumedProgressIndicatorColor,
        pausedContainerColor = pausedContainerColor,
        pausedBinColor = pausedBinColor,
        pausedOutlineColor = pausedOutlineColor,
        pausedProgressedContainerColor = pausedProgressedContainerColor,
        pausedProgressedBinColor = pausedProgressedBinColor,
        pausedProgressIndicatorColor = pausedProgressIndicatorColor,
    )
}