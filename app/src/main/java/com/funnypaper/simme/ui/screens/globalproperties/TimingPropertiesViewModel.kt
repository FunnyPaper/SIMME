package com.funnypaper.simme.ui.screens.globalproperties

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funnypaper.simme.domain.model.TimingModel
import com.funnypaper.simme.domain.repository.NavigationPreferencesRepository
import com.funnypaper.simme.domain.usecase.GetTimingUseCase
import com.funnypaper.simme.domain.usecase.UpdateTimingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TimingPropertiesViewModel @Inject constructor(
    getTimingUseCase: GetTimingUseCase,
    private val updateTimingUseCase: UpdateTimingUseCase,
    navigationPreferencesRepository: NavigationPreferencesRepository,
) : ViewModel() {
    private var projectId = navigationPreferencesRepository.projectId.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )

    val state = getTimingUseCase(projectId.value!!).map { model ->
        if (model != null) {
            TimingUIState.Loaded(
                id = model.id,
                bpm = model.bpm,
                offset = model.offset,
                millis = model.millis
            )
        } else {
            TimingUIState.Loaded()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TimingUIState.Loading
    )

    suspend fun updateTiming(bpm: Int, duration: Long, offset: Long) {
        if (state.value is TimingUIState.Loaded) {
            val state = state.value as TimingUIState.Loaded
            updateTimingUseCase(
                projectId.value!!,
                TimingModel(
                    bpm = bpm,
                    offset = offset,
                    millis = duration
                ).apply { id = state.id }
            )
        }
    }
}

sealed interface TimingUIState {
    data object Loading : TimingUIState
    data class Loaded(
        val id: Int = 0,
        val bpm: Int = 1,
        val offset: Long = 0,
        val millis: Long = 1,
    ) : TimingUIState
}