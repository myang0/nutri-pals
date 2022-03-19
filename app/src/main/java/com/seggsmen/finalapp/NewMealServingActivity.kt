package com.seggsmen.finalapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.seggsmen.finalapp.databinding.ActivityNewMealServingBinding
import android.view.View.OnTouchListener
import android.widget.TextView
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.seggsmen.finalapp.logic.Const
import com.seggsmen.finalapp.logic.NewMeal
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.floor

class NewMealServingActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewMealServingBinding
    lateinit var pieChart: PieChart

    private val colorMap: Map<String, Int> = mapOf(
        "vegetable" to Color.parseColor("#4a8427"),
        "fruit" to Color.parseColor("#e2644f"),
        "grain" to Color.parseColor("#c7ad2b"),
        "fish" to Color.parseColor("#4ea4ad"),
        "poultry" to Color.parseColor("#c57923"),
        "redMeat" to Color.parseColor("#ca502e"),
        "oil" to Color.parseColor("#ebc351"),
        "dairy" to Color.parseColor("#4e77c1"),
    )

    private val foodGroupDisplayNameMap: Map<String, String> = mapOf(
        "vegetable" to "Vegetables",
        "fruit" to "Fruits",
        "grain" to "Grains",
        "fish" to "Fish",
        "poultry" to "Poultry",
        "redMeat" to "Red Meat",
        "oil" to "Healthy Oils",
        "dairy" to "Dairy",
    )

    lateinit var servingsMap: MutableMap<String, Int>
    var totalServings: Int = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewMealServingBinding.inflate (layoutInflater)
        setContentView(binding.root)

        binding.nextButton.setOnClickListener {navigateToNextScreen()}

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {onBackPressed()}

        servingsMap = mutableMapOf(
            "vegetable" to 0,
            "fruit" to 0,
            "grain" to 0,
            "fish" to 0,
            "poultry" to 0,
            "redMeat" to 0,
            "oil" to 0,
            "dairy" to 0,
        )

        binding.indicator.createIndicators(3, 2)

        pieChart = binding.nutritionPieChart

        initializePieChart()
        setPieChartData()

        setFoodGroupCardClickListeners()
    }
    private fun navigateToNextScreen() {
        val newMeal = intent.getParcelableExtra<NewMeal>(Const.EXTRA_CODE_NEW_MEAL)

        newMeal!!.timesEaten = 1
        newMeal.vegetableServings = servingsMap["vegetable"]!!
        newMeal.fruitServings = servingsMap["fruit"]!!
        newMeal.grainServings = servingsMap["grain"]!!
        newMeal.fishServings = servingsMap["fish"]!!
        newMeal.poultryServings = servingsMap["poultry"]!!
        newMeal.redMeatServings = servingsMap["redMeat"]!!
        newMeal.oilServings = servingsMap["oil"]!!
        newMeal.dairyServings = servingsMap["dairy"]!!

        val databaseRef = Firebase.database
        val userDataRef = databaseRef.getReference(Const.DB_USERS)

        val sharedPrefs = this.getSharedPreferences(Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        val userKey = sharedPrefs.getString(Const.USER_KEY, Const.STRING_NO_VALUE)

        val uuid: String = UUID.randomUUID().toString()
        userDataRef.child(userKey!!).child(Const.DB_PAST_MEALS).child("Meal_${uuid}").setValue(newMeal)

        val intent = Intent(this, FeedPetActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(Const.EXTRA_CODE_NEW_MEAL, newMeal)
        startActivity(intent)
        finish()
    }

    private fun initializePieChart() {
        pieChart.description.text = ""

        pieChart.isDrawHoleEnabled = false
        pieChart.isRotationEnabled = false
        pieChart.setTouchEnabled(false)

        pieChart.legend.isEnabled = false

        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)
    }

    private fun setPieChartData() {
        val dataEntries = ArrayList<PieEntry>()
        for ((foodGroup, servingCount) in servingsMap) {
            if (servingCount > 0) {
                dataEntries.add(
                    PieEntry(
                        servingCount.toFloat(),
                        foodGroupDisplayNameMap[foodGroup]!!
                    )
                )
            }
        }

        val dataColors = ArrayList<Int>()
        for ((foodGroup, servingCount) in servingsMap) {
            if (servingCount > 0) {
                dataColors.add(colorMap[foodGroup]!!)
            }
        }

        val dataSet = PieDataSet(dataEntries, "")
        dataSet.colors = dataColors

        val data = PieData(dataSet)
        data.setValueTextSize(12f)
        data.setValueFormatter(IntFormatter())

        pieChart.data = data

        pieChart.invalidate()
    }

    private fun setFoodGroupCardClickListeners() {
        for ((foodGroup, _) in servingsMap) {
            var cardId = "${foodGroup}Card"

            var resId: Int = resources.getIdentifier(cardId, "id", packageName)

            var foodGroupCard = findViewById<CardView>(resId)
            foodGroupCard.setOnClickListener { addPortion(foodGroup) }
        }
    }

    private fun addPortion(foodGroup: String) {
        if (!servingsMap.containsKey(foodGroup)) return

        servingsMap[foodGroup] = servingsMap[foodGroup]!!.plus(1)
        totalServings++

        setPieChartData()
    }

    inner class IntFormatter(): ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return floor(value).toInt().toString()
        }
    }
}