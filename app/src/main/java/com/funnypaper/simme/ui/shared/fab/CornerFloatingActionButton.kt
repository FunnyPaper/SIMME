package com.funnypaper.simme.ui.shared.fab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.RemoveDone
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.SubcomposeMeasureScope
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset

@Composable
fun CornerFloatingActionButton(
    modifier: Modifier = Modifier,
    horizontalButtons: List<@Composable () -> Unit> = emptyList(),
    verticalButtons: List<@Composable () -> Unit> = emptyList(),
    spacing: Dp = 8.dp,
    tonalElevation: Dp = 6.dp,
    shadowElevation: Dp = 6.dp,
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    contentColor: Color = contentColorFor(backgroundColor = containerColor),
    shape: Shape = FloatingActionButtonDefaults.smallShape
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    val rotation by animateFloatAsState(
        targetValue = if (expanded) 45f else 0f,
        animationSpec = tween(500, 0, EaseInOutCubic),
        label = "Fab rotation"
    )

    SubcomposeLayout(modifier = modifier.fillMaxSize()) { constraints ->
        val fabSpacing = 16.dp.roundToPx()
        val maxWidth = constraints.maxWidth
        val maxHeight = constraints.maxHeight
        val loose = constraints.copy(minWidth = 0, minHeight = 0)

        layout(maxWidth, maxHeight) {
            // Measure fab
            val fab = measurePlaceables(
                slot = CornerFloatingActionButtonElement.FAB,
                constraints = loose
            ) {
                SmallFloatingActionButton(
                    onClick = { expanded = !expanded },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        modifier = Modifier.rotate(rotation)
                    )
                }
            }

            val withoutFabConstraints = loose.offset(
                -fabSpacing * 2 - fab.maxWidth - spacing.roundToPx(),
                -fabSpacing * 2 - fab.maxHeight
            )

            // Measure horizontal bar
            val horizontalBar = measurePlaceables(
                slot = CornerFloatingActionButtonElement.HORIZONTAL_ACTIONS,
                constraints = withoutFabConstraints
            ) {
                CornerFloatingActionButtonRow(
                    expanded = expanded,
                    fabHeight = fab.maxHeight.toDp(),
                    buttons = horizontalButtons.reversed(),
                    tonalElevation = tonalElevation,
                    shadowElevation = shadowElevation,
                    containerColor = containerColor,
                    contentColor = contentColor,
                    shape = shape
                )
            }

            // Measure vertical bar
            val verticalBar = measurePlaceables(
                slot = CornerFloatingActionButtonElement.VERTICAL_ACTIONS,
                constraints = withoutFabConstraints
            ) {
                CornerFloatingActionButtonColumn(
                    expanded = expanded,
                    fabWidth = fab.maxWidth.toDp(),
                    buttons = verticalButtons.reversed(),
                    tonalElevation = tonalElevation,
                    shadowElevation = shadowElevation,
                    containerColor = containerColor,
                    contentColor = contentColor,
                    shape = shape
                )
            }

            // Place horizontal bar
            horizontalBar.placeables.forEach {
                it.place(
                    x = (maxWidth - fab.maxWidth - horizontalBar.maxWidth - spacing.roundToPx())
                        .coerceAtLeast(fabSpacing * 2),
                    y = maxHeight - horizontalBar.maxHeight
                )
            }

            // Place vertical bar
            verticalBar.placeables.forEach {
                it.place(
                    x = maxWidth - verticalBar.maxWidth,
                    y = (maxHeight - fab.maxHeight - verticalBar.maxHeight - spacing.roundToPx())
                        .coerceAtLeast(fabSpacing * 2)
                )
            }

            // Place fab
            fab.placeables.forEach {
                it.place(
                    x = maxWidth - fab.maxWidth,
                    y = maxHeight - fab.maxHeight
                )
            }
        }
    }
}

@Composable
private fun CornerFloatingActionButtonRow(
    expanded: Boolean,
    fabHeight: Dp,
    buttons: List<@Composable () -> Unit> = emptyList(),
    tonalElevation: Dp = 6.dp,
    shadowElevation: Dp = 6.dp,
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    contentColor: Color = contentColorFor(backgroundColor = containerColor),
    shape: Shape = FloatingActionButtonDefaults.smallShape,
) {
    AnimatedVisibility(
        visible = expanded,
        enter = slideInHorizontally(tween(500, 0, EaseInCubic)) { it * 2 },
        exit = slideOutHorizontally(tween(500, 0, EaseInCubic)) { it * 2 },
    ) {
        Box(
            modifier = Modifier.height(fabHeight),
            contentAlignment = Alignment.Center
        ) {
            CompositionLocalProvider(
                LocalContentColor provides contentColor,
                LocalAbsoluteTonalElevation provides tonalElevation
            ) {
                Row(
                    modifier = Modifier
                        .requiredHeight(40.dp)
                        .shadow(shadowElevation, shape, false)
                        .background(containerColor, shape)
                        .clip(shape)
                        .horizontalScroll(rememberScrollState())
                ) {
                    buttons.forEach { it() }
                }
            }
        }
    }
}

@Composable
private fun CornerFloatingActionButtonColumn(
    expanded: Boolean,
    fabWidth: Dp,
    buttons: List<@Composable () -> Unit> = emptyList(),
    tonalElevation: Dp = 6.dp,
    shadowElevation: Dp = 6.dp,
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    contentColor: Color = contentColorFor(backgroundColor = containerColor),
    shape: Shape = FloatingActionButtonDefaults.smallShape,
) {
    AnimatedVisibility(
        visible = expanded,
        enter = slideInVertically(tween(500, 0, EaseInCubic)) { it * 2 },
        exit = slideOutVertically(tween(500, 0, EaseInCubic)) { it * 2 },
    ) {
        Box(
            modifier = Modifier.width(fabWidth),
            contentAlignment = Alignment.Center
        ) {
            CompositionLocalProvider(
                LocalContentColor provides contentColor,
                LocalAbsoluteTonalElevation provides tonalElevation
            ) {
                Column(
                    modifier = Modifier
                        .requiredWidth(40.dp)
                        .shadow(shadowElevation, shape, false)
                        .background(containerColor, shape)
                        .clip(shape)
                        .verticalScroll(rememberScrollState())
                ) {
                    buttons.forEach { it() }
                }
            }
        }
    }
}

private data class MeasurementResult(
    val placeables: List<Placeable>,
    val maxWidth: Int,
    val maxHeight: Int
)

private fun SubcomposeMeasureScope.measurePlaceables(
    slot: CornerFloatingActionButtonElement,
    constraints: Constraints,
    content: @Composable () -> Unit
): MeasurementResult {
    val placeables = subcompose(slot) {
        content()
    }.map { it.measure(constraints) }
    val maxWidth = placeables.maxByOrNull { it.width }?.width ?: 0
    val maxHeight = placeables.maxByOrNull { it.height }?.height ?: 0

    return MeasurementResult(placeables, maxWidth, maxHeight)
}

private enum class CornerFloatingActionButtonElement {
    FAB, HORIZONTAL_ACTIONS, VERTICAL_ACTIONS
}
