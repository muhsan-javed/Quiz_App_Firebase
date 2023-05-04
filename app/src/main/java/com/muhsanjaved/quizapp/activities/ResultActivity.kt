package com.muhsanjaved.quizapp.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.google.gson.Gson
import com.muhsanjaved.quizapp.databinding.ActivityResultBinding
import com.muhsanjaved.quizapp.models.Question
import com.muhsanjaved.quizapp.models.Quiz

class ResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityResultBinding
    lateinit var quiz : Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
    }

    private fun setUpViews() {
        val quizData:String? = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson<Quiz>(quizData, Quiz::class.java)
        calculateScore()
        setAnswerView()
    }

    private fun calculateScore() {
        var score = 0
        for (entry : MutableMap.MutableEntry<String, Question> in quiz.questions.entries){
            val  question : Question = entry.value
            if (question.answer == question.userAnswer){
                score += 10
            }
        }
        binding.tvScore.text = "Your Score: $score"
    }

    private fun setAnswerView() {
        val builder  = StringBuilder("")
        for (entry : MutableMap.MutableEntry<String, Question> in quiz.questions.entries){
            val question :Question = entry.value
            builder.append("<font color'#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${question.answer}</font><br/><br/>")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            binding.txtAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT)
        }else{
            binding.txtAnswer.text = Html.fromHtml(builder.toString())
        }
    }
}