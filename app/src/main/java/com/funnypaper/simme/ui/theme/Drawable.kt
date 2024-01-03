package com.funnypaper.simme.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import com.funnypaper.simme.R

data class Drawable(
    @DrawableRes val loading: Int = R.drawable.image_loading,
    @DrawableRes val error: Int = R.drawable.image_error,
    @DrawableRes val defaultProjectThumbnail: Int = R.drawable.default_project_icon,
    @DrawableRes val defaultRankThumbnail: Int = R.drawable.default_rank_icon,
)

val LocalDrawable = compositionLocalOf { Drawable() }

val MaterialTheme.drawable: Drawable
    @Composable
    @ReadOnlyComposable
    get() = LocalDrawable.current