package com.example.rtotest.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rtotest.R
import com.example.rtotest.databinding.FragmentQuestionBinding
import com.example.rtotest.model.Question

class QuestionFragment : Fragment() {

    private val binding by lazy { FragmentQuestionBinding.inflate(layoutInflater) }
    private lateinit var currentQuestion: Question

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Question>("currentQuestion")?.let {
            currentQuestion = it
        }

        binding.apply {
            queNoQuestionFragment.text = "Question ${currentQuestion.questionNo}"
            questionQuestionFragment.text = currentQuestion.que
            answerQuestionFragment.text = currentQuestion.ans
        }
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