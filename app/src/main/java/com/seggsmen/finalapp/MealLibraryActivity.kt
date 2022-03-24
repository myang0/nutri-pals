package com.seggsmen.finalapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.seggsmen.finalapp.databinding.ActivityMealLibraryBinding
import com.seggsmen.finalapp.logic.*
import kotlin.math.ceil

class MealLibraryActivity : AppCompatActivity() {
    lateinit var binding: ActivityMealLibraryBinding
    lateinit var list: RecyclerView

    var meals = arrayListOf<SavedMeal>()
//    lateinit var meals: ArrayList<SavedMeal>

    var maxPageNumber = 1
    var currentPageNumber = 0

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMealLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mealLibraryToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fetchMeals()
    }

    private fun fetchMeals() {
        val dbRef = Firebase.database
        val userDataRef = dbRef.getReference(Const.DB_USERS)

        val sharedPrefs = this.getSharedPreferences(Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        val userKey = sharedPrefs.getString(Const.USER_KEY, Const.STRING_NO_VALUE)

        userDataRef
            .child(userKey!!)
            .child(Const.DB_PAST_MEALS)
            .addListenerForSingleValueEvent(
                object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        maxPageNumber = 1
                        if (snapshot.value != null) {
                            Log.d(Const.LOG, "Saved Meal data is present")
                            val mealsFromFirebase = snapshot.value as Map<String, Map<String, Any>>

//                            meals = arrayListOf()

                            for ((_, firebaseMeal) in mealsFromFirebase) {
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
                                    (firebaseMeal["timesEaten"] as Long).toInt()
                                )

                                meals.add(meal)
                            }

                            maxPageNumber = ceil(((meals.size-1)/6f).toDouble()).toInt()
                            if (maxPageNumber < 1) maxPageNumber = 1

                        }
                        viewPager = binding.mealLibraryViewPager
                        viewPager.adapter = ScreenSlidePagerAdapter(this@MealLibraryActivity)
                        binding.indicator.setViewPager(viewPager)
                        Log.d(Const.LOG, "HELLO?")
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                }
            )
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = maxPageNumber

        override fun createFragment(position: Int): MealLibraryPageFragment {
            val bundle = Bundle()
            bundle.putParcelableArrayList("meals", meals)
            bundle.putInt("pageNumber", currentPageNumber)
            currentPageNumber++

            val newFragment = MealLibraryPageFragment()
            newFragment.arguments = bundle
            return newFragment
        }
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