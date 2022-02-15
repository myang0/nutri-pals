package com.seggsmen.finalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.seggsmen.finalapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar?.hide()

        binding.pastPetsButton.setOnClickListener { navigateToPastPets() }
        binding.pastMealsButton.setOnClickListener { navigateToPastMeals() }
    }

    private fun navigateToPastPets() {
        val intent: Intent = Intent(this, ListPastPetsActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToPastMeals() {
        Toast.makeText(this, "going to past meals", Toast.LENGTH_SHORT).show()
    }
}