package com.funnypaper.simme.domain.model

import com.funnypaper.simme.data.local.entity.MetaDataEntity

data class MetaDataModel(
    val data: String
) {
    var id: Int = 0
}

fun MetaDataEntity.toMetaDataModel() =
    MetaDataModel(data = this.data).apply {
        id = this@toMetaDataModel.id
    }