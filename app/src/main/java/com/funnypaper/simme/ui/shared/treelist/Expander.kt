package com.funnypaper.simme.ui.shared.treelist

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Expander(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    val hidden = rememberSaveable {
        mutableStateOf(false)
    }

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
            ),
            childrenHidden = hidden
        ),
        childPadding = 8.dp,
        modifier = modifier.height(IntrinsicSize.Max)
    )
}