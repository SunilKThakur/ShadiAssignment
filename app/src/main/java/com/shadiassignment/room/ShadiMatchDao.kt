package com.shadiassignment.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shadiassignment.models.ShadiMatchDBModel
import androidx.room.Update




@Dao
interface ShadiMatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertShadiUsers(list: List<ShadiMatchDBModel>)

    @Query("SELECT * FROM ShadiMatchDBModel")
     suspend fun getShadiUsers(): List<ShadiMatchDBModel>

    @Update
    suspend fun update(model: ShadiMatchDBModel)
}