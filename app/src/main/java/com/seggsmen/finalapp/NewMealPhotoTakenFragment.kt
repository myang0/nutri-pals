package com.seggsmen.finalapp

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
import com.seggsmen.finalapp.databinding.FragmentNewMealPhotoTakenBinding

class NewMealPhotoTakenFragment : Fragment() {
    lateinit var binding: FragmentNewMealPhotoTakenBinding
    lateinit var pager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewMealPhotoTakenBinding.inflate(layoutInflater)
        binding.nextButton.setOnClickListener {navigateToNextScreen()}

        var activity = activity as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pager = activity?.findViewById(R.id.pager)!!
        binding.toolbar.setNavigationOnClickListener {pager.currentItem = pager.currentItem-1}

        return binding.root
    }

    private fun navigateToNextScreen() {
        pager.currentItem = pager.currentItem+1
    }
}