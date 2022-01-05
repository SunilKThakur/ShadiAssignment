package com.shadiassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.ltassignment.utils.Resource
import com.example.mymvvmdemo.room.ShadiMatchDatabase
import com.shadiassignment.models.ShadiMatchDBModel
import com.shadiassignment.models.ShadiMatchRequestModel
import com.shadiassignment.repository.MainRepository
import com.shadiassignment.utils.ShadiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShadiViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _uiState  = MutableStateFlow<ShadiEvent>(ShadiEvent.Empty)
    val uiState: StateFlow<ShadiEvent> = _uiState


    fun getShadiMatchList(
        shadiMatchDataBase: ShadiMatchDatabase,
        isOnline:Boolean,
        shadiMatchRequestModel: ShadiMatchRequestModel
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if(isOnline) {
                _uiState.value = ShadiEvent.Loading
                when (val rateResponse = repository.getUsersList(shadiMatchRequestModel)) {
                    is Resource.Error -> {
                        _uiState.value = ShadiEvent.Failure(rateResponse.message!!)
                    }
                    is Resource.Success -> {
                        val shadiUsersList: MutableList<ShadiMatchDBModel> = ArrayList()
                        rateResponse.data?.results?.map {
                            shadiUsersList.add(
                                ShadiMatchDBModel(
                                    email = it.email,
                                    gender = it.gender,
                                    name = it.name,
                                    nat = it.nat,
                                    phone = it.phone,
                                    picture = it.picture
                                )
                            )
                        }
                        shadiMatchDataBase.getShadiMatchDao().insertShadiUsers(shadiUsersList)
                        _uiState.value = ShadiEvent.Success(
                            shadiMatchDataBase.getShadiMatchDao().getShadiUsers()
                        )
                    }
                }
            }
            else
            {
                _uiState.value = ShadiEvent.Success(
                    shadiMatchDataBase.getShadiMatchDao().getShadiUsers()
                )
            }
        }
    }
}

