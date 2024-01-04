package com.funnypaper.simme.domain.model

import android.net.Uri
import com.funnypaper.simme.data.local.entity.RankEntity

data class RankModel(
    val name: String,
    val requiredPoint: Int,
    val thumbnailUri: Uri
) {
    var id: Int = 0
}

fun RankEntity.toRankModel() =
    RankModel(
        name = this.name,
        requiredPoint = requiredPoint,
        thumbnailUri = thumbnailPath?.let { Uri.parse(it) } ?: Uri.EMPTY
    ).apply { id = this@toRankModel.id }