package com.example.rtotest.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rtotest.R
import com.example.rtotest.adapter.OptionsAdapter
import com.example.rtotest.viewmodels.PracticeViewModel
import com.google.android.material.snackbar.Snackbar


class PracticeFragment : Fragment(), OptionsAdapter.ClickListener {

    private lateinit var practiceViewModel: PracticeViewModel

    private lateinit var question: TextView
    private lateinit var rvOptions: RecyclerView
    private lateinit var nextBtn: Button
    private lateinit var prevBtn: Button
    private lateinit var layout: CoordinatorLayout
    private lateinit var correct: TextView
    private lateinit var incorrect: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_practice, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        practiceViewModel = ViewModelProviders.of(this)[PracticeViewModel::class.java]

        view.apply {
            question = findViewById(R.id.practice_question)
            nextBtn = findViewById(R.id.nextBtn)
            prevBtn = findViewById(R.id.prevBtn)
            layout = findViewById(R.id.layout)
            correct = findViewById(R.id.correct)
            incorrect = findViewById(R.id.incorrect)
        }
        practiceViewModel.apply {

            if (questionNo < listPracticeQA.size) {
                setQuestion()
                showRvOptions(view)
            }

            correct.text = answeredCorrect.toString()
            incorrect.text = answeredIncorrect.toString()

            nextBtn.setOnClickListener {
                if (questionNo < listPracticeQA.size - 1) {
                    questionNo++
                    setQuestion()
                    showRvOptions(view)
                } else {
                    Snackbar.make(layout, "End!", Snackbar.LENGTH_SHORT)
                        .setAction("Go to First Question") {
                            questionNo = 0
                            setQuestion()
                            showRvOptions(view)
                        }
                        .setActionTextColor(Color.CYAN)
                        .show()
                }
            }

            prevBtn.setOnClickListener {
                if (questionNo > 0) {
                    questionNo--
                    setQuestion()
                    showRvOptions(view)
                } else {
                    Snackbar.make(layout, "No Previous Question!", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        practiceViewModel.apply {

            question.text = "${listPracticeQA[questionNo].id}. " +
                    listPracticeQA[questionNo].que
        }
    }

    private fun showRvOptions(view: View) {

        rvOptions = view.findViewById(R.id.rv_options)

        val linearLM = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        practiceViewModel.apply {

            rvOptions.let {
                it.layoutManager = linearLM
                it.adapter = OptionsAdapter(
                    listPracticeQA[questionNo].options,
                    listPracticeQA[questionNo].ansId,
                    answeredList[questionNo],
                    listSelectedOpt[questionNo],
                    this@PracticeFragment
                )
            }
        }
    }

    override fun onClickRvOption(
        option: String,
        selectedOpt: Int?
    ) {

        practiceViewModel.apply {

            if (!answeredList[questionNo]) {
                answeredList[questionNo] = true

                listSelectedOpt[questionNo] = selectedOpt

                selectedOpt?.let {
                    if (selectedOpt + 1 ==
                            listPracticeQA[questionNo].ansId
                    ) {
                        answeredCorrect++
                        correct.text = answeredCorrect.toString()
                    } else {
                        answeredIncorrect++
                        incorrect.text = answeredIncorrect.toString()
                    }
                }

                rvOptions.adapter = OptionsAdapter(
                    listPracticeQA[questionNo].options,
                    listPracticeQA[questionNo].ansId,
                    answeredList[questionNo],
                    selectedOpt,
                    this@PracticeFragment
                )
            }
        }
    }

    private fun updateScore() {
        practiceViewModel.apply {

            correct.text = answeredCorrect.toString()
            incorrect.text = answeredIncorrect.toString()
        }
    }
}