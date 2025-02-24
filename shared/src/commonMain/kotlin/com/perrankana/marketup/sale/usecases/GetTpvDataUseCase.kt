package com.perrankana.marketup.sale.usecases


import com.perrankana.marketup.sale.models.TpvEvent
import com.perrankana.marketup.sale.repositories.TpvEventRepository
import com.perrankana.marketup.stock.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetTpvDataUseCase {
    suspend operator fun invoke(): Result<Flow<Pair<TpvEvent, List<String>>>>
}

class GetTpvDataUseCaseImpl(
    private val tpvEventRepository: TpvEventRepository,
    private val categoryRepository: CategoryRepository
) : GetTpvDataUseCase {
    override suspend fun invoke(): Result<Flow<Pair<TpvEvent, List<String>>>> = kotlin.runCatching {
        println("[GetTpvDataUseCase]")
        categoryRepository.getCategories().map {
            val tpvEvent = tpvEventRepository.getTpvEvent()
            println("[GetTpvDataUseCase] tpvEvent = $tpvEvent")
            tpvEvent to it
        }
    }
}