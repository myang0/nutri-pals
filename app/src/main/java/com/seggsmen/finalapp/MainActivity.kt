package com.seggsmen.finalapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.seggsmen.finalapp.databinding.ActivityMainBinding
import com.seggsmen.finalapp.logic.Const
import com.seggsmen.finalapp.logic.EvoStats
import com.seggsmen.finalapp.logic.PetStats
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var petStats = PetStats()
    var evoStats = EvoStats()
    val animationDuration = (200).toLong()
    lateinit var userKey: String

    lateinit var sharedPrefs: SharedPreferences
    lateinit var petStatsRef: DatabaseReference
    lateinit var evoStatsRef: DatabaseReference

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
        sharedPrefs = this.getSharedPreferences(Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        userKey = sharedPrefs.getString(Const.USER_KEY, Const.STRING_NO_VALUE)!!

        petStatsRef = Firebase.database.getReference(Const.DB_USERS).child(userKey).child(Const.DB_PET_STATS)

        petStatsRef.addListenerForSingleValueEvent( object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val dbPetStats = snapshot.value as HashMap<*, *>
                petStats.petName = dbPetStats [Const.DB_PETNAME] as String
                petStats.feeling = dbPetStats [Const.DB_FEELING] as String
                petStats.timeLastEaten = dbPetStats [Const.DB_LAST_EATEN] as String
                petStats.timeLastDecay = dbPetStats [Const.DB_LAST_DECAY] as String
                petStats.vegetableServings = dbPetStats [Const.DB_VEGETABLE] as Long
                petStats.fruitServings = dbPetStats [Const.DB_FRUIT] as Long
                petStats.grainServings = dbPetStats [Const.DB_GRAIN] as Long
                petStats.fishServings = dbPetStats [Const.DB_FISH] as Long
                petStats.poultryServings = dbPetStats [Const.DB_POULTRY] as Long
                petStats.redMeatServings = dbPetStats [Const.DB_REDMEAT] as Long
                petStats.oilServings = dbPetStats [Const.DB_OIL] as Long
                petStats.dairyServings = dbPetStats [Const.DB_DAIRY] as Long

                binding.petName.text = petStats.petName
                loadEvoData()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun loadEvoData() {
        userKey = sharedPrefs.getString(Const.USER_KEY, Const.STRING_NO_VALUE)!!

        evoStatsRef = Firebase.database.getReference(Const.DB_USERS).child(userKey).child(Const.DB_EVO_STATS)
        evoStatsRef.addListenerForSingleValueEvent( object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val dbEvoStats = snapshot.value as HashMap<*, *>
                evoStats.evoType = dbEvoStats [Const.DB_EVO_TYPE] as String
                evoStats.timeLastEvo = dbEvoStats [Const.DB_LAST_EVO] as String
                evoStats.totalServings = dbEvoStats [Const.DB_TOTAL_SERVINGS] as Long
                evoStats.starvedServings = dbEvoStats [Const.DB_STARVED_SERVINGS] as Long
                evoStats.vegetableServings = dbEvoStats [Const.DB_VEGETABLE] as Long
                evoStats.fruitServings = dbEvoStats [Const.DB_FRUIT] as Long
                evoStats.grainServings = dbEvoStats [Const.DB_GRAIN] as Long
                evoStats.fishServings = dbEvoStats [Const.DB_FISH] as Long
                evoStats.poultryServings = dbEvoStats [Const.DB_POULTRY] as Long
                evoStats.redMeatServings = dbEvoStats [Const.DB_REDMEAT] as Long
                evoStats.oilServings = dbEvoStats [Const.DB_OIL] as Long
                evoStats.dairyServings = dbEvoStats [Const.DB_DAIRY] as Long

                checkFoodServingDecay()
                checkEvolution()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun checkEvolution() {
        val lastEvoTime = LocalDateTime.parse(evoStats.timeLastEvo)

        val currentTime = Instant.ofEpochMilli(System.currentTimeMillis())
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        val timeElapsed: Long = ChronoUnit.MINUTES.between(lastEvoTime, currentTime)
        Log.d(Const.LOG, "Last Evo Time: ${lastEvoTime}")
        Log.d(Const.LOG, "Current Time: ${currentTime}")
        Log.d(Const.LOG, "Minutes Elapsed: ${timeElapsed}")

        if (timeElapsed > 60*24*7 && evoStats.evoType == Const.EVO_INITIAL) {
            Log.d(Const.LOG, "Starting Evolution")
//            val intent = Intent(this, ___:class.java)
//            startActivity(intent)
        }
    }

    private fun checkFoodServingDecay() {
        var lastDecayTime = LocalDateTime.parse(petStats.timeLastDecay)

        val currentTime = Instant.ofEpochMilli(System.currentTimeMillis())
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        val timeElapsed: Long = ChronoUnit.MINUTES.between(lastDecayTime, currentTime)
//        val timeElapsed: Long = ChronoUnit.SECONDS.between(lastDecayTime, currentTime)
        Log.d(Const.LOG, "Last Decay Time: ${lastDecayTime}")
        Log.d(Const.LOG, "Current Time: ${currentTime}")
        Log.d(Const.LOG, "Minutes Elapsed: ${timeElapsed}")
//        Log.d(Const.LOG, "Seconds: ${timeElapsed}")

        val instances = timeElapsed/(60*3) // Every 3 hours -> 8 servings per day
//        val instances = timeElapsed/30 // Half minutes
        Log.d(Const.LOG, "Instances: ${instances}")

        if (instances > 0) {
            lastDecayTime = lastDecayTime.plusHours(instances*12)
//            lastDecayTime = lastDecayTime.plusSeconds(instances*30)
            petStats.timeLastDecay = lastDecayTime.toString()
        }
        for (i in 1..instances) {
            val totalServings = petStats.vegetableServings + petStats.fruitServings +
                    petStats.grainServings + petStats.fishServings + petStats.poultryServings +
                    petStats.redMeatServings + petStats.oilServings + petStats.dairyServings
            Log.d(Const.LOG, "Total Servings: ${totalServings}")
            if (totalServings > 0) {
                foodServingDecay()
            } else {
                Log.d(Const.LOG, "NO SERVINGS, STARVING!")
                evoStats.starvedServings++
                evoStatsRef.setValue(evoStats)
            }
        }

        binding.vegetableServingText.text = "${petStats.vegetableServings} / 3"
        binding.fruitServingText.text = "${petStats.fruitServings} / 3"
        binding.grainServingText.text = "${petStats.grainServings} / 3"
        binding.fishServingText.text = "${petStats.fishServings} / 3"
        binding.poultryServingText.text = "${petStats.poultryServings} / 3"
        binding.redMeatServingText.text = "${petStats.redMeatServings} / 3"
        binding.oilServingText.text = "${petStats.oilServings} / 3"
        binding.dairyServingText.text = "${petStats.dairyServings} / 3"
        Log.d(Const.LOG, "New Decay: ${lastDecayTime}}")
        petStatsRef.setValue(petStats)
    }

    private fun foodServingDecay() {
        val randomServing = listOf(Const.DB_VEGETABLE, Const.DB_FRUIT, Const.DB_GRAIN, Const.DB_FISH,
            Const.DB_POULTRY, Const.DB_REDMEAT, Const.DB_OIL, Const.DB_DAIRY).random()
        when (randomServing) {
            Const.DB_VEGETABLE -> {
                if (petStats.vegetableServings > 0) {
                    petStats.vegetableServings--
                    Log.d(Const.LOG, "VEGGIES--")
                } else {
                    foodServingDecay()
                    Log.d(Const.LOG, "VEGGIES REROLLED")
                }
            }
            Const.DB_FRUIT -> {
                if (petStats.fruitServings > 0) {
                    petStats.fruitServings--
                    Log.d(Const.LOG, "FRUITS--")
                } else {
                    foodServingDecay()
                    Log.d(Const.LOG, "FRUITS REROLLED")
                }
            }
            Const.DB_GRAIN -> {
                if (petStats.grainServings > 0) {
                    petStats.grainServings--
                    Log.d(Const.LOG, "GRAINS--")
                } else {
                    foodServingDecay()
                    Log.d(Const.LOG, "GRAINS REROLLED")
                }
            }
            Const.DB_FISH -> {
                if (petStats.fishServings > 0) {
                    petStats.fishServings--
                    Log.d(Const.LOG, "FISH--")
                } else {
                    foodServingDecay()
                    Log.d(Const.LOG, "FISH REROLLED")
                }
            }
            Const.DB_POULTRY -> {
                if (petStats.poultryServings > 0) {
                    petStats.poultryServings--
                    Log.d(Const.LOG, "POULTRY--")
                } else {
                    foodServingDecay()
                    Log.d(Const.LOG, "POULTRY REROLLED")
                }
            }
            Const.DB_REDMEAT -> {
                if (petStats.redMeatServings > 0) {
                    petStats.redMeatServings--
                    Log.d(Const.LOG, "RED MEAT--")
                } else {
                    foodServingDecay()
                    Log.d(Const.LOG, "RED MEAT REROLLED")
                }
            }
            Const.DB_OIL -> {
                if (petStats.oilServings > 0) {
                    petStats.oilServings--
                    Log.d(Const.LOG, "OIL--")
                } else {
                    foodServingDecay()
                    Log.d(Const.LOG, "OIL REROLLED")
                }
            }
            Const.DB_DAIRY -> {
                if (petStats.dairyServings > 0) {
                    petStats.dairyServings--
                    Log.d(Const.LOG, "DAIRY--")
                } else {
                    foodServingDecay()
                    Log.d(Const.LOG, "DAIRY REROLLED")
                }
            }
        }
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