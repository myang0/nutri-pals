package com.seggsmen.finalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.seggsmen.finalapp.databinding.ObsoleteActivityListPastMealsBinding

class ObsoleteListPastMealsActivity : AppCompatActivity() {
    lateinit var binding: ObsoleteActivityListPastMealsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ObsoleteActivityListPastMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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