package com.shadiassignment.repository

import com.code.ltassignment.utils.Resource
import com.shadiassignment.models.ShadiMatchRequestModel
import com.shadiassignment.models.ShadiMatchResponseModel

interface MainRepository {
    suspend fun getUsersList(shadiMatchRequestModel:ShadiMatchRequestModel):Resource<ShadiMatchResponseModel>
}