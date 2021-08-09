package com.example.rtotest.fragments.home_fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rtotest.R
import com.example.rtotest.model.Question
import kotlinx.android.synthetic.main.fragment_question.*

class QuestionFragment : Fragment() {

    private lateinit var currentQuestion: Question

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Question>("currentQuestion")?.let {
            currentQuestion = it
        }

        que_no_questionFragment.text = "Question ${currentQuestion.questionNo}"
        question_questionFragment.text = currentQuestion.que
        answer_questionFragment.text = currentQuestion.ans
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_primary, menu)
        menu.findItem(R.id.menu_primary_items).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.settings_item) {
            findNavController().navigate(R.id.action_global_settingsFragment)
            true
        } else super.onOptionsItemSelected(item)
    }
}