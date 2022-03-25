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
import com.seggsmen.finalapp.logic.PetStats

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var petStats = PetStats()
    val animationDuration = (200).toLong()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadPetStats()
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

    private fun loadPetStats() {
        val sharedPrefs = this.getSharedPreferences(Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        val userKey = sharedPrefs.getString(Const.USER_KEY, Const.STRING_NO_VALUE)!!

        val petStatsRef = Firebase.database.getReference(Const.DB_USERS).child(userKey).child(Const.DB_PET_STATS)
        petStatsRef.addListenerForSingleValueEvent( object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dbPetStats = snapshot.value as HashMap<*, *>
                binding.feelingStatusText.text = dbPetStats [Const.DB_FEELING] as String
                petStats.timeLastEaten = dbPetStats [Const.DB_LAST_EATEN] as Long
                petStats.timeLastVisited = dbPetStats [Const.DB_LAST_VISITED] as Long
                binding.vegetableServingText.text = "${(dbPetStats [Const.DB_VEGETABLE] as Long)} / 3"
                binding.fruitServingText.text = "${(dbPetStats [Const.DB_FRUIT] as Long)} / 3"
                binding.grainServingText.text = "${(dbPetStats [Const.DB_GRAIN] as Long)} / 3"
                binding.fishServingText.text = "${(dbPetStats [Const.DB_FISH] as Long)} / 3"
                binding.poultryServingText.text = "${(dbPetStats [Const.DB_POULTRY] as Long)} / 3"
                binding.redMeatServingText.text = "${(dbPetStats [Const.DB_REDMEAT] as Long)} / 3"
                binding.oilServingText.text = "${(dbPetStats [Const.DB_OIL] as Long)} / 3"
                binding.dairyServingText.text = "${(dbPetStats [Const.DB_DAIRY] as Long)} / 3"

//                petStats.feeling = dbPetStats [Const.DB_FEELING] as String
//                petStats.timeLastEaten = dbPetStats [Const.DB_LAST_EATEN] as Long
//                petStats.timeLastVisited = dbPetStats [Const.DB_LAST_VISITED] as Long
//                petStats.vegetableServings = dbPetStats [Const.DB_VEGETABLE] as Int
//                petStats.fruitServings = dbPetStats [Const.DB_FRUIT] as Int
//                petStats.grainServings = dbPetStats [Const.DB_GRAIN] as Int
//                petStats.fishServings = dbPetStats [Const.DB_FISH] as Int
//                petStats.poultryServings = dbPetStats [Const.DB_POULTRY] as Int
//                petStats.redMeatServings = dbPetStats [Const.DB_REDMEAT] as Int
//                petStats.oilServings = dbPetStats [Const.DB_OIL] as Int
//                petStats.dairyServings = dbPetStats [Const.DB_DAIRY] as Int
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun enablePetting() {
        // TODO: Bjingus petting tingz
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