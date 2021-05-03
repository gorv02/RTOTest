package com.example.rtotest.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rtotest.R
import com.example.rtotest.adapter.OptionsAdapter
import com.example.rtotest.model.PracticeQuestionUI
import com.example.rtotest.viewmodels.PracticeQuestionUIViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class PracticeQuestionFragment : Fragment(), OptionsAdapter.ClickListener {

    private lateinit var mUIViewModel: PracticeQuestionUIViewModel

    private lateinit var question: TextView
    private lateinit var rvOptions: RecyclerView
    private lateinit var nextBtn: Button
    private lateinit var prevBtn: Button
    private lateinit var correct: TextView
    private lateinit var incorrect: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mUIViewModel =
                ViewModelProvider(this).get(PracticeQuestionUIViewModel::class.java)

        restoreData()

        savedInstanceState?.let { inState ->
            (inState.getInt("index") as Int?)?.let { index ->
                mUIViewModel.setIndex(index)
            }
            (inState.getInt("ca") as Int?)?.let { ca ->
                mUIViewModel.setAnsweredCorrect(ca)
            }
            (inState.getInt("ia") as Int?)?.let { ia ->
                mUIViewModel.setAnsweredIncorrect(ia)
            }
        }
    }

    private fun restoreData() {
        val sharedPreferences = this.activity?.getSharedPreferences("DATA", 0)
        sharedPreferences?.apply {
            mUIViewModel.setIndex(getInt("index", 0))
            mUIViewModel.setAnsweredCorrect(getInt("ca", 0))
            mUIViewModel.setAnsweredIncorrect(getInt("ia", 0))
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_practice_question,
                container,
                false)

        setHasOptionsMenu(true)

        view.apply {
            question = findViewById(R.id.practice_question)
            nextBtn = findViewById(R.id.nextBtn)
            prevBtn = findViewById(R.id.prevBtn)
            correct = findViewById(R.id.correct)
            incorrect = findViewById(R.id.incorrect)
        }

        mUIViewModel.addList()

        mUIViewModel.questionsListUI.observe(viewLifecycleOwner) {

            mUIViewModel.index().observe(viewLifecycleOwner) { index ->
                mUIViewModel.updateCurrentItem(mUIViewModel.indexValue())
                setQuestion(index)
                showRvOptions(view, index)

                when (index) {
                    0 -> prevBtn.visibility = View.GONE
                    mUIViewModel.listPracticeQA.size - 1 -> nextBtn.visibility = View.GONE
                    else -> {
                        prevBtn.visibility = View.VISIBLE
                        nextBtn.visibility = View.VISIBLE
                    }
                }
            }

            mUIViewModel.answeredCorrect().observe(viewLifecycleOwner) { value ->
                correct.text = value.toString()
            }

            mUIViewModel.answeredIncorrect().observe(viewLifecycleOwner) { value ->
                incorrect.text = value.toString()
            }
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.practice_question_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.restart) {
            askToRestart()
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun askToRestart() {
        MaterialAlertDialogBuilder(requireContext())
                .setTitle(
                        "Restart Session"
                )
                .setMessage(
                        "Do you want to restart this session?"
                )
                .setPositiveButton("Yes") { _, _ ->

                    mUIViewModel.deleteData()
                    val list = List(10) {
                        PracticeQuestionUI(0, false, null)
                    }
                    mUIViewModel.addDefaultList(list)


                    mUIViewModel.setIndex(0)
                    mUIViewModel.setAnsweredCorrect(0)
                    mUIViewModel.setAnsweredIncorrect(0)
                }
                .setNegativeButton("No") { _, _ -> }
                .setCancelable(false)
                .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        nextBtn.setOnClickListener { _ ->
            mUIViewModel.increaseIndex()
        }

        prevBtn.setOnClickListener {
            mUIViewModel.decreaseIndex()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion(index: Int) {
        mUIViewModel.apply {
            question.text = "${index + 1}. ${listPracticeQA[index].que}"
        }
    }

    private fun showRvOptions(view: View, index: Int) {

        rvOptions = view.findViewById(R.id.rv_options)

        val linearLM = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL,
                false)

        rvOptions.let { rv ->
            rv.layoutManager = linearLM
            rv.adapter = OptionsAdapter(
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

    }

    override fun onClickRvOption(
            option: String,
            selectedOpt: Int?
    ) {
        if (!mUIViewModel.currentItem().isAnswered) {

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

            rvOptions.adapter = OptionsAdapter(
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
        super.onSaveInstanceState(outState)

        mUIViewModel.indexValue().let { index ->
            outState.putInt("index", index)
        }
        mUIViewModel.answeredCorrect().value?.let { ca ->
            outState.putInt("ca", ca)
        }
        mUIViewModel.answeredIncorrect().value?.let { ia ->
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
            apply()
        }
    }

    override fun onDestroy() {
        saveData()
        super.onDestroy()
    }
}