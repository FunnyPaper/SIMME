package com.funnypaper.simme.ui.screens.globalproperties

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funnypaper.simme.domain.repository.NavigationPreferencesRepository
import com.funnypaper.simme.domain.usecase.GetAudioFileUseCase
import com.funnypaper.simme.domain.usecase.UpdateAudioFileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioPropertiesViewModel @Inject constructor(
    getAudioFileUseCase: GetAudioFileUseCase,
    private val updateAudioFileUseCase: UpdateAudioFileUseCase,
    navigationPreferencesRepository: NavigationPreferencesRepository,
) : ViewModel() {
    private var projectId = navigationPreferencesRepository.projectId.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )

    init {
        viewModelScope.launch {
            getAudioFileUseCase(projectId.value!!).map { model ->
                if (model != null) {
                    AudioFileUIState.Loaded(
                        name = model.name,
                        pcm = model.pcm,
                        millis = model.millis,
                        uri = model.uri
                    )
                } else {
                    AudioFileUIState.None
                }
            }.collect {
                _state.emit(it)
            }
        }
    }

    private val _state: MutableStateFlow<AudioFileUIState> =
        MutableStateFlow(AudioFileUIState.Loading)
    val state = _state.asStateFlow()
    suspend fun updateAudioFile(uri: Uri) {
        updateAudioFileUseCase(projectId.value!!, uri)
        _state.emit(AudioFileUIState.Loading)
    }

    suspend fun deleteAudioFile() {
        updateAudioFileUseCase(projectId.value!!, null)
    }
}

sealed interface AudioFileUIState {
    data object Loading : AudioFileUIState
    data object None : AudioFileUIState
    data class Loaded(
        val name: String,
        val pcm: List<Float>,
        val millis: Long,
        val uri: Uri,
    ) : AudioFileUIState
}
