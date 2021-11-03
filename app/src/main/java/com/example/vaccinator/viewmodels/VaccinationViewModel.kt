package com.example.vaccinator.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vaccinator.data.models.DistrictsResponse.*
import com.example.vaccinator.data.models.SlotResponse.*
import com.example.vaccinator.data.models.StatesResponse.State
import com.example.vaccinator.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VaccinationViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {


    private var _slotsList = MutableLiveData<List<Session>>()
    var slotsList: LiveData<List<Session>> = _slotsList

    private var _slotsListByDistrict = MutableLiveData<List<Session>>()
    var slotsListByDistrict: LiveData<List<Session>> = _slotsListByDistrict


    private var _statesList=MutableLiveData<List<State>>()
    var statesList: LiveData<List<State>> = _statesList

    private var _districtsList=MutableLiveData<List<District>>()
    var districtsList: LiveData<List<District>> = _districtsList

    private var _selectedStateId=MutableLiveData<String>()
    var selectedStateId: LiveData<String> = _selectedStateId


    fun getFeed(pincode: String, date: String) {
        viewModelScope.launch {
            repo.getFeed(pincode, date).let {
                _slotsList.postValue(it.body()?.sessions)
            }
        }
    }

    fun getStates() {
        viewModelScope.launch {
            repo.getStates().let {
                _statesList.postValue(it.body()?.states)
            }
        }
    }

    fun getDistricts(stateId: String) {
        viewModelScope.launch {
            repo.getDistricts(stateId).let {
                Log.d("viewmodel","value of response ${it.body()}")
                _districtsList.postValue(it.body()?.districts)
            }
        }
    }
     fun setSelectedState(state: String){
        _selectedStateId.value=state
    }

    fun getSlotsByDistrict(districtId: String, date: String) {
        viewModelScope.launch {
            repo.getSlotsByDistrict(districtId, date).let {
                _slotsListByDistrict.postValue(it.body()?.sessions)
            }
        }
    }
}