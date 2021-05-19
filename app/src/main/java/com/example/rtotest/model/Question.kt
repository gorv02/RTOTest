package com.example.rtotest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
        val questionNo: Int,
        val que: String?,
        val ans: String?
) : Parcelable