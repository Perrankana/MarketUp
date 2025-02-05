package com.perrankana.marketup.android.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perrankana.marketup.events.AskStartEvent
import com.perrankana.marketup.events.EditEvent
import com.perrankana.marketup.events.EventSceneData
import com.perrankana.marketup.events.NewEvent
import com.perrankana.marketup.events.StartEvent
import com.perrankana.marketup.events.models.Event
import com.perrankana.marketup.events.models.Status
import com.perrankana.marketup.events.useCases.GetCurrentEventUseCase
import com.perrankana.marketup.events.useCases.SaveCurrentEventUseCase
import com.perrankana.marketup.events.useCases.StartCurrentEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentEventViewModel @Inject constructor(
    val getCurrentEventUseCase: GetCurrentEventUseCase,
    val saveCurrentEvent: SaveCurrentEventUseCase,
    val startCurrentEvent: StartCurrentEventUseCase
) : ViewModel() {

    private val _currentEventData: MutableLiveData<EventSceneData> = MutableLiveData(NewEvent)
    val currentEventData: LiveData<EventSceneData>
        get() = _currentEventData

    init {
        getCurrentEventData()
    }

    private fun getCurrentEventData() {
        viewModelScope.launch {
            getCurrentEventUseCase().fold(
                onSuccess = {
                    when (it.first.status){
                        Status.Ended -> Log.e(TAG, "[getCurrentEventData] event is Ended")
                        Status.NotStarted -> _currentEventData.value = EditEvent(it.first)
                        Status.Started -> _currentEventData.value = StartEvent(it.second!!)
                    }
                },
                onFailure = {
                    Log.e(TAG, "[getCurrentEventData]", it)
                    _currentEventData.value = NewEvent
                }
            )
        }
    }

    fun saveEvent(event: Event) {
        viewModelScope.launch {
            saveCurrentEvent(event).fold(
                onSuccess = {
                    _currentEventData.value = AskStartEvent(it)
                },
                onFailure = {
                    Log.e(TAG, "[saveEvent]", it)
                }
            )
        }
    }

    fun startEvent(event: Event) {
        viewModelScope.launch {
            startCurrentEvent(event).fold(
                onSuccess = {
                    _currentEventData.value = StartEvent(it)
                },
                onFailure = {
                    Log.e(TAG, "[startEvent]", it)
                }
            )
        }
    }
    companion object{
        const val TAG = "CurrentEvent"
    }
}