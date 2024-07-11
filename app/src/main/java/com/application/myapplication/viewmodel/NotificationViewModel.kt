package com.application.myapplication.viewmodel


import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.application.myapplication.repositories.LocalRepository
import com.application.myapplication.util.Resource
import com.application.myapplication.util.getUserIdFromSharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Notification(val id: Int, val title: String, val message: String)

@Entity(tableName = "user_notifications")
data class UserNotification(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val userId: Int,
    val title: String,
    val message: String,
    val timestamp: Long
)



@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _userNotifications = MutableStateFlow<Resource<List<UserNotification>>>(Resource.Idle)
    val userNotifications: StateFlow<Resource<List<UserNotification>>> = _userNotifications

    private val _badgeCountState = MutableStateFlow(value = 0)
    val badgeCount: StateFlow<Int> = _badgeCountState.asStateFlow()

    init {
        getNotificationsByUserId()
        getBadgeCount()
    }

    private fun getNotificationsByUserId() = viewModelScope.launch {
        val userId = getUserIdFromSharedPref(sharedPreferences)
        val result = localRepository.getUserNotificationsByUserIdFromDb(userId)
        _userNotifications.value = result
    }

    fun updateBadgeCount(newCount: Int) = viewModelScope.launch {
        _badgeCountState.value = newCount
    }

    fun deleteUserNotification(notification: UserNotification) = viewModelScope.launch {
        localRepository.deleteUserNotificationFromDb(notification)
        getNotificationsByUserId()
    }

    fun updateUserNotification(notification: UserNotification) = viewModelScope.launch {
        localRepository.updateUserNotificationFromDb(notification)
        getNotificationsByUserId()
    }

    private fun getBadgeCount() = viewModelScope.launch {
        val userId = getUserIdFromSharedPref(sharedPreferences)
        val result = localRepository.getNotificationBadgeCountFromDb(userId)
        _badgeCountState.value = result
    }

    private fun getUserIdFromSharedPref(sharedPreferences: SharedPreferences): Int {
        // Assuming userId is stored as an integer in SharedPreferences
        return sharedPreferences.getInt("user_id", -1)
    }
}


























//class NotificationViewModel : ViewModel() {
//    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
//    val notifications: StateFlow<List<Notification>> = _notifications
//
//    init {
//        loadNotifications()
//    }
//
//    private fun loadNotifications() {
//        viewModelScope.launch {
//            // Simulate a network or database call
//            _notifications.value = listOf(
//                Notification(1, "Welcome", "Thanks for using our app!"),
//                Notification(2, "Update", "Version 2.0 is now available.")
//            )
//        }
//    }
//}