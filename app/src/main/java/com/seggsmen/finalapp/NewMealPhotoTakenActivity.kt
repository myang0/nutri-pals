package com.seggsmen.finalapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.seggsmen.finalapp.databinding.ActivityNewMealPhotoTakenBinding

class NewMealPhotoTakenActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewMealPhotoTakenBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityNewMealPhotoTakenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.nextButton.setOnClickListener {navigateToNextScreen()}

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {onBackPressed()}

    }

    private fun navigateToNextScreen() {
        val intent: Intent = Intent(this, NewMealServingActivity::class.java)
        startActivity(intent)
    }
}