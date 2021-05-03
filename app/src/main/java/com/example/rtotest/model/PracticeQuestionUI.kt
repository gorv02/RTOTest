package com.example.rtotest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "practice_question_user")
data class PracticeQuestionUI(
    @PrimaryKey(autoGenerate = true)
    val questionNo : Int,
    val isAnswered: Boolean,
    val selectedOption: Int?
)