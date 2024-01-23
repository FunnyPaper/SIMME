package com.funnypaper.simme.domain.usecase

import android.content.Context
import android.net.Uri
import com.funnypaper.simme.data.repository.audio.IDataAudioRepository
import com.funnypaper.simme.domain.model.toAudioFileModel
import com.funnypaper.simme.domain.utility.audio.getPCMData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAudioFileUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val audioRepository: IDataAudioRepository,
) {
    operator fun invoke(projectId: Int) =
        audioRepository.getAudioByProjectId(projectId).map {
            it?.toAudioFileModel()?.let {
                it.copy(pcm = getPCM(it.uri))
            }
        }

    private suspend fun getPCM(uri: Uri): List<Float> =
        withContext(Dispatchers.IO) {
            getPCMData(context, uri)
        }
}