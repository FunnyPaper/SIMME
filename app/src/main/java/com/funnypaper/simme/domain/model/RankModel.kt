package com.funnypaper.simme.domain.model

import android.net.Uri

data class RankModel(
    val name: String,
    val requiredPoint: Int,
    val thumbnailUri: Uri
)
