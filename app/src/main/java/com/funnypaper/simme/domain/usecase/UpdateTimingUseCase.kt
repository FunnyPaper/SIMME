package com.funnypaper.simme.domain.usecase

import com.funnypaper.simme.data.repository.timing.IDataTimingRepository
import com.funnypaper.simme.domain.model.TimingModel
import com.funnypaper.simme.domain.model.toTimingEntity
import javax.inject.Inject

class UpdateTimingUseCase @Inject constructor(
    private val timingRepository: IDataTimingRepository,
) {
    suspend operator fun invoke(projectId: Int, model: TimingModel) =
        timingRepository.updateTiming(model.toTimingEntity(projectId))
}