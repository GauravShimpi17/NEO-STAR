package com.example.registration.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registration.model.PrimaryData
import com.example.registration.repo.UserRepo

class UserViewModel(private val repo: UserRepo) : ViewModel() {

    private val _saveStatus = MutableLiveData<Boolean>()
    val saveStatus: LiveData<Boolean> get() = _saveStatus

    private val _userLiveData = MutableLiveData<List<PrimaryData>>()
    val userLiveData: LiveData<List<PrimaryData>> get() = _userLiveData

//    private var _userData =  MutableLiveData<PrimaryData>()
    val userData= ObservableField(PrimaryData())


    fun saveUser(primaryData: PrimaryData) {
        if (validateData(primaryData)) {
            repo.saveUser(primaryData)
            _saveStatus.postValue(true)
        }
    }

    fun validateData(primaryData: PrimaryData):Boolean{
        return true
    }
}