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