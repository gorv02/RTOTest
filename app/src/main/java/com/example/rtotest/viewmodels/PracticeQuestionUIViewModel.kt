package com.example.rtotest.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rtotest.dataGenerator.listPracticeQueAns
import com.example.rtotest.model.PracticeQuestion
import com.example.rtotest.model.PracticeQuestionUI
import com.example.rtotest.repository.PracticeQuestionUIRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PracticeQuestionUIViewModel(
        application: Application
) : AndroidViewModel(application) {

    private val mUIRepository = PracticeQuestionUIRepo(application)

    val listPracticeQA: List<PracticeQuestion> = listPracticeQueAns(301)

    val questionsListUILive = mUIRepository.readAllDataAsLiveData

    private val hasPreviousSession = MutableLiveData(false)
    fun hasPreviousSessionValue() = hasPreviousSession.value ?: false
    fun setPreviousSession(value: Boolean) {
        hasPreviousSession.value = value
    }

    private val index = MutableLiveData(0)
    fun index() = index
    fun indexValue() = index.value ?: 0
    fun setIndex(value: Int) {
        index.value = value
    }

    fun increaseIndex() {
        index.value = index.value?.plus(1)
    }

    fun decreaseIndex() {
        index.value = index.value?.minus(1)
    }

    private val answeredCorrect = MutableLiveData(0)
    fun answeredCorrect() = answeredCorrect
    fun answeredCorrectValue() = answeredCorrect.value ?: 0
    fun increaseCorrect() {
        answeredCorrect.value = answeredCorrect.value?.plus(1)
    }

    fun setAnsweredCorrect(value: Int) {
        answeredCorrect.value = value
    }

    private var answeredIncorrect = MutableLiveData(0)
    fun answeredIncorrect() = answeredIncorrect
    fun answeredIncorrectValue() = answeredIncorrect.value ?: 0
    fun increaseIncorrect() {
        answeredIncorrect.value = answeredIncorrect.value?.plus(1)
    }

    fun setAnsweredIncorrect(value: Int) {
        answeredIncorrect.value = value
    }

    private val currentItem = MutableLiveData<PracticeQuestionUI>()
    fun currentItem() = currentItem.value
            ?: PracticeQuestionUI(0, false, null)

    fun updateCurrentItem(index: Int) {
        if (questionsListUILive.value?.isNotEmpty() == true)
            currentItem.value = questionsListUILive.value?.get(index)
    }

    fun addDefaultList(practiceQuestionUIList: List<PracticeQuestionUI>) {
        viewModelScope.launch(Dispatchers.IO) {
            mUIRepository.addDefaultList(
                    practiceQuestionUIList
            )
        }
    }

    fun update(practiceQuestionUI: PracticeQuestionUI) {
        viewModelScope.launch(Dispatchers.IO) {
            mUIRepository.update(
                    practiceQuestionUI
            )
        }
    }

    fun deleteData() {
        viewModelScope.launch(Dispatchers.IO) {
            mUIRepository.deleteAll()
        }
    }
}