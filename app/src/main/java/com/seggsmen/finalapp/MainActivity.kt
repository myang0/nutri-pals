package com.seggsmen.finalapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.seggsmen.finalapp.databinding.ActivityMainBinding
import com.seggsmen.finalapp.logic.Const

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
        binding.pettingArea.setOnClickListener {bjingusFrown()}
        binding.pettingArea.post {enablePetting()}
        binding.trackMealOverlayLayer.alpha = 0f
    }

    private fun enablePetting() {
        // To-do: Bjingus petting tingz
    }

    private fun bjingusFrown() {
        binding.mainActivityBjingus.setImageResource(R.drawable.bjingus_upset_anim)
        binding.mainActivityBjingus.animate().alpha(1f).withEndAction {
            binding.mainActivityBjingus.setImageResource(R.drawable.bjingus_happy_anim)
        }.duration = 1000
    }

    private fun navigateToPastPets() {
        val intent: Intent = Intent(this, OnboardingActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToPastMeals() {
        val intent: Intent = Intent(this, MealLibraryActivity::class.java)
        startActivity(intent)
    }

    private fun showBlurAndTrackButton() {
        binding.trackMealUnderlay.animate().alpha(0.5f).duration = animationDuration
        binding.trackMealOverlayLayer.visibility = View.VISIBLE
        binding.trackMealOverlayLayer.animate().alpha(1f).duration = animationDuration
        enableUnderlayClickable(false)
    }

    private fun navigateToNewMeal() {
        val intent: Intent = Intent(this, NewMealAddMealActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSavedMeals() {
        val intent: Intent = Intent(this, SavedMealsActivity::class.java)
        startActivity(intent)
    }

    private fun cancelTrackMealOverlay() {
        binding.trackMealUnderlay.animate().alpha(1f).duration = animationDuration
        binding.trackMealOverlayLayer.animate().alpha(0f).withEndAction{
            binding.trackMealOverlayLayer.visibility = View.GONE
        }.duration = animationDuration

        enableUnderlayClickable(true)
    }

    private fun enableUnderlayClickable(boolean: Boolean) {
        binding.pastPetsButton.isClickable = boolean
        binding.pastMealsButton.isClickable = boolean
        binding.trackMealButton.isClickable = boolean
        binding.pettingArea.isClickable = boolean
    }
}