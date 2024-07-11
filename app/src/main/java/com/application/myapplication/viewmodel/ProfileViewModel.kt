package com.application.myapplication.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//
//class ProfileViewModel : ViewModel() {
//
//    private val _name = MutableLiveData("")
//    val name: LiveData<String> get() = _name
//
//    private val _email = MutableLiveData("")
//    val email: LiveData<String> get() = _email
//
//    private val _phoneNumber = MutableLiveData("")
//    val phoneNumber: LiveData<String> get() = _phoneNumber
//
//    fun updateName(newName: String) {
//        _name.value = newName
//    }
//
//    fun updateEmail(newEmail: String) {
//        _email.value = newEmail
//    }
//
//    fun updatePhoneNumber(newPhoneNumber: String) {
//        _phoneNumber.value = newPhoneNumber
//    }
//}


import androidx.lifecycle.viewModelScope
import com.application.myapplication.data.repository.AuthRepository
import com.application.myapplication.model.User
import com.application.myapplication.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _userInfo = MutableStateFlow<Resource<User>?>(value = Resource.Idle)
    val userInfo: StateFlow<Resource<User>?> = _userInfo
    init {
        getUserInfoFromFirebase()
    }

    private fun getUserInfoFromFirebase() = viewModelScope.launch {
        _userInfo.value = Resource.Loading
        val result = repository.retrieveData()
        _userInfo.value = result
    }

    fun logout() {
        repository.logout()
        _userInfo.value = null
    }

    fun updateUserInfoFirebase(user: User) = viewModelScope.launch {
        repository.updateData(user = user)
    }
}