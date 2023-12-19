package com.funnypaper.simme.ui.shared.treelist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.funnypaper.simme.R
import com.funnypaper.simme.ui.theme.SIMMETheme

/**
 * Helper holder for creating tree structure that can be passed to [TreeList] component
 * @param children Nested tree nodes containing composable contents
 * @param content Content of the current tree node
 */
data class TreeListItem(
    val children: List<TreeListItem>? = null,
    val content: @Composable () -> Unit,
) {
    /**
     * Should content of the children hide or show
     */
    internal var childrenHidden: Boolean by mutableStateOf(false)
}

/**
 * Component allowing to lay children in nested structure similar to folder tree list
 * @param treeListItem Tree item to be composed
 * @param modifier The modifier to apply for this component
 * @param expandIcon Icon used for expanding children content
 * @param hideIcon Icon used for hiding children content
 * @param childPadding The padding to apply to children nodes to the left side.
 * Composed gap is equal to product of padding and nesting level.
 * @param colors Color scheme to apply for this component
 */
@Composable
fun TreeList(
    treeListItem: TreeListItem,
    modifier: Modifier = Modifier,
    expandIcon: ImageVector = TreeListDefaults.expandIcon,
    hideIcon: ImageVector = TreeListDefaults.hideIcon,
    childPadding: Dp = TreeListDefaults.childPadding,
    colors: TreeListColors = TreeListDefaults.colors(),
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        node(
            treeListItem = treeListItem,
            expandIcon = expandIcon,
            hideIcon = hideIcon,
            toggleVisibility = { it.childrenHidden = !it.childrenHidden },
            // Root node should always be present
            hidden = false,
            childPadding = childPadding,
            colors = colors
        )
    }
}

private fun LazyListScope.node(
    treeListItem: TreeListItem,
    expandIcon: ImageVector,
    hideIcon: ImageVector,
    toggleVisibility: (TreeListItem) -> Unit,
    hidden: Boolean,
    colors: TreeListColors,
    childPadding: Dp = 0.dp,
    depth: Int = 0,
) {
    item {
        AnimatedVisibility(
            visible = !hidden,
            enter = slideIn(spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow)) {
                IntOffset(-it.width, 0)
            },
            exit = slideOut(tween(500, 0, EaseInOutCubic)) {
                IntOffset(-it.width, 0)
            }
        ) {
            val fullHidden by remember {
                derivedStateOf {
                    treeListItem.childrenHidden || treeListItem.children == null
                }
            }
            val contentColor = colors.nodeContentColor(expanded = !fullHidden).value
            val containerColor = colors.nodeContainerColor(expanded = !fullHidden).value

            CompositionLocalProvider(
                // Provide default colors to be used if content doesn't specify any
                LocalContentColor provides contentColor,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        // Make rows as tall as expand more/less icon buttons
                        .height(IntrinsicSize.Min)
                        .background(containerColor)
                        .padding(start = childPadding * depth)
                ) {
                    Box(
                        modifier = Modifier
                            // Match icon size from the edges
                            .padding(12.dp)
                            .weight(1f)
                    ) {
                        treeListItem.content()
                    }

                    // Show expand / hide action if content has children
                    if (treeListItem.children != null) {
                        IconButton(
                            onClick = { toggleVisibility(treeListItem) }
                        ) {
                            Icon(
                                imageVector = if(treeListItem.childrenHidden) expandIcon else hideIcon,
                                contentDescription = stringResource(
                                    id = if(treeListItem.childrenHidden)
                                        R.string.expand_icon
                                    else
                                        R.string.hide_icon
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    // Compose every children if any in recursive manner
    treeListItem.children?.forEach {
        node(
            treeListItem = it,
            expandIcon = expandIcon,
            hideIcon = hideIcon,
            toggleVisibility = toggleVisibility,
            // Hidden property should cascade to children
            hidden = treeListItem.childrenHidden || hidden,
            childPadding = childPadding,
            depth = depth + 1,
            colors = colors
        )
    }
}

/**
 * Color palette used inside TreeList component
 * @param expandedNodeContainerColor Color used for background when content is expanded
 * @param expandedNodeContentColor Color used for foreground when content is expanded
 * @param hiddenNodeContainerColor Color used for background when content is hid
 * @param hiddenNodeContentColor Color used for foreground when content is hid
 */
@Immutable
class TreeListColors internal constructor(
    private val expandedNodeContainerColor: Color,
    private val expandedNodeContentColor: Color,
    private val hiddenNodeContainerColor: Color,
    private val hiddenNodeContentColor: Color
) {
    /**
     * Calculates color to be used in container context
     * @param expanded Should current color represent expanded state
     */
    @Composable
    internal fun nodeContainerColor(expanded: Boolean): State<Color> {
        return rememberUpdatedState(newValue = if(expanded) expandedNodeContainerColor else hiddenNodeContainerColor)
    }

    /**
     * Calculates color to be used in content context
     * @param expanded Should current color represent expanded state
     */
    @Composable
    internal fun nodeContentColor(expanded: Boolean): State<Color> {
        return rememberUpdatedState(newValue = if(expanded) expandedNodeContentColor else hiddenNodeContentColor)
    }
}

/**
 * Default values and composables used with TreeList component.
 */
object TreeListDefaults {
    val expandIcon = Icons.Filled.ExpandMore
    val hideIcon = Icons.Filled.ExpandLess
    val childPadding = 16.dp

    /**
     * Composes color palette for TreeList component
     * @param expandedNodeContainerColor Color used for background when content is expanded
     * @param expandedNodeContentColor Color used for foreground when content is expanded
     * @param hiddenNodeContainerColor Color used for background when content is hid
     * @param hiddenNodeContentColor Color used for foreground when content is hid
     */
    @Composable
    fun colors(
        expandedNodeContainerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        expandedNodeContentColor: Color = contentColorFor(backgroundColor = expandedNodeContainerColor),
        hiddenNodeContainerColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .38f),
        hiddenNodeContentColor: Color = contentColorFor(backgroundColor = hiddenNodeContainerColor),
    ): TreeListColors =
        TreeListColors(
            expandedNodeContainerColor = expandedNodeContainerColor,
            expandedNodeContentColor = expandedNodeContentColor,
            hiddenNodeContainerColor = hiddenNodeContainerColor,
            hiddenNodeContentColor = hiddenNodeContentColor
        )
}

@Composable
@Preview
private fun TreeListPreview() {
    SIMMETheme {
        Surface {
            TreeList(
                treeListItem = TreeListItem(
                    content = { Text(text = "Level 0") },
                    children = listOf(
                        TreeListItem { Text(text = "Level 1") },
                        TreeListItem(
                            content = { Text(text = "Level 1") },
                            children = listOf(
                                TreeListItem(
                                    content = { Text(text = "Level 2") }
                                )
                            )
                        ),
                        TreeListItem { Text(text = "Level 1") },
                        TreeListItem(
                            content = { Text(text = "Level 1") },
                            children = listOf(
                                TreeListItem(
                                    content = { Text(text = "Level 2") },
                                    children = listOf(
                                        TreeListItem { Text(text = "Level 3") }
                                    )
                                )
                            )
                        ),
                        TreeListItem(
                            content = { Text(text = "Level 1") },
                            children = listOf(
                                TreeListItem(
                                    content = { Text(text = "Level 2") },
                                    children = listOf(
                                        TreeListItem { Text(text = "Level 3") }
                                    )
                                )
                            )
                        ),
                        TreeListItem(
                            content = { Text(text = "Level 1") },
                            children = listOf(
                                TreeListItem(
                                    content = { Text(text = "Level 2") },
                                    children = listOf(
                                        TreeListItem { Text(text = "Level 3") }
                                    )
                                )
                            )
                        ),
                        TreeListItem(
                            content = { Text(text = "Level 1") },
                            children = listOf(
                                TreeListItem(
                                    content = { Text(text = "Level 2") },
                                    children = listOf(
                                        TreeListItem { Text(text = "Level 3") }
                                    )
                                )
                            )
                        ),
                        TreeListItem(
                            content = { Text(text = "Level 1") },
                            children = listOf(
                                TreeListItem(
                                    content = { Text(text = "Level 2") },
                                    children = listOf(
                                        TreeListItem { Text(text = "Level 3") }
                                    )
                                )
                            )
                        ),
                        TreeListItem(
                            content = { Text(text = "Level 1") },
                            children = listOf(
                                TreeListItem(
                                    content = { Text(text = "Level 2") },
                                    children = listOf(
                                        TreeListItem { Text(text = "Level 3") }
                                    )
                                )
                            )
                        )
                    )
                ),
                expandIcon = Icons.Filled.ExpandMore,
                hideIcon = Icons.Filled.ExpandLess,
                childPadding = 16.dp
            )
        }
    }
}