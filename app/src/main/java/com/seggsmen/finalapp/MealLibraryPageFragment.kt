package com.seggsmen.finalapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.seggsmen.finalapp.databinding.FragmentMealLibraryBinding
import com.seggsmen.finalapp.logic.SavedMeal

class MealLibraryPageFragment : Fragment() {
    lateinit var binding: FragmentMealLibraryBinding
    lateinit var meals: List<SavedMeal?>
    var pageNumber = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealLibraryBinding.inflate(layoutInflater)
        meals = arguments?.getParcelableArrayList("meals")!!
        pageNumber = arguments?.getInt("pageNumber")!!
        initializeTiles()
        return binding.root
    }

    private fun initializeTiles() {
        var currentMealIndex = 0 + pageNumber*6

        for (cardView in binding.mealLibraryLayout.children) {
            val layout = (cardView as ViewGroup).getChildAt(0)
            val imageView = (layout as ConstraintLayout).getChildAt(0) as ImageView
            val textView = layout.getChildAt(1) as TextView

            when {
                currentMealIndex > meals.size-1 -> {
                    imageView.setImageDrawable(null)
                    textView.text = ""
                }
                else -> {
                    imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, meals[currentMealIndex]!!.image))
                    textView.text = meals[currentMealIndex]!!.name
                    val mealIndex = currentMealIndex
                    cardView.setOnClickListener { loadMealDetail(mealIndex) }
                }
            }

            currentMealIndex++
        }
    }

    private fun loadMealDetail(mealIndex: Int) {
        val intent: Intent  = Intent(activity, ViewPastMealActivity::class.java)
        intent.putExtra("name", meals[mealIndex]!!.name)
        intent.putExtra("image_id", meals[mealIndex]!!.image)
        intent.putExtra("vegetable", meals[mealIndex]!!.vegetableServings)
        intent.putExtra("fruit", meals[mealIndex]!!.fruitServings)
        intent.putExtra("grain", meals[mealIndex]!!.grainServings)
        intent.putExtra("fish", meals[mealIndex]!!.fishServings)
        intent.putExtra("poultry", meals[mealIndex]!!.poultryServings)
        intent.putExtra("red_meat", meals[mealIndex]!!.redMeatServings)
        intent.putExtra("oil", meals[mealIndex]!!.oilServings)
        intent.putExtra("dairy", meals[mealIndex]!!.dairyServings)
        intent.putExtra("times_eaten", meals[mealIndex]!!.timesEaten)
        startActivity(intent)
    }


}