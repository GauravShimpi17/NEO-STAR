package com.example.registration.data.model

import androidx.room.Embedded
import androidx.room.Relation


data class PrimaryDataWithDetails(
    @Embedded val primaryData: PrimaryData,
    @Relation(
        parentColumn = "primaryInfoId",
        entityColumn = "professionalId"
    )
    val professionalData: ProfessionalData?,
    @Relation(
        parentColumn = "primaryInfoId",
        entityColumn = "addressId"
    )
    val addressData: AddressData?
)