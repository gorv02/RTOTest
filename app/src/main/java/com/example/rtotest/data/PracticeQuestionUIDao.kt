package com.example.rtotest.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.rtotest.model.PracticeQuestionUI

@Dao
interface PracticeQuestionUIDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDefaultList(practiceQuestionUIList: List<PracticeQuestionUI>)

    @Update
    suspend fun update(practiceQuestionUI: PracticeQuestionUI)

    @Query("DELETE FROM practice_question_user")
    suspend fun deleteAll()

    @Query("SELECT * FROM practice_question_user ORDER BY questionNo ASC")
    fun getAll(): LiveData<List<PracticeQuestionUI>>

}