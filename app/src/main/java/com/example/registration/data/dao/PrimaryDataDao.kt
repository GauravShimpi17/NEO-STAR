package com.example.registration.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.registration.data.model.PrimaryData
import com.example.registration.data.model.PrimaryDataWithDetails

@Dao
interface PrimaryDataDao {

    @Upsert
    fun savePrimaryData(primaryData: PrimaryData):Long

    @Transaction
    @Query("SELECT * FROM primary_info WHERE primaryInfoId = :id")
    fun getPrimaryDataWithDetails(id: String): LiveData<PrimaryDataWithDetails>

    @Query("SELECT * FROM primary_info")
    fun getAllUsers(): LiveData<List<PrimaryData>>
}