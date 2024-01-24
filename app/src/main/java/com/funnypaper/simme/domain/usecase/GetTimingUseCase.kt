package com.funnypaper.simme.domain.usecase

import com.funnypaper.simme.data.repository.timing.IDataTimingRepository
import com.funnypaper.simme.domain.model.toTimingModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTimingUseCase @Inject constructor(
    private val timingRepository: IDataTimingRepository,
) {
    operator fun invoke(projectId: Int) =
        timingRepository.getTimingByProjectId(projectId).map {
            it?.toTimingModel()
        }
}