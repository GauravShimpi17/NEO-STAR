package com.example.registration.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.registration.data.model.PrimaryData
import com.example.registration.data.model.ProfessionalData

@Dao
interface ProfessionalInfoDao {

    @Upsert
    fun insertProfessionalInfo(professionalData: ProfessionalData)

}