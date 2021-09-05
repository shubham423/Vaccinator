package com.example.vaccinator.ui.vaccination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vaccinator.data.models.SlotResponse
import com.example.vaccinator.data.models.SlotResponse.*
import com.example.vaccinator.data.repository.MainRepository
import kotlinx.coroutines.launch

class VaccinationViewModel : ViewModel() {
    var repo = MainRepository()
    private var _slotsList = MutableLiveData<List<Session>>()
    var slotsList: LiveData<List<Session>> = _slotsList

    fun getFeed(pincode: String, data: String) {
        viewModelScope.launch {
            repo.getFeed(pincode, data).let {
                _slotsList.postValue(it.body()?.sessions)
            }
        }
    }
}