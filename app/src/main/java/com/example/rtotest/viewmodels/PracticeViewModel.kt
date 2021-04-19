package com.example.rtotest.viewmodels

import androidx.lifecycle.ViewModel
import com.example.rtotest.dataGenerator.listPracticeQueAns
import com.example.rtotest.model.PracticeQuestion

class PracticeViewModel : ViewModel() {
    var questionNo = 0
    var answeredCorrect = 0
    var answeredIncorrect = 0
    var listPracticeQA: List<PracticeQuestion> = listPracticeQueAns(10)
    var answeredList: MutableList<Boolean> = MutableList(10) { false }
    var listSelectedOpt: MutableList<Int?> = MutableList(10){null}
}