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
import com.seggsmen.finalapp.databinding.ActivityNewMealServingBinding

class NewMealServingActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewMealServingBinding
    lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewMealServingBinding.inflate (layoutInflater)
        setContentView(binding.root)
        binding.nextButton.setOnClickListener {navigateToNextScreen()}

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {onBackPressed()}

        pieChart = binding.nutritionPieChart
        initPieChart()
        setPieChartData()
    }

    private fun navigateToNextScreen() {
    }

    private fun initPieChart() {
        pieChart.description.text = ""

        pieChart.isDrawHoleEnabled = false
        pieChart.isRotationEnabled = false
        pieChart.setTouchEnabled(false)
        pieChart.setDrawEntryLabels(false)

        pieChart.legend.isEnabled = false
    }

    private fun setPieChartData() {
        pieChart.setUsePercentValues(true)
        val dataEntries = ArrayList<PieEntry>()
        dataEntries.add(PieEntry(15f))
        dataEntries.add(PieEntry(15f))
        dataEntries.add(PieEntry(15f))
        dataEntries.add(PieEntry(12.5f))
        dataEntries.add(PieEntry(12.5f))
        dataEntries.add(PieEntry(12.5f))
        dataEntries.add(PieEntry(2.5f))
        dataEntries.add(PieEntry(15f))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#4a8427"))
        colors.add(Color.parseColor("#e2644f"))
        colors.add(Color.parseColor("#c7ad2b"))
        colors.add(Color.parseColor("#4ea4ad"))
        colors.add(Color.parseColor("#c57923"))
        colors.add(Color.parseColor("#ca502e"))
        colors.add(Color.parseColor("#ebc351"))
        colors.add(Color.parseColor("#4e77c1"))

        val dataSet = PieDataSet(dataEntries, "")
        dataSet.sliceSpace = 3f
        dataSet.colors = colors
        dataSet.setDrawValues(false)

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())

        pieChart.data = data
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        pieChart.invalidate()
    }
}