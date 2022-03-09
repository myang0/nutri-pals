package com.seggsmen.finalapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.seggsmen.finalapp.databinding.ActivityViewPastMealBinding
import com.seggsmen.finalapp.logic.SavedMeal
import com.seggsmen.finalapp.logic.SavedMealListAdapter

class ViewPastMealActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewPastMealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewPastMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar3)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadIntentData()
    }

    private fun loadIntentData() {
        binding.viewPastMealName.text = intent.getStringExtra("name")
        binding.viewPastMealImage.setImageDrawable(ContextCompat.getDrawable(
            this, intent.getIntExtra("image_id", R.drawable.fried_rice)))
        binding.viewPastMealVegCount.text = intent.getIntExtra("vegetable", -1).toString()
        binding.viewPastMealFruitCount.text = intent.getIntExtra("fruit", -1).toString()
        binding.viewPastMealGrainCount.text = intent.getIntExtra("grain", -1).toString()
        binding.viewPastMealFishCount.text = intent.getIntExtra("fish", -1).toString()
        binding.viewPastMealPoultryCount.text = intent.getIntExtra("poultry", -1).toString()
        binding.viewPastMealRedMeatCount.text = intent.getIntExtra("red_meat", -1).toString()
        binding.viewPastMealOilCount.text = intent.getIntExtra("oil", -1).toString()
        binding.viewPastMealDairyCount.text = intent.getIntExtra("dairy", -1).toString()
        binding.viewPastMealTimesEaten.text = intent.getIntExtra("times_eaten", -1).toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}