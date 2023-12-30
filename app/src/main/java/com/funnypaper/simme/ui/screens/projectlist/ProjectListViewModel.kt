package com.funnypaper.simme.ui.screens.projectlist

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.funnypaper.simme.data.local.entity.MetaDataEntity
import com.funnypaper.simme.domain.model.AudioFileModel
import com.funnypaper.simme.domain.model.BoardModel
import com.funnypaper.simme.domain.model.MetaDataModel
import com.funnypaper.simme.domain.model.PointModel
import com.funnypaper.simme.domain.model.RankModel
import com.funnypaper.simme.domain.model.TimingModel

class ProjectListViewModel: ViewModel() {

}

data class ProjectItemDetailsUIState(
    val thumbnailUri: Uri,
    val title: String,
    val description: String,
    val author: String,
    val timing: TimingModel,
    val audio: AudioFileModel?,
    val board: BoardModel,
    val metaData: List<MetaDataModel>,
    val ranks: List<RankModel>
)

data class ProjectItemUIState(
    val id: Int,
    val thumbnailUri: Uri,
    val title: String,
    val selected: Boolean
)