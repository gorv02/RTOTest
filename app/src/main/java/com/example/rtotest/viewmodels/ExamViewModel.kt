package com.example.rtotest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rtotest.dataGenerator.listPracticeQueAns
import com.example.rtotest.model.PracticeQuestion

class ExamViewModel : ViewModel() {

    val listExamQA: List<PracticeQuestion> = listPracticeQueAns(10)

    private val _index = MutableLiveData(0)
    val index: LiveData<Int>
        get() = _index

    fun indexValue() = _index.value ?: 0
    fun setIndex(value: Int) {
        _index.value = value
    }

    fun increaseIndex() {
        _index.value = _index.value?.plus(1)
    }

    private val _listOfSelectedOptions = MutableLiveData<MutableList<Int?>>(
            MutableList(listExamQA.size) { null }
    )

    fun getSelectedOption(index: Int) = _listOfSelectedOptions.value?.get(index)
    fun getListOfSelectedOptions() = _listOfSelectedOptions.value
            ?: MutableList(listExamQA.size) { null }

    fun addSelectedOption(index: Int, option: Int?) {
        _listOfSelectedOptions.value?.let {
            it[index] = option
        }
    }

    fun resetListOfSelectedOptions() {
        _listOfSelectedOptions.value = MutableList(listExamQA.size) { null }
    }

    private val _listOfIsCorrect = MutableLiveData<MutableList<Boolean?>>(
            MutableList(listExamQA.size) { null }
    )

    fun getListOfIsCorrect() = _listOfIsCorrect.value ?: MutableList(listExamQA.size) { null }
    fun addIsCorrect(index: Int, isCorrect: Boolean?) {
        _listOfIsCorrect.value?.let {
            it[index] = isCorrect
        }
    }

    fun resetListOfIsCorrect() {
        _listOfIsCorrect.value = MutableList(listExamQA.size) { null }
    }

    private val _timeLeftInMillis = MutableLiveData<Long>()
    val timeLeftInMillis: LiveData<Long>
        get() = _timeLeftInMillis

    fun getTimeLeftInMillis() = _timeLeftInMillis.value ?: 30000
    fun setTimeLeftInMillis(value: Long) {
        _timeLeftInMillis.value = value
    }
}