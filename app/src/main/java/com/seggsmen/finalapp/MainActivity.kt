package com.seggsmen.finalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.seggsmen.finalapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val animationDuration = (200).toLong()

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
        binding.trackMealOverlayLayer.alpha = 0f
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
        binding.trackMealUnderlay.animate().alpha(0.5f).duration = animationDuration
        binding.trackMealOverlayLayer.visibility = View.VISIBLE
        binding.trackMealOverlayLayer.animate().alpha(1f).duration = animationDuration
        binding.pastPetsButton.isClickable = false
        binding.pastMealsButton.isClickable = false
        binding.trackMealButton.isClickable = false
    }

    private fun navigateToNewMeal() {
        val intent: Intent = Intent(this, NewMealAddMealActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSavedMeals() {
        Toast.makeText(this, "going to saved meals", Toast.LENGTH_SHORT).show()
    }

    private fun cancelTrackMealOverlay() {
        binding.trackMealUnderlay.animate().alpha(1f).duration = animationDuration
        binding.trackMealOverlayLayer.animate().alpha(0f).withEndAction{
            binding.trackMealOverlayLayer.visibility = View.GONE
        }.duration = animationDuration

        binding.pastPetsButton.isClickable = true
        binding.pastMealsButton.isClickable = true
        binding.trackMealButton.isClickable = true
    }
}