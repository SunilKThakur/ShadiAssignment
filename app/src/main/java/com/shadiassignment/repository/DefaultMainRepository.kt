package com.shadiassignment.repository

import com.shadiassignment.network.ShadiApi
import com.code.ltassignment.utils.Resource
import com.shadiassignment.models.ShadiMatchRequestModel
import com.shadiassignment.models.ShadiMatchResponseModel
import okhttp3.ResponseBody
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: ShadiApi
): MainRepository {
    override suspend fun getUsersList(shadiMatchRequestModel: ShadiMatchRequestModel): Resource<ShadiMatchResponseModel> {
        return try {
            val response=api.getRates(shadiMatchRequestModel.results)
            val result=response.body()
            if(response.isSuccessful && result!=null)
            {
                Resource.Success(result)
            }
            else{
                Resource.Error(response.message())
            }
        }catch (error:Exception)
        {
            Resource.Error(error.message?:"An error occurred")
        }
    }
}