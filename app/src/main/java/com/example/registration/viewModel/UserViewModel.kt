package com.example.registration.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registration.data.model.AddressData
import com.example.registration.data.model.PrimaryData
import com.example.registration.data.model.ProfessionalData
import com.example.registration.data.repo.UserRepo

class UserViewModel(private val repo: UserRepo) : ViewModel() {

    private val _saveStatus = MutableLiveData<Boolean>()
    val saveStatus: LiveData<Boolean> get() = _saveStatus

    private val _userLiveData = MutableLiveData<List<PrimaryData>>()
    val userLiveData: LiveData<List<PrimaryData>> get() = _userLiveData

//    private var _userData =  MutableLiveData<PrimaryData>()
    val userPrimaryData= ObservableField(PrimaryData())
    val userProfessionalData= ObservableField(ProfessionalData())
    val userAddressData= ObservableField(AddressData())


    fun saveUser(primaryData: PrimaryData, professionalData: ProfessionalData, addressData: AddressData) {
        if (validateData(primaryData)) {
            repo.saveUser(primaryData, professionalData, addressData)
            _saveStatus.postValue(true)
        }
    }

    private fun validateData(primaryData: PrimaryData):Boolean{
        return true
    }

    fun setImage(byteArray: ByteArray) {
        userPrimaryData.set(userPrimaryData.get()?.copy(
            image = byteArray
        ))
    }
}