package com.seggsmen.finalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.seggsmen.finalapp.databinding.ActivityListPastMealsBinding

class ListPastMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivityListPastMealsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListPastMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var actionBar : ActionBar? = this.supportActionBar

        actionBar?.setTitle("Meal Library")
        actionBar?.setSubtitle("Saved Meals")
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.pastMealListing.setOnClickListener { navigateToPastMealView() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun navigateToPastMealView() {
        val intent: Intent = Intent(this, ViewPastMealActivity::class.java)
        startActivity(intent)
    }
}