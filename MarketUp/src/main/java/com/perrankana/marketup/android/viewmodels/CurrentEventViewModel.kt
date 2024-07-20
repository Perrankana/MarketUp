package com.perrankana.marketup.android.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perrankana.marketup.DashboardData
import com.perrankana.marketup.useCases.GetCurrentEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentEventViewModel @Inject constructor(val getCurrentEventUseCase: GetCurrentEventUseCase) : ViewModel() {

    private val _dashboardData: MutableLiveData<DashboardData> = MutableLiveData(DashboardData())
    val currentEventData: LiveData<DashboardData>
        get() = _dashboardData
    fun getDashBoardData() {
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