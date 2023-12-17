package com.funnypaper.simme.ui.screens.projectlist

import android.net.Uri
import androidx.lifecycle.ViewModel

class ProjectListViewModel: ViewModel() {

}

data class ProjectItemUIState(
    val id: Int,
    val thumbnailUri: Uri,
    val title: String,
    val selected: Boolean
)

data class ProjectItemDetailsUIState(
    val id: Int,
    val thumbnailUri: Uri,
    val title: String,
    val description: String,
    val author: String,
    val startOffset: Int,
    val audioUri: Uri,
    val board: Board,
    val difficulties: List<Difficulty>,
    val ranks: List<Rank>,
    val metaData: MetaData
)

data class Board(
    val id: Int,
    val width: Float,
    val height: Float,
    val metaData: MetaData
)

data class Difficulty(
    val id: Int,
    val thumbnailUri: Uri,
    val name: String,
    val pointMultiplier: Float,
    val bmp: Int,
    val metaData: MetaData
)

data class Rank(
    val id: Int,
    val name: String,
    val requiredPoint: Int,
    val metaData: MetaData
)

data class MetaData(
    val id: Int,
    val data: List<String>
)