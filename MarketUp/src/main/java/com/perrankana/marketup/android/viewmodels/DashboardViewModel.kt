package com.perrankana.marketup.android.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perrankana.marketup.dashboard.DashboardData
import com.perrankana.marketup.events.useCases.GetCurrentEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(val getCurrentEventUseCase: GetCurrentEventUseCase) : ViewModel() {

    private val _dashboardData: MutableLiveData<DashboardData> = MutableLiveData(DashboardData())
    val dashboardData: LiveData<DashboardData>
        get() = _dashboardData

    init {
        getDashBoardData()
    }

    private fun getDashBoardData() {
        viewModelScope.launch {
            getCurrentEventUseCase().fold(
                onSuccess = {
                    _dashboardData.value = DashboardData(it.name)
                },
                onFailure = {
                    _dashboardData.value = DashboardData()
                }
            )
        }
    }
}