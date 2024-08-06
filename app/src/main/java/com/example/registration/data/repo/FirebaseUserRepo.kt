package com.example.registration.data.repo

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.registration.data.model.PrimaryData
import com.google.firebase.firestore.FirebaseFirestore
/*

class FirebaseUserRepo() : UserRepo {

    private val database = FirebaseFirestore.getInstance().collection("primary_data")

    override fun saveUser(primaryData: PrimaryData) {
        database.document(primaryData.primaryInfoId.toString())
            .set(primaryData)
            .addOnCompleteListener {
            }

    }

    override fun getUsers(): List<PrimaryData> {
        TODO("Not yet implemented")
    }
}*/
