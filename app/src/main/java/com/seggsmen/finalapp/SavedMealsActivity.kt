package com.seggsmen.finalapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.seggsmen.finalapp.databinding.ActivitySavedMealsBinding
import com.seggsmen.finalapp.logic.Const
import com.seggsmen.finalapp.logic.SavedMeal
import com.seggsmen.finalapp.logic.SavedMealListAdapter
import com.seggsmen.finalapp.logic.sampleFoodsList
import kotlin.math.ceil

class SavedMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySavedMealsBinding
    lateinit var list: RecyclerView

    private lateinit var mealIdList: ArrayList<String>

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
                        var savedMeals: ArrayList<SavedMeal> = arrayListOf()
                        if (snapshot.value != null) {
                            val mealsFromFirebase = snapshot.value as Map<String, Map<String, Any>>

                            mealIdList = arrayListOf()

                            for ((mealId, firebaseMeal) in mealsFromFirebase) {
                                val isSaved = firebaseMeal["saved"] as Boolean
                                if (isSaved) {
                                    mealIdList.add(mealId)

                                    var meal = SavedMeal(
                                        firebaseMeal["name"] as String,
                                        firebaseMeal["saved"] as Boolean,
                                        firebaseMeal["imageString"] as String,
                                        (firebaseMeal["vegetableServings"] as Long).toInt(),
                                        (firebaseMeal["fruitServings"] as Long).toInt(),
                                        (firebaseMeal["grainServings"] as Long).toInt(),
                                        (firebaseMeal["fishServings"] as Long).toInt(),
                                        (firebaseMeal["poultryServings"] as Long).toInt(),
                                        (firebaseMeal["redMeatServings"] as Long).toInt(),
                                        (firebaseMeal["oilServings"] as Long).toInt(),
                                        (firebaseMeal["dairyServings"] as Long).toInt(),
                                        (firebaseMeal["timesEaten"] as Long).toInt(),
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

            var selectedMeal: SavedMeal? = (list.adapter as SavedMealListAdapter).getSelectedFood()
            if (selectedMeal != null) {
                selectedMeal.timesEaten++
            }

            userDataRef.child(userKey!!).child(Const.DB_PAST_MEALS).child(currentId).setValue(selectedMeal)

            val intent = Intent(this, FeedPetActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra(Const.EXTRA_CODE_NEW_MEAL, selectedMeal)
            startActivity(intent)
            finish()
        }
    }

    private fun onDetail() {
        val selectedMeal: SavedMeal? = (list.adapter as SavedMealListAdapter).getSelectedFood()

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