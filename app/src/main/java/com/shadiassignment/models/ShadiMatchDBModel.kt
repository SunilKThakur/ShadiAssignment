package com.shadiassignment.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShadiMatchDBModel(
    @PrimaryKey(autoGenerate = true)
    val shadiUserId: Int=0,
    val email: String,
    val gender: String,
    @Embedded val name: Name,
    val nat: String,
    val phone: String,
    @Embedded val picture: Picture,
    var isAccepted:Int=0
)