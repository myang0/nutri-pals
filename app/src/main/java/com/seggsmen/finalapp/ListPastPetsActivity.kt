package com.seggsmen.finalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.seggsmen.finalapp.databinding.ActivityListPastPetsBinding

class ListPastPetsActivity : AppCompatActivity() {
    lateinit var binding: ActivityListPastPetsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListPastPetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var actionBar : ActionBar? = this.supportActionBar

        actionBar?.setTitle("Pet History")
        actionBar?.setSubtitle("Pet Log")
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.pastPetListing.setOnClickListener { navigateToPastPetView() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun navigateToPastPetView() {
        val intent: Intent = Intent(this, ViewPastPetActivity::class.java)
        startActivity(intent)
    }
}