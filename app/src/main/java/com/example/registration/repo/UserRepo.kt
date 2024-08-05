package com.example.registration.repo

import androidx.lifecycle.LiveData
import com.example.registration.model.PrimaryData

interface UserRepo {
    fun saveUser(primaryData: PrimaryData)

    fun getUsers() :List<PrimaryData>
}