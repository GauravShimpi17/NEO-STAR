package com.example.registration.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.registration.data.dao.AddressDataDao
import com.example.registration.data.dao.PrimaryDataDao
import com.example.registration.data.dao.ProfessionalInfoDao
import com.example.registration.data.model.AddressData
import com.example.registration.data.model.PrimaryData
import com.example.registration.data.model.ProfessionalData

@Database(entities = [PrimaryData::class, ProfessionalData::class, AddressData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun primaryDataDao(): PrimaryDataDao
    abstract fun professionalDataDao(): ProfessionalInfoDao
    abstract fun addressDao(): AddressDataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
