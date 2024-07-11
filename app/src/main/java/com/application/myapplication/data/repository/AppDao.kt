package com.application.myapplication.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.application.myapplication.viewmodel.UserNotification

@Dao
interface AppDao {
    @Query("SELECT COUNT(*) FROM user_notifications WHERE userId = :userId AND read = 0")
    suspend fun getUnreadNotificationCount(userId: Int): Int

    @Query("SELECT * FROM user_notifications WHERE userId = :userId")
    suspend fun getNotificationsByUserId(userId: Int): List<UserNotification>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: UserNotification)

    @Update
    suspend fun updateNotification(notification: UserNotification)

    @Delete
    suspend fun deleteNotification(notification: UserNotification)
}
