package com.example.rtotest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rtotest.dataGenerator.listQueAns
import com.example.rtotest.dataGenerator.listTrafficIcons
import com.example.rtotest.model.Question
import com.example.rtotest.model.TrafficSigns

class HomeViewModel : ViewModel() {
    private val _listOfQuestionAnswer =
            MutableLiveData<List<Question>>(listQueAns(300))
    val listOfQuestion: LiveData<List<Question>>
        get() = _listOfQuestionAnswer

    private val _listOfTrafficSigns =
            MutableLiveData<List<TrafficSigns>>(listTrafficIcons(100))
    val listOfTrafficSigns: LiveData<List<TrafficSigns>>
        get() = _listOfTrafficSigns

}