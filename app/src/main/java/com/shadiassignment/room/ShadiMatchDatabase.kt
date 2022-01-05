package com.example.mymvvmdemo.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shadiassignment.models.*
import com.shadiassignment.room.ShadiMatchDao

@Database(version = 1, entities = [ShadiMatchDBModel::class])
abstract class ShadiMatchDatabase : RoomDatabase() {

    companion object {
        fun get(application: Application): ShadiMatchDatabase {
            return Room.databaseBuilder(application, ShadiMatchDatabase::class.java, "WEATHER").build()
        }
    }

    abstract fun getShadiMatchDao(): ShadiMatchDao
}