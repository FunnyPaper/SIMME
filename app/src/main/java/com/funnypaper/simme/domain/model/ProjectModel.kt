package com.funnypaper.simme.domain.model

import android.net.Uri

data class ProjectModel(
    val thumbnailUri: Uri,
    val title: String,
    val description: String,
    val author: String,
    val startOffset: Int,
    val bmp: Int,
    val audioUri: Uri,
    val board: BoardModel
)
