package com.seggsmen.finalapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.seggsmen.finalapp.databinding.ActivitySavedMealsBinding
import com.seggsmen.finalapp.logic.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.HashMap

class SavedMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySavedMealsBinding
    lateinit var list: RecyclerView

    private lateinit var mealIdList: ArrayList<String>
    private lateinit var selectedMeal: NewMeal

    private lateinit var dbRef: FirebaseDatabase
    private lateinit var userDataRef: DatabaseReference

    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var userKey: String

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivitySavedMealsBinding.inflate (layoutInflater);
        setContentView(binding.root)

        setSupportActionBar(binding.savedMealsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.savedMealsToolbar.setNavigationOnClickListener {onBackPressed()}

        fetchMeals()
    }

    private fun fetchMeals() {
        dbRef = Firebase.database
        userDataRef = dbRef.getReference(Const.DB_USERS)

        sharedPrefs = this.getSharedPreferences(Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        userKey = sharedPrefs.getString(Const.USER_KEY, Const.STRING_NO_VALUE)!!

        userDataRef
            .child(userKey!!)
            .child(Const.DB_PAST_MEALS)
            .addListenerForSingleValueEvent(
                object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var savedMeals: ArrayList<NewMeal> = arrayListOf()
                        if (snapshot.value != null) {
                            val mealsFromFirebase = snapshot.value as Map<String, Map<String, Any>>

                            mealIdList = arrayListOf()

                            for ((mealId, firebaseMeal) in mealsFromFirebase) {
                                val isSaved = firebaseMeal["saved"] as Boolean
                                if (isSaved) {
                                    mealIdList.add(mealId)

                                    var meal = NewMeal(
                                        firebaseMeal[Const.DB_NAME] as String,
                                        firebaseMeal[Const.DB_IMAGE_STRING] as String,
                                        firebaseMeal[Const.DB_PORTION] as String,
                                        firebaseMeal[Const.DB_FEELING] as String,
                                        firebaseMeal[Const.DB_SAVED] as Boolean,
                                        (firebaseMeal[Const.DB_VEGETABLE] as Long).toInt(),
                                        (firebaseMeal[Const.DB_FRUIT] as Long).toInt(),
                                        (firebaseMeal[Const.DB_GRAIN] as Long).toInt(),
                                        (firebaseMeal[Const.DB_FISH] as Long).toInt(),
                                        (firebaseMeal[Const.DB_POULTRY] as Long).toInt(),
                                        (firebaseMeal[Const.DB_REDMEAT] as Long).toInt(),
                                        (firebaseMeal[Const.DB_OIL] as Long).toInt(),
                                        (firebaseMeal[Const.DB_DAIRY] as Long).toInt(),
                                        (firebaseMeal[Const.DB_TIMES_EATEN] as Long).toInt(),
                                    )

                                    savedMeals.add(meal)
                                }
                            }
                        }

                        list = binding.savedMealsList

                        list.adapter = SavedMealListAdapter(savedMeals, this@SavedMealsActivity) {
                            updateButtonStates()
                        }

                        list.layoutManager = LinearLayoutManager(
                            this@SavedMealsActivity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                }
            )
    }

    private fun updateButtonStates() {
        when ((list.adapter as SavedMealListAdapter).isFoodSelected()) {
            false -> {
                binding.savedMealsDetailsButton.isClickable = false
                binding.savedMealsDetailsButton.alpha = 0.5f
                binding.savedMealsDetailsButton.setOnClickListener(null)

                binding.savedMealsSelectButton.isClickable = false
                binding.savedMealsSelectButton.alpha = 0.5f
                binding.savedMealsSelectButton.setOnClickListener(null)
            }
            true -> {
                binding.savedMealsDetailsButton.isClickable = true
                binding.savedMealsDetailsButton.alpha = 1f
                binding.savedMealsDetailsButton.setOnClickListener {onDetail()}

                binding.savedMealsSelectButton.isClickable = true
                binding.savedMealsSelectButton.alpha = 1f
                binding.savedMealsSelectButton.setOnClickListener {onSelect()}
            }
        }
    }

    private fun onSelect() {
        val selectedPos = (list.adapter as SavedMealListAdapter).getSelectedPos()

        if (selectedPos != null) {
            val currentId: String = mealIdList[selectedPos]

            selectedMeal = (list.adapter as SavedMealListAdapter).getSelectedFood()!!
            selectedMeal.timesEaten++

            userDataRef.child(userKey!!).child(Const.DB_PAST_MEALS).child(currentId).setValue(selectedMeal)

            // Update pet stats
            val petStatsRef = userDataRef.child(userKey).child(Const.DB_PET_STATS)
            val petStats = PetStats()

            petStatsRef.addListenerForSingleValueEvent( object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dbPetStats = snapshot.value as HashMap<*, *>
                    petStats.feeling = dbPetStats[Const.DB_FEELING] as String
                    petStats.timeLastEaten = dbPetStats[Const.DB_LAST_EATEN] as String
                    petStats.timeLastDecay = dbPetStats[Const.DB_LAST_DECAY] as String
                    petStats.vegetableServings = dbPetStats[Const.DB_VEGETABLE] as Long
                    petStats.fruitServings = dbPetStats[Const.DB_FRUIT] as Long
                    petStats.grainServings = dbPetStats[Const.DB_GRAIN] as Long
                    petStats.fishServings = dbPetStats[Const.DB_FISH] as Long
                    petStats.poultryServings = dbPetStats[Const.DB_POULTRY] as Long
                    petStats.redMeatServings = dbPetStats[Const.DB_REDMEAT] as Long
                    petStats.oilServings = dbPetStats[Const.DB_OIL] as Long
                    petStats.dairyServings = dbPetStats[Const.DB_DAIRY] as Long

                    petStats.timeLastEaten = Instant.ofEpochMilli(System.currentTimeMillis())
                                                .atZone(ZoneId.systemDefault())
                                                .toLocalDateTime().toString()
                    petStats.vegetableServings += selectedMeal!!.vegetableServings
                    petStats.fruitServings += selectedMeal.fruitServings
                    petStats.grainServings += selectedMeal.grainServings
                    petStats.fishServings += selectedMeal.fishServings
                    petStats.poultryServings += selectedMeal.poultryServings
                    petStats.redMeatServings += selectedMeal.redMeatServings
                    petStats.oilServings += selectedMeal.oilServings
                    petStats.dairyServings += selectedMeal.dairyServings

                    petStatsRef.setValue(petStats)
                    saveEvoDataAndNext()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    private fun saveEvoDataAndNext() {
        val evoStatsRef = Firebase.database.getReference(Const.DB_USERS).child(userKey).child(Const.DB_EVO_STATS)
        val evoStats = EvoStats()

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

                evoStats.totalServings += selectedMeal.vegetableServings + selectedMeal.fruitServings +
                        selectedMeal.grainServings + selectedMeal.fishServings +
                        selectedMeal.poultryServings + selectedMeal.redMeatServings +
                        selectedMeal.oilServings + selectedMeal.dairyServings
                evoStats.vegetableServings += selectedMeal.vegetableServings
                evoStats.fruitServings += selectedMeal.fruitServings
                evoStats.grainServings += selectedMeal.grainServings
                evoStats.fishServings += selectedMeal.fishServings
                evoStats.poultryServings += selectedMeal.poultryServings
                evoStats.redMeatServings += selectedMeal.redMeatServings
                evoStats.oilServings += selectedMeal.oilServings
                evoStats.dairyServings += selectedMeal.dairyServings

                evoStatsRef.setValue(evoStats)
                navigateToNextScreen()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun navigateToNextScreen() {
        val intent = Intent(this@SavedMealsActivity, FeedPetActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(Const.EXTRA_CODE_NEW_MEAL, selectedMeal)
        startActivity(intent)
        finish()
    }

    private fun onDetail() {
        val selectedMeal: NewMeal? = (list.adapter as SavedMealListAdapter).getSelectedFood()

        val intent: Intent = Intent(this, ViewPastMealActivity::class.java)
        intent.putExtra("name", selectedMeal?.name)
        intent.putExtra("image_string", selectedMeal?.imageString)
        intent.putExtra("vegetable", selectedMeal?.vegetableServings)
        intent.putExtra("fruit", selectedMeal?.fruitServings)
        intent.putExtra("grain", selectedMeal?.grainServings)
        intent.putExtra("fish", selectedMeal?.fishServings)
        intent.putExtra("poultry", selectedMeal?.poultryServings)
        intent.putExtra("red_meat", selectedMeal?.redMeatServings)
        intent.putExtra("oil", selectedMeal?.oilServings)
        intent.putExtra("dairy", selectedMeal?.dairyServings)
        intent.putExtra("times_eaten", selectedMeal?.timesEaten)
        startActivity(intent)
    }
}