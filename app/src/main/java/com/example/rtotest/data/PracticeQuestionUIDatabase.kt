package com.example.rtotest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rtotest.model.PracticeQuestionUI

@Database(entities = [PracticeQuestionUI::class], version = 1, exportSchema = false)
abstract class PracticeQuestionUIDatabase : RoomDatabase() {

    abstract fun practiceQuestionUserDao(): PracticeQuestionUIDao

    companion object {
        @Volatile
        private var INSTANCE: PracticeQuestionUIDatabase? = null

        fun getDatabase(context: Context) = INSTANCE ?: synchronized(this) {
            return Room.databaseBuilder(
                    context.applicationContext,
                    PracticeQuestionUIDatabase::class.java,
                    "user_database"
            ).build().also { INSTANCE = it }
        }
    }
}
