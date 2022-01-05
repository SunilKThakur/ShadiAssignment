package com.code.ltassignment.di

import com.shadiassignment.network.ShadiApi
import com.shadiassignment.repository.DefaultMainRepository
import com.shadiassignment.repository.MainRepository
import com.shadiassignment.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShadiApi(): ShadiApi =Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ShadiApi::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(api: ShadiApi): MainRepository = DefaultMainRepository(api)

}