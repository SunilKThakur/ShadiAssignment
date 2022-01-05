package com.shadiassignment.utils

import com.shadiassignment.models.ShadiMatchDBModel

sealed class ShadiEvent {
    data class Success(val usersList:List<ShadiMatchDBModel>): ShadiEvent()
    data class Failure(val errorText: String): ShadiEvent()
    object Loading: ShadiEvent()
    object Empty: ShadiEvent()
}