package com.example.rtotest.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rtotest.dataGenerator.listPracticeQueAns
import com.example.rtotest.model.PracticeQuestion

class ExamViewModel : ViewModel() {

    val listExamQA: List<PracticeQuestion> = listPracticeQueAns(10)

    private val index = MutableLiveData(0)
    fun index() = index
    fun indexValue() = index.value ?: 0
    fun setIndex(value: Int) {
        index.value = value
    }

    fun increaseIndex() {
        index.value = index.value?.plus(1)
    }

    private val listOfSelectedOptions = MutableLiveData<MutableList<Int?>>(
            MutableList(listExamQA.size) { null }
    )

    fun getSelectedOption(index: Int) = listOfSelectedOptions.value?.get(index)
    fun getListOfSelectedOptions() = listOfSelectedOptions.value
            ?: MutableList(listExamQA.size) { null }

    fun addSelectedOption(index: Int, option: Int?) {
        listOfSelectedOptions.value?.let {
            it[index] = option
        }
    }

    fun resetListOfSelectedOptions() {
        listOfSelectedOptions.value = MutableList(listExamQA.size) { null }
    }

    private val listOfIsCorrect = MutableLiveData<MutableList<Boolean?>>(
            MutableList(listExamQA.size) { null }
    )

    fun getListOfIsCorrect() = listOfIsCorrect.value ?: MutableList(listExamQA.size) { null }
    fun addIsCorrect(index: Int, isCorrect: Boolean?) {
        listOfIsCorrect.value?.let {
            it[index] = isCorrect
        }
    }

    fun resetListOfIsCorrect() {
        listOfIsCorrect.value = MutableList(listExamQA.size) { null }
    }

    var timeLeftInMillis = MutableLiveData<Long>()
    fun getTimeLeftInMillis() = timeLeftInMillis.value ?: 30000
    fun setTimeLeftInMillis(value: Long){
        timeLeftInMillis.value = value
    }
}