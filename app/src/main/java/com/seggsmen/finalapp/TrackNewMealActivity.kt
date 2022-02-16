package com.seggsmen.finalapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.seggsmen.finalapp.databinding.ActivityNewMealBinding

class TrackNewMealActivity  : AppCompatActivity() {
    lateinit var binding: ActivityNewMealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var actionBar : ActionBar? = this.supportActionBar

        actionBar?.setTitle("Meal Info")
        actionBar?.setSubtitle("Track Meal")
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.nextButton.setOnClickListener {navigateToNextScreen()}
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun navigateToNextScreen() {
        Toast.makeText(this, "wahoo", Toast.LENGTH_SHORT).show()
//        val intent: Intent = Intent(this, ::class.java)
//        startActivity(intent)
    }
}