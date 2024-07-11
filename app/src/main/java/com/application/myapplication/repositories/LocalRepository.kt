package com.application.myapplication.repositories

import com.application.myapplication.data.repository.AppDao
import com.application.myapplication.util.Resource
import com.application.myapplication.viewmodel.UserNotification
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val appDao: AppDao
) {
    suspend fun getUserNotificationsByUserIdFromDb(userId: Int): Resource<List<UserNotification>> {
        return try {
            val notifications = appDao.getNotificationsByUserId(userId)
            Resource.Success(notifications)
        } catch (e: Exception) {
            Resource.Failure(e.toString())
        }
    }

    suspend fun deleteUserNotificationFromDb(notification: UserNotification) {
        appDao.deleteNotification(notification)
    }

    suspend fun updateUserNotificationFromDb(notification: UserNotification) {
        appDao.updateNotification(notification)
    }

    suspend fun getNotificationBadgeCountFromDb(userId: Int): Int {
        return appDao.getUnreadNotificationCount(userId)
    }
}

