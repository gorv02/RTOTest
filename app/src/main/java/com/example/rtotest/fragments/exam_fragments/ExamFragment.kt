package com.example.rtotest.fragments.exam_fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rtotest.R
import com.example.rtotest.adapter.ExamOptionsAdapter
import com.example.rtotest.viewmodels.ExamViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_exam.*
import kotlin.properties.Delegates


class ExamFragment : Fragment(), ExamOptionsAdapter.ClickListener {

    private val mExamViewModel by lazy {
        activity?.let {
            ViewModelProvider(it).get(ExamViewModel::class.java)
        } ?: ViewModelProvider(this).get(ExamViewModel::class.java)
    }

    private lateinit var countDownTimer: CountDownTimer
    private var endTime by Delegates.notNull<Long>()
    private var onSameQuestion = false


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_exam, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        askPermission()
                    }
                }
        )

        savedInstanceState?.let { inState ->
            onSameQuestion = inState.getBoolean("onSameQuestion")
            endTime = inState.getLong("endTime")
            mExamViewModel.setTimeLeftInMillis(endTime - System.currentTimeMillis())
        }
    }

    private fun askPermission() {
        MaterialAlertDialogBuilder(requireContext())
                .setTitle(
                        "Stop Exam"
                )
                .setMessage(
                        "Are you sure you want to stop this Exam?"
                )
                .setPositiveButton("Yes") { _, _ ->
                    findNavController()
                            .navigate(R.id.action_examFragment_to_practiceFragment)
                    countDownTimer.cancel()
                    mExamViewModel.setIndex(0)
                    mExamViewModel.resetListOfIsCorrect()
                    mExamViewModel.resetListOfSelectedOptions()
                }
                .setNegativeButton("No") { _, _ ->
                }
                .setCancelable(false)
                .show()
    }

    private fun startCountDown() {
        endTime = System.currentTimeMillis() + mExamViewModel.getTimeLeftInMillis()

        countDownTimer = object : CountDownTimer(
                mExamViewModel.getTimeLeftInMillis(),
                1000) {
            override fun onTick(millisUntilFinished: Long) {
                mExamViewModel.setTimeLeftInMillis(millisUntilFinished)
            }

            override fun onFinish() {
                if (mExamViewModel.indexValue() < mExamViewModel.listExamQA.size - 1)
                    mExamViewModel.increaseIndex()
                else {
                    cancel()
                    findNavController().navigate(R.id.action_examFragment_to_examStatusFragment)
                }
            }
        }.start()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_exam_and_practice, menu)

        val queNoMenuItem = menu.findItem(R.id.menu_item_question_no)
        val timerMenuItem = menu.findItem(R.id.menu_item_timer)

        val timerMenuItemTextView = timerMenuItem.actionView
                .findViewById<TextView>(R.id.menu_item_timer_text)

        mExamViewModel.index.observe(viewLifecycleOwner) {
            queNoMenuItem.title = "${it + 1}/${mExamViewModel.listExamQA.size}"
        }

        val timerTextColorStateList = timerMenuItemTextView.currentTextColor

        mExamViewModel.timeLeftInMillis.observe(viewLifecycleOwner) {
            timerMenuItemTextView.text = "${it / 1000} s"
            if (it / 1000 < 10) {
                timerMenuItemTextView.setTextColor(Color.RED)
            } else {
                timerMenuItemTextView.setTextColor(timerTextColorStateList)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mExamViewModel.index.observe(viewLifecycleOwner) { index ->
            showQuestion(index)
            showOptions(index)

            if (onSameQuestion) {
                startCountDown()
                onSameQuestion = false
            } else {
                mExamViewModel.setTimeLeftInMillis(30_000)
                startCountDown()
            }

            when (index) {
                mExamViewModel.listExamQA.size - 1 -> {
                    exam_nextBtn.visibility = View.GONE
                    exam_finishBtn.visibility = View.VISIBLE
                }
            }
        }

        exam_nextBtn.setOnClickListener {
            countDownTimer.cancel()
            mExamViewModel.increaseIndex()
        }

        exam_finishBtn.setOnClickListener {
            countDownTimer.cancel()
            findNavController().navigate(R.id.action_examFragment_to_examStatusFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showQuestion(index: Int) {
        mExamViewModel.apply {
            exam_question_text.text = "${index + 1}. ${listExamQA[index].que}"
        }
    }

    private fun showOptions(index: Int) {
        exam_options_rv.adapter = ExamOptionsAdapter(
                mExamViewModel
                        .listExamQA[index]
                        .options,
                mExamViewModel.getSelectedOption(index),
                this@ExamFragment
        )
    }

    override fun onClickRvOption(option: String, selectedOpt: Int?) {
        mExamViewModel.addSelectedOption(
                mExamViewModel.indexValue(), selectedOpt
        )

        mExamViewModel.apply {
            if (option ==
                    listExamQA[indexValue()].options[listExamQA[indexValue()].ansId - 1]) {
                addIsCorrect(indexValue(), true)
            } else {
                addIsCorrect(indexValue(), false)
            }
        }

        exam_options_rv.adapter = ExamOptionsAdapter(
                mExamViewModel
                        .listExamQA[mExamViewModel.indexValue()]
                        .options,
                mExamViewModel.getSelectedOption(mExamViewModel.indexValue()),
                this@ExamFragment
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        onSameQuestion = true
        outState.putBoolean("onSameQuestion", onSameQuestion)
        outState.putLong("endTime", endTime)
    }

    override fun onDestroy() {
        countDownTimer.cancel()
        super.onDestroy()
    }
}