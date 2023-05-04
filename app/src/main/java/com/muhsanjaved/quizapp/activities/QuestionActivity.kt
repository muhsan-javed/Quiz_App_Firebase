package com.muhsanjaved.quizapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.muhsanjaved.quizapp.adapters.OptionAdapter
import com.muhsanjaved.quizapp.databinding.ActivityQuestionBinding
import com.muhsanjaved.quizapp.models.Question
import com.muhsanjaved.quizapp.models.Quiz

class QuestionActivity : AppCompatActivity() {

    lateinit var binding:ActivityQuestionBinding
    var quizzes : MutableList<Quiz>? = null
    var questions : MutableMap<String, Question>? = null
    var index = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // buildView()
        setUpFireStore()
        setUpEventListener()
    }

    private fun setUpEventListener() {
        binding.btnPrevious.setOnClickListener {
            index--
            buildView()
        }
        binding.btnNext.setOnClickListener {
            index++
            buildView()
        }
        binding.btnSubmit.setOnClickListener {
           Log.d("FINALQUIZ",questions.toString())

            val intent = Intent(this, ResultActivity::class.java)
            val json :String = Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ", json)
            startActivity(intent)

        }
    }

    private fun setUpFireStore() {
        val firestore:FirebaseFirestore = FirebaseFirestore.getInstance()
        var date :String? = intent.getStringExtra("DATE")
        if (date != null){
            firestore.collection("quizzes").whereEqualTo("title","05-03-2023")
                .get()
                .addOnSuccessListener {
                    //it.toObjects(Quiz::class.java)
                    //Log.d("DATA",  it.toObjects(Quiz::class.java).toString())
                    if (it != null && !it.isEmpty){
                        //Log.d("DATA",  it.toObjects(Quiz::class.java).toString())
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].questions
                        buildView()
                    }
                }

        }


    }

    private fun buildView() {

        binding.btnPrevious.visibility = View.GONE
        binding.btnNext.visibility = View.GONE
        binding.btnSubmit.visibility = View.GONE

        if (index == 1) {// first question
            binding.btnNext.visibility = View.VISIBLE
        }
        else if (index == questions!!.size) { // last question
            binding.btnSubmit.visibility = View.VISIBLE
            binding.btnPrevious.visibility = View.VISIBLE
        }
        else{ // Middle
            binding.btnPrevious.visibility = View.VISIBLE
            binding.btnNext.visibility = View.VISIBLE
        }

        val  question : Question? = questions!!["question$index"]
        question?.let {
            binding.descriptionShow.text = it.description
            val optionAdapter  = OptionAdapter(this,it)
            binding.optionList.layoutManager = LinearLayoutManager(this)
            binding.optionList.adapter = optionAdapter
            binding.optionList.setHasFixedSize(true)
        }


        /*val question = Question(
            "Which is the only bird that can fly backwards",
            "Sunbird",
            "KingFisher",
            "Honey eater",
            "Hummingbird",
            "Hummingbird"
        )*/

       /* binding.descriptionShow.text = question.description
        val optionAdapter  = OptionAdapter(this,question)
        binding.optionList.layoutManager = LinearLayoutManager(this)
        binding.optionList.adapter = optionAdapter
        binding.optionList.setHasFixedSize(true)*/
    }
}