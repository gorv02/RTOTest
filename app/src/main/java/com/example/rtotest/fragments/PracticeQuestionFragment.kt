package com.example.rtotest.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rtotest.R
import com.example.rtotest.adapter.PracticeOptionsAdapter
import com.example.rtotest.databinding.FragmentPracticeQuestionBinding
import com.example.rtotest.model.PracticeQuestionUI
import com.example.rtotest.viewmodels.PracticeQuestionUIViewModel


class PracticeQuestionFragment : Fragment()
    , PracticeOptionsAdapter.ClickListener{

    private val mUIViewModel by lazy {
        ViewModelProvider(requireActivity()).get(PracticeQuestionUIViewModel::class.java)
    }
    private val binding by lazy { FragmentPracticeQuestionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.let { inState ->
            mUIViewModel.setIndex(inState.getInt("index"))
            mUIViewModel.setAnsweredCorrect(inState.getInt("ca"))
            mUIViewModel.setAnsweredIncorrect(inState.getInt("ia"))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)
        mUIViewModel.run {
            if (isPQTableInitialized.value != true) {
                val list = List(listPracticeQA.size) {
                    PracticeQuestionUI(0, false, null)
                }
                mUIViewModel.addDefaultList(list)
                isPQTableInitialized.value = true
            }
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_exam_and_practice , menu)
        menu.findItem(R.id.menu_item_timer).isVisible = false

        val queNo = menu.findItem(R.id.menu_item_question_no)
        mUIViewModel.index.observe(viewLifecycleOwner){
            queNo.title = "${it+1}/${mUIViewModel.listPracticeQA.size}"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mUIViewModel.questionsListUILive.observe(viewLifecycleOwner) {
            mUIViewModel.index.observe(viewLifecycleOwner) { index ->
                mUIViewModel.updateCurrentItem(mUIViewModel.indexValue())
                setQuestion(index)
                showRvOptions(index)

                when (index) {
                    0 -> {
                        binding.prevBtn.visibility = View.GONE
                        binding.nextBtn.visibility = View.VISIBLE
                    }
                    mUIViewModel.listPracticeQA.size - 1 -> {
                        binding.nextBtn.visibility = View.GONE
                        binding.prevBtn.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.prevBtn.visibility = View.VISIBLE
                        binding.nextBtn.visibility = View.VISIBLE
                    }
                }
            }

            mUIViewModel.answeredCorrect.observe(viewLifecycleOwner) { value ->
                binding.correct.text = value.toString()
            }

            mUIViewModel.answeredIncorrect.observe(viewLifecycleOwner) { value ->
                binding.incorrect.text = value.toString()
            }
        }

        binding.nextBtn.setOnClickListener {
            mUIViewModel.increaseIndex()
        }

        binding.prevBtn.setOnClickListener {
            mUIViewModel.decreaseIndex()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion(index: Int) {
        mUIViewModel.apply {
            binding.practiceQuestion.text = "${index + 1}. ${listPracticeQA[index].que}"
        }
    }

    private fun showRvOptions(index: Int) {
        binding.rvOptions.adapter = PracticeOptionsAdapter(
                    mUIViewModel
                            .listPracticeQA[index]
                            .options,
                    mUIViewModel
                            .listPracticeQA[index]
                            .ansId,
                    mUIViewModel.currentItem().isAnswered,
                    mUIViewModel.currentItem().selectedOption,
                    this@PracticeQuestionFragment
            )
    }

    override fun onClickRvOption(
            option: String,
            selectedOpt: Int?
    ) {
        if (!mUIViewModel.currentItem().isAnswered) {

            if (!mUIViewModel.hasPreviousSessionValue()) {
                mUIViewModel.setPreviousSession(true)
            }

            val updated = PracticeQuestionUI(
                    mUIViewModel.currentItem().questionNo,
                    true,
                    selectedOpt
            )
            mUIViewModel.update(updated)

            selectedOpt?.let {
                if (selectedOpt + 1 ==
                        mUIViewModel
                                .listPracticeQA[mUIViewModel.indexValue()]
                                .ansId
                ) {
                    mUIViewModel.increaseCorrect()
                } else {
                    mUIViewModel.increaseIncorrect()
                }
            }

            binding.rvOptions.adapter = PracticeOptionsAdapter(
                    mUIViewModel
                            .listPracticeQA[mUIViewModel.indexValue()]
                            .options,
                    mUIViewModel
                            .listPracticeQA[mUIViewModel.indexValue()]
                            .ansId,
                    mUIViewModel.currentItem().isAnswered,
                    selectedOpt,
                    this@PracticeQuestionFragment
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mUIViewModel.indexValue().let { index ->
            outState.putInt("index", index)
        }
        mUIViewModel.answeredCorrectValue().let { ca ->
            outState.putInt("ca", ca)
        }
        mUIViewModel.answeredIncorrectValue().let { ia ->
            outState.putInt("ia", ia)
        }
    }


    @SuppressLint("CommitPrefEdits")
    private fun saveData() {
        val sharedPreferences = this.activity?.getSharedPreferences("DATA", 0)
        val editor = sharedPreferences?.edit()
        editor?.apply {
            putInt("index", mUIViewModel.indexValue())
            putInt("ca", mUIViewModel.answeredCorrectValue())
            putInt("ia", mUIViewModel.answeredIncorrectValue())
            putBoolean("hasPreviousSession", mUIViewModel.hasPreviousSessionValue())
            putBoolean("isPQTableInitialised", mUIViewModel.isPQTableInitialized.value == true)
            apply()
        }
    }

    override fun onDestroy() {
        saveData()
        mUIViewModel.setIndex(0)
        super.onDestroy()
    }
}