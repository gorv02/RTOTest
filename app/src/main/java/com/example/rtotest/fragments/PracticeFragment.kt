package com.example.rtotest.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rtotest.R
import com.example.rtotest.model.PracticeQuestionUI
import com.example.rtotest.viewmodels.PracticeQuestionUIViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_practice.*

class PracticeFragment : Fragment() {

    private val mUIViewModel by lazy {
        ViewModelProvider(requireActivity()).get(PracticeQuestionUIViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_practice, container, false)
    }

    override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?
    ) {

        practice_question_card.setOnClickListener {
            val sharedPreferences = this.activity?.getSharedPreferences("DATA", 0)

            mUIViewModel.isPQTableInitialized.value =
                    sharedPreferences?.getBoolean("isPQTableInitialised", false)

            sharedPreferences?.getBoolean("hasPreviousSession", false)?.let {
                if (it) {
                    askToResume(sharedPreferences)
                } else {
                    findNavController()
                            .navigate(R.id.action_practiceFragment_to_practiceQuestionFragment)
                }
            }
        }
        exam_card.setOnClickListener {
            findNavController()
                    .navigate(R.id.action_practiceFragment_to_examIntroFragment)
        }

    }

    private fun askToResume(sharedPreferences: SharedPreferences?) {
        MaterialAlertDialogBuilder(requireContext())
                .setTitle(
                        "Resume Previous Session"
                )
                .setMessage(
                        "You have a previous session.\n" +
                                "Do you want to resume that session?"
                )
                .setPositiveButton("Yes") { _, _ ->
                    mUIViewModel.setPreviousSession(true)
                    restoreData(sharedPreferences)

                    findNavController()
                            .navigate(R.id.action_practiceFragment_to_practiceQuestionFragment)
                }
                .setNegativeButton("No") { _, _ ->

                    mUIViewModel.setIndex(0)
                    mUIViewModel.setAnsweredCorrect(0)
                    mUIViewModel.setAnsweredIncorrect(0)
                    mUIViewModel.setPreviousSession(false)

                    mUIViewModel.deleteData()

                    val list = List(mUIViewModel.listPracticeQA.size) {

                        PracticeQuestionUI(0, false, null)
                    }
                    mUIViewModel.addDefaultList(list)

                    findNavController()
                            .navigate(R.id.action_practiceFragment_to_practiceQuestionFragment)
                }
                .setCancelable(false)
                .show()
    }

    private fun restoreData(sharedPreferences: SharedPreferences?) {
        sharedPreferences?.apply {
            mUIViewModel.setIndex(getInt("index", 0))
            mUIViewModel.setAnsweredCorrect(getInt("ca", 0))
            mUIViewModel.setAnsweredIncorrect(getInt("ia", 0))
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_primary, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.settings_item) {
            findNavController().navigate(R.id.action_global_settingsFragment)
            true
        } else super.onOptionsItemSelected(item)
    }
}