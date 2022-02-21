package com.seggsmen.finalapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seggsmen.finalapp.databinding.ActivitySavedMealsBinding
import com.seggsmen.finalapp.logic.SavedMeal
import com.seggsmen.finalapp.logic.SavedMealListAdapter
import com.seggsmen.finalapp.logic.sampleFoodsList

class SavedMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySavedMealsBinding
    lateinit var list: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivitySavedMealsBinding.inflate (layoutInflater);
        setContentView(binding.root)

        setSupportActionBar(binding.savedMealsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.savedMealsToolbar.setNavigationOnClickListener {onBackPressed()}
        list = binding.savedMealsList
        list.adapter = SavedMealListAdapter(sampleFoodsList(resources), this) {
            updateButtonStates()
        }
        list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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
        val intent: Intent = Intent(this, FeedPetActivity::class.java)
        startActivity(intent)
    }

    private fun onDetail() {
        val selectedMeal: SavedMeal? = (list.adapter as SavedMealListAdapter).getSelectedFood()

        val intent: Intent = Intent(this, ViewPastMealActivity::class.java)
        intent.putExtra("name", selectedMeal?.name)
        intent.putExtra("image_id", selectedMeal?.image)
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