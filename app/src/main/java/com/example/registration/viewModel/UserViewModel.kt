package com.example.registration.viewModel

import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registration.data.model.AddressData
import com.example.registration.data.model.PrimaryData
import com.example.registration.data.model.PrimaryDataWithDetails
import com.example.registration.data.model.ProfessionalData
import com.example.registration.data.model.error.AddressDataError
import com.example.registration.data.model.error.ProfessionalDataError
import com.example.registration.data.model.error.PrimaryDataError
import com.example.registration.data.repo.UserRepo
import com.example.registration.view.util.enumClass.Designation
import com.example.registration.view.util.enumClass.Education
import com.example.registration.view.util.enumClass.State
import com.example.registration.view.util.isAddressValid
import com.example.registration.view.util.isEmailValid
import com.example.registration.view.util.isExpValid
import com.example.registration.view.util.isGradeValid
import com.example.registration.view.util.isNameValid
import com.example.registration.view.util.isPasswordValid
import com.example.registration.view.util.isPhoneNumberValid
import com.example.registration.view.util.isPinCodeValid
import com.example.registration.view.util.matchesFirstEnumConstant

class UserViewModel(private val repo: UserRepo) : ViewModel() {

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> get() = _gender

    private val _primaryDataError = MutableLiveData(PrimaryDataError())
    val primaryDataError: LiveData<PrimaryDataError> = _primaryDataError

    private val _addressDataError = MutableLiveData(AddressDataError())
    val addressDataError: LiveData<AddressDataError> = _addressDataError

    private val _professionalDataError = MutableLiveData(ProfessionalDataError())
    val professionalDataError: LiveData<ProfessionalDataError> = _professionalDataError

    val userPrimaryData = ObservableField(PrimaryData())
    val userProfessionalData = ObservableField(ProfessionalData())
    val userAddressData = ObservableField(AddressData())

    fun saveUser(
        primaryData: PrimaryData, professionalData: ProfessionalData, addressData: AddressData
    ) {
        if (validatePrimaryData(primaryData)) {
            repo.saveUser(primaryData, professionalData, addressData)
        }
    }

    fun getAllUser(): LiveData<List<PrimaryDataWithDetails>> {
        return repo.getUsers()
    }

    fun validatePrimaryData(
        primaryData: PrimaryData
    ): Boolean {
        var isValid = true
        val error = PrimaryDataError(
            firstNameError = if (!primaryData.firstName.isNameValid()) {
                Log.d("hello","Upper")
                isValid = false
                Log.d("hello","lower")
                "First name must be more than 3 characters and contain only letters."

            } else null,
            lastNameError = if (!primaryData.lastName.isNameValid()) {
                isValid = false
                "Last name must be more than 3 characters and contain only letters."
            } else null,
            emailError = if (!primaryData.email.isEmailValid()) {
                isValid = false
                "Invalid email address."
            } else null,
            phoneNumberError = if (!primaryData.phoneNumber.isPhoneNumberValid()) {
                isValid = false
                "Phone number must be 10 digits."
            } else null,
            passwordError = if (!primaryData.password.isPasswordValid()) {
                isValid = false
                "Password must be at least 8 characters long and include one uppercase letter, one number, and one special character."
            } else null,
            confirmPasswordError = if (primaryData.password != primaryData.confirmPassword) {
                isValid = false
                "Passwords do not match."
            } else null
        )
        _primaryDataError.postValue(error)
        return isValid
    }

    fun validateAddressData(addressData: AddressData) : Boolean{
        var isValid = true

        val error = AddressDataError(
            addressError = if (!addressData.address.isAddressValid()) {
                isValid = false
                "Address must be more than 3 characters."
            } else null,
            landmarkError = if (!addressData.landmark.isAddressValid()){
                isValid = false
                "Landmark must be more than 3 characters"
            }else null,
            cityNameError = if (!addressData.city.isNameValid()){
                isValid = false
                "City Name must be more than 3 characters and contain only letters."
            }else null,
            pinCodeError = if (!addressData.pinCode.isPinCodeValid()){
                isValid = false
                "PinCode must be 6 numbers."
            }else null,
            stateError = if (addressData.state.matchesFirstEnumConstant<State>()){
                isValid = false
                "State not selected"
            }else null
        )
        _addressDataError.postValue(error)
        return isValid
    }

    fun validateExperienceData(professionalData: ProfessionalData) : Boolean{
        var isValid = true

        val error = ProfessionalDataError(
            education = if (professionalData.education.matchesFirstEnumConstant<Education>()){
                isValid = false
                "Education not selected"
            }else null,
            designation = if (professionalData.designation.matchesFirstEnumConstant<Designation>()){
                isValid = false
                "Designation not selected"
            }else null,
            grade = if (professionalData.grade.isGradeValid()){
                isValid = false
                "Grade not valid"
            }else null,
            experience = if (professionalData.experience.isExpValid()){
                isValid = false
                "experience not added"
            }else null

        )
        _professionalDataError.postValue(error)
        return isValid
    }

    fun setImageUri(uri: Uri) {
        userPrimaryData.set(
            userPrimaryData.get()?.copy(
                imageUri = uri.toString()
            )
        )
    }

    fun setGender(gender: String) {
        _gender.value = gender
        userPrimaryData.set(
            userPrimaryData.get()?.copy(
                gender = gender
            )
        )
    }


}