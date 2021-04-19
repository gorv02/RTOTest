package com.example.rtotest.model

data class PracticeQuestion (
    val id: Int,
    val que: String,
    val options: List<String>,
    val ansId: Int
    )