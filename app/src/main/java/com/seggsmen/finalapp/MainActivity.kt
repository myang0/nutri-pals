package com.seggsmen.finalapp

import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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
        binding.trackMealButton.setOnClickListener { showBlurAndTrackButton() }
        binding.trackMealOverlayLayer.setOnClickListener { cancelTrackMealOverlay() }
        binding.cancelTrackMealButton.setOnClickListener { cancelTrackMealOverlay() }
        binding.newMealButton.setOnClickListener { navigateToNewMeal() }
        binding.savedMealsButton.setOnClickListener { navigateToSavedMeals() }
    }

    private fun navigateToPastPets() {
        val intent: Intent = Intent(this, ListPastPetsActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToPastMeals() {
        val intent: Intent = Intent(this, ListPastMealsActivity::class.java)
        startActivity(intent)
    }

    private fun showBlurAndTrackButton() {
        binding.trackMealUnderlay.alpha = 0.5f
        binding.trackMealOverlayLayer.visibility = View.VISIBLE
        binding.pastPetsButton.isClickable = false
        binding.pastMealsButton.isClickable = false
        binding.trackMealButton.isClickable = false
    }

    private fun navigateToNewMeal() {
        Toast.makeText(this, "going to new meals", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToSavedMeals() {
        Toast.makeText(this, "going to saved meals", Toast.LENGTH_SHORT).show()
    }

    private fun cancelTrackMealOverlay() {
        binding.trackMealUnderlay.alpha = 1f
        binding.trackMealOverlayLayer.visibility = View.GONE
        binding.pastPetsButton.isClickable = true
        binding.pastMealsButton.isClickable = true
        binding.trackMealButton.isClickable = true
    }
}