package com.example.rtotest.adapter


import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rtotest.R
import com.example.rtotest.model.PracticeQuestion

class ScorecardAdapter(
    private val list: List<PracticeQuestion>,
    private val listOfIsCorrect: List<Boolean?>,
    private val listOfSelectedOptions: List<Int?>
) : RecyclerView.Adapter<ScorecardAdapter.VerticalViewHolder>() {

    inner class VerticalViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {
        private val question = view.findViewById<TextView>(R.id.scorecard_question)
        private val yourAnswer = view.findViewById<TextView>(R.id.scorecard_your_answer)
        private val correctAnswer = view.findViewById<TextView>(R.id.scorecard_correct_answer)
        private val statusImg = view.findViewById<ImageView>(R.id.scorecard_question_status_image)
        private val statusText = view.findViewById<TextView>(R.id.scorecard_question_status_text)

        @SuppressLint("SetTextI18n")
        fun bindData(data: PracticeQuestion, isCorrect: Boolean?, selectedOption: Int?) {

            question.text = "${data.id}. ${data.que}"
            when (isCorrect) {
                true -> {
                    setStatus(QuestionStatus.CORRECT)
                    yourAnswer.text = "Your Answer : ${data.options[data.ansId - 1]}"
                    yourAnswer.visibility = View.VISIBLE
                    correctAnswer.visibility = View.GONE
                }
                false -> {
                    setStatus(QuestionStatus.INCORRECT)
                    yourAnswer.text = "Your Answer : ${data.options[selectedOption!!]}"
                    correctAnswer.text = "Correct Answer : ${data.options[data.ansId - 1]}"
                    yourAnswer.visibility = View.VISIBLE
                    correctAnswer.visibility = View.VISIBLE
                }
                else -> {
                    setStatus(QuestionStatus.UNANSWERED)
                    correctAnswer.text = "Correct Answer : ${data.options[data.ansId - 1]}"
                    yourAnswer.visibility = View.GONE
                    correctAnswer.visibility = View.VISIBLE
                }
            }
        }

        private fun setStatus(questionStatus: QuestionStatus){
            statusImg.setImageResource(questionStatus.iconId)
            statusText.text = questionStatus.toString()
            statusText.setTextColor(questionStatus.color)
            yourAnswer.setTextColor(questionStatus.color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_scorecard, parent, false)

        return VerticalViewHolder(v)
    }

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        holder.bindData(list[position], listOfIsCorrect[position], listOfSelectedOptions[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

enum class QuestionStatus(val iconId: Int,val color: Int){
    CORRECT(
        R.drawable.ic_outline_check_circle_outline_24, Color.parseColor("#008000")),
    INCORRECT(
        R.drawable.ic_outline_cancel_24, Color.RED),
    UNANSWERED(
        R.drawable.ic_round_error_outline_24, Color.GRAY)
}