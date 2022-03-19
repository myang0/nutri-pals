package com.seggsmen.finalapp

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.seggsmen.finalapp.databinding.ActivityViewPastMealBinding
import com.seggsmen.finalapp.logic.Const
import com.seggsmen.finalapp.logic.SavedMeal
import com.seggsmen.finalapp.logic.SavedMealListAdapter
import com.seggsmen.finalapp.util.BitmapConverter

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

        val imageString = intent.getStringExtra("image_string")

        if (imageString == Const.STRING_NO_VALUE || imageString == null) {
            binding.viewPastMealImage.setImageDrawable(resources.getDrawable(R.drawable.placeholder, applicationContext.theme))
        } else {
            val imageBitmap: Bitmap = BitmapConverter.convertStringToBitmap(imageString)
            binding.viewPastMealImage.setImageBitmap(imageBitmap)
        }

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