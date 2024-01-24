package com.funnypaper.simme.domain.usecase

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import com.funnypaper.simme.data.local.entity.AudioEntity
import com.funnypaper.simme.data.repository.audio.IDataAudioRepository
import com.funnypaper.simme.domain.extensions.getFilename
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UpdateAudioFileUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val audioRepository: IDataAudioRepository,
) {
    suspend operator fun invoke(projectId: Int, uri: Uri?) {
        if (uri == null) {
            audioRepository.deleteAudioByProjectId(projectId)
        } else {
            val retriever = MediaMetadataRetriever().apply {
                setDataSource(context, uri)
            }

            audioRepository.insertAudio(
                AudioEntity(
                    projectId = projectId,
                    name = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
                        ?: uri.getFilename(context.contentResolver)
                        ?: "Unknown",
                    uriPath = uri.toString(),
                    millis = try {
                        retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                            ?.toLong()
                            ?: 0
                    } catch (e: Exception) {
                        e.printStackTrace()
                        0
                    } finally {
                        retriever.release()
                    }
                )
            )
        }
    }
}