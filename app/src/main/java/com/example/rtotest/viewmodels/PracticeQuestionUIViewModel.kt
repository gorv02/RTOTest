package com.example.rtotest.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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

    private val _hasPreviousSession = MutableLiveData(false)
    fun hasPreviousSessionValue() = _hasPreviousSession.value ?: false
    fun setPreviousSession(value: Boolean) {
        _hasPreviousSession.value = value
    }

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

    fun decreaseIndex() {
        _index.value = _index.value?.minus(1)
    }

    private val _answeredCorrect = MutableLiveData(0)
    val answeredCorrect: LiveData<Int>
        get() = _answeredCorrect

    fun answeredCorrectValue() = _answeredCorrect.value ?: 0
    fun increaseCorrect() {
        _answeredCorrect.value = _answeredCorrect.value?.plus(1)
    }

    fun setAnsweredCorrect(value: Int) {
        _answeredCorrect.value = value
    }

    private var _answeredIncorrect = MutableLiveData(0)
    val answeredIncorrect: LiveData<Int>
        get() = _answeredIncorrect

    fun answeredIncorrectValue() = _answeredIncorrect.value ?: 0
    fun increaseIncorrect() {
        _answeredIncorrect.value = _answeredIncorrect.value?.plus(1)
    }

    fun setAnsweredIncorrect(value: Int) {
        _answeredIncorrect.value = value
    }

    private val _currentItem = MutableLiveData<PracticeQuestionUI>()
    fun currentItem() = _currentItem.value
            ?: PracticeQuestionUI(0, false, null)

    fun updateCurrentItem(index: Int) {
        if (questionsListUILive.value?.isNotEmpty() == true)
            _currentItem.value = questionsListUILive.value?.get(index)
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