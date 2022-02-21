package com.seggsmen.finalapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.seggsmen.finalapp.databinding.ActivityMealLibraryBinding
import com.seggsmen.finalapp.logic.*
import kotlin.math.ceil


class MealLibraryActivity : AppCompatActivity() {
    lateinit var binding: ActivityMealLibraryBinding
    lateinit var list: RecyclerView
    lateinit var meals: List<SavedMeal>
    var maxPageNumber = 1
    var currentPageNumber = 0

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMealLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mealLibraryToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        meals = sampleFoodsList(resources)
        maxPageNumber = ceil(((meals.size-1)/6f).toDouble()).toInt()

        viewPager = binding.mealLibraryViewPager
        viewPager.adapter = ScreenSlidePagerAdapter(this)
        binding.indicator.setViewPager(viewPager)
    }


    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = maxPageNumber

        override fun createFragment(position: Int): MealLibraryPageFragment {
            val bundle = Bundle()
            bundle.putParcelableArrayList("meals", ArrayList(sampleFoodsList(resources)))
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