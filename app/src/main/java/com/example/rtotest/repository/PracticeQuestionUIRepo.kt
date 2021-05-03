package com.example.rtotest.repository

import android.content.Context
import com.example.rtotest.data.PracticeQuestionUIDatabase
import com.example.rtotest.model.PracticeQuestionUI

class PracticeQuestionUIRepo(context: Context) {

    private val practiceQuestionUserDatabase = PracticeQuestionUIDatabase.getDatabase(context)

    val readAllData = practiceQuestionUserDatabase
        .practiceQuestionUserDao()
        .getAll()

    suspend fun addDefaultList(practiceQuestionUIList: List<PracticeQuestionUI>) {
        practiceQuestionUserDatabase
            .practiceQuestionUserDao()
            .addDefaultList(practiceQuestionUIList)
    }

    suspend fun update(practiceQuestionUI: PracticeQuestionUI) {
        practiceQuestionUserDatabase
            .practiceQuestionUserDao()
            .update(practiceQuestionUI)
    }

    suspend fun deleteAll(){
        practiceQuestionUserDatabase
                .practiceQuestionUserDao()
                .deleteAll()
    }
}