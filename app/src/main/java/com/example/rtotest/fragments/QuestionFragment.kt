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

    private lateinit var binding: FragmentQuestionBinding
    private lateinit var currentQuestion: Question

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentQuestionBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentQuestion = arguments?.getParcelable("currentQuestion")
            ?: Question(1, "", "")

        binding.apply {
            queNoQuestionFragment.text = "Question ${currentQuestion.questionNO}"
            questionQuestionFragment.text = currentQuestion.que
            answerQuestionFragment.text = currentQuestion.ans
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_primary, menu)
        menu.findItem(R.id.share_item).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.settings_item) {
            findNavController().navigate(R.id.action_global_settingsFragment)
            true
        } else super.onOptionsItemSelected(item)
    }
}