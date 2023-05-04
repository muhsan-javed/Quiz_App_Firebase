package com.muhsanjaved.quizapp.activities

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.muhsanjaved.quizapp.R
import com.muhsanjaved.quizapp.adapters.QuizAdapter
import com.muhsanjaved.quizapp.databinding.ActivityMainBinding
import com.muhsanjaved.quizapp.models.Quiz
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    lateinit var fireStore : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //populateDummyData()
        setUpFireStore()

        setSupportActionBar(binding.appBar)

        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.mainDrawer, R.string.app_name, R.string.app_name
        )
        actionBarDrawerToggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            binding.mainDrawer.closeDrawers()
            true
        }
        setUpRecyclerView()
        btnDatePicker()
    }

    private fun btnDatePicker() {
        binding.btnDatePicker.setOnClickListener {
            val datePicker:MaterialDatePicker<Long> = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATAPICKER", datePicker.headerText)

                val dateFormatter = SimpleDateFormat("dd-mm-yyyy")
                val date:String = dateFormatter.format(Date(it))

                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("DATE", date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATAPICKER", datePicker.headerText)
            }
            datePicker.addOnCancelListener {
                Log.d("DATAPICKER", "Date Picker Cancelled")
            }

        }
    }

    private fun setUpFireStore() {
        fireStore = FirebaseFirestore.getInstance()

        val collectionReference: CollectionReference = fireStore.collection("quizzes")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null){
                Toast.makeText(this,"Error fetching data",Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA", value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun populateDummyData() {
        quizList.add(Quiz("05-09-23","05-09-23"))
        quizList.add(Quiz("06-09-23","06-09-23"))
        quizList.add(Quiz("07-09-23","07-09-23"))
        quizList.add(Quiz("08-09-23","08-09-23"))
        quizList.add(Quiz("09-09-23","09-09-23"))
        quizList.add(Quiz("10-09-23","10-09-23"))
        quizList.add(Quiz("11-09-23","11-09-23"))
        quizList.add(Quiz("12-09-23","12-09-23"))
        quizList.add(Quiz("13-09-23","13-09-23"))
        quizList.add(Quiz("14-09-23","14-09-23"))
        quizList.add(Quiz("15-09-23","15-09-23"))
        quizList.add(Quiz("16-09-23","16-09-23"))
        quizList.add(Quiz("05-09-23","05-09-23"))
        quizList.add(Quiz("06-09-23","06-09-23"))
        quizList.add(Quiz("07-09-23","07-09-23"))
        quizList.add(Quiz("08-09-23","08-09-23"))
        quizList.add(Quiz("09-09-23","09-09-23"))
        quizList.add(Quiz("10-09-23","10-09-23"))
        quizList.add(Quiz("11-09-23","11-09-23"))
        quizList.add(Quiz("12-09-23","12-09-23"))
        quizList.add(Quiz("13-09-23","13-09-23"))
        quizList.add(Quiz("14-09-23","14-09-23"))
        quizList.add(Quiz("15-09-23","15-09-23"))
        quizList.add(Quiz("16-09-23","16-09-23"))
    }

    private fun setUpRecyclerView() {
        adapter  = QuizAdapter(this,quizList)
        binding.recyclerView.layoutManager = GridLayoutManager(this,2)
        binding.recyclerView.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}