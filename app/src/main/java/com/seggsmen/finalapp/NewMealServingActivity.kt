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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.seggsmen.finalapp.logic.Const
import com.seggsmen.finalapp.logic.NewMeal
import java.util.*
import kotlin.collections.ArrayList

class NewMealServingActivity : AppCompatActivity() {
    lateinit var dragDestinationText: TextView

    lateinit var binding: ActivityNewMealServingBinding
    lateinit var pieChart: PieChart

    val pieDataColors: ArrayList<Int> = arrayListOf<Int>(
        Color.parseColor("#4a8427"),
        Color.parseColor("#e2644f"),
        Color.parseColor("#c7ad2b"),
        Color.parseColor("#4ea4ad"),
        Color.parseColor("#c57923"),
        Color.parseColor("#ca502e"),
        Color.parseColor("#ebc351"),
        Color.parseColor("#4e77c1"),
    )

    lateinit var servingsMap: MutableMap<String, Int>
    var totalServings: Int = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewMealServingBinding.inflate (layoutInflater)
        setContentView(binding.root)

        dragDestinationText = binding.dragDestinationText

        binding.nextButton.setOnClickListener {navigateToNextScreen()}

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {onBackPressed()}

        servingsMap = mutableMapOf(
            "vegetables" to 0,
            "fruits" to 0,
            "grains" to 0,
            "seafood" to 0,
            "poultry" to 0,
            "meat" to 0,
            "oils" to 0,
            "dairy" to 0,
        )

        binding.dairyCard.post {
            setFoodTilesDraggable()
        }
        binding.indicator.createIndicators(3, 2)

        pieChart = binding.nutritionPieChart

        initializePieChart()
        setPieChartData()
    }
    private fun navigateToNextScreen() {
        //TODO: Set proper serving values
        val newMeal = intent.getParcelableExtra<NewMeal>(Const.EXTRA_CODE_NEW_MEAL)

        newMeal!!.timesEaten = 1
        newMeal.vegetableServings = 69
        newMeal.fruitServings = 0
        newMeal.grainServings = 0
        newMeal.fishServings = 0
        newMeal.poultryServings = 0
        newMeal.redMeatServings = 0
        newMeal.oilServings = 0
        newMeal.dairyServings = 0

        val databaseRef = Firebase.database
        val userDataRef = databaseRef.getReference(Const.DB_USERS)

        val sharedPrefs = this.getSharedPreferences(Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        val userKey = sharedPrefs.getString(Const.USER_KEY, Const.STRING_NO_VALUE)
        userDataRef.child(userKey!!).child(Const.DB_SAVED_MEALS).child(newMeal.name).setValue(newMeal)

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
        pieChart.setDrawEntryLabels(false)

        pieChart.legend.isEnabled = false

        pieChart.setUsePercentValues(true)

        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)
    }

    private fun setPieChartData() {
        if (dragDestinationText.visibility == View.VISIBLE && totalServings != 0) {
            dragDestinationText.visibility = View.INVISIBLE
        }

        val dataEntries = ArrayList<PieEntry>()
        dataEntries.add(PieEntry(countToPieEntryValue(servingsMap["vegetables"]!!)))
        dataEntries.add(PieEntry(countToPieEntryValue(servingsMap["fruits"]!!)))
        dataEntries.add(PieEntry(countToPieEntryValue(servingsMap["grains"]!!)))
        dataEntries.add(PieEntry(countToPieEntryValue(servingsMap["seafood"]!!)))
        dataEntries.add(PieEntry(countToPieEntryValue(servingsMap["poultry"]!!)))
        dataEntries.add(PieEntry(countToPieEntryValue(servingsMap["meat"]!!)))
        dataEntries.add(PieEntry(countToPieEntryValue(servingsMap["oils"]!!)))
        dataEntries.add(PieEntry(countToPieEntryValue(servingsMap["dairy"]!!)))

        val dataSet = PieDataSet(dataEntries, "")
        dataSet.sliceSpace = 2f
        dataSet.colors = pieDataColors
        dataSet.setDrawValues(false)

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())

        pieChart.data = data

        pieChart.invalidate()
    }

    private fun countToPieEntryValue(count: Int): Float {
        return if (totalServings == 0) 0f else ((count.toFloat() / totalServings.toFloat())) * 100f
    }

    var foodCardInitialPosArr = Array(8) {FloatArray(2)}
    var touchPos = FloatArray(2)

    @SuppressLint("ClickableViewAccessibility")
    private fun setFoodTilesDraggable() {
        var pieChartPositionArr = IntArray(2)
        pieChart.getLocationOnScreen(pieChartPositionArr)
        var foodCards = ArrayList<CardView>()
        foodCards.add(binding.vegetableCard)
        foodCards.add(binding.fruitCard)
        foodCards.add(binding.grainCard)
        foodCards.add(binding.fishCard)
        foodCards.add(binding.poultryCard)
        foodCards.add(binding.redMeatCard)
        foodCards.add(binding.oilCard)
        foodCards.add(binding.dairyCard)

        var foodLocationArr = Array(8) {IntArray(2)}
        for ((index, card) in foodCards.withIndex()) {
            card.getLocationInWindow(foodLocationArr[index])
            foodCardInitialPosArr[index][0] = foodLocationArr[index][0].toFloat() - card.height*0.14f
            foodCardInitialPosArr[index][1] = foodLocationArr[index][1].toFloat() - card.height*0.75f
            card.x = foodCardInitialPosArr[index][0]
            card.y = foodCardInitialPosArr[index][1]
            Log.d("shark", card.resources.getResourceName(card.id) + "Initial Pos [" + foodCardInitialPosArr[index][0] + ", " + foodCardInitialPosArr[index][1] + "]")

            card.setOnTouchListener { view, event ->
                view.parent.requestDisallowInterceptTouchEvent(true)
                var newX = 0f
                var newY = 0f

                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        touchPos[0] = event.x
                        touchPos[1] = event.y
                        Log.d("shark", card.resources.getResourceName(card.id) + "Down Pos [" + touchPos[0] + ", " + touchPos[1] + "]")
                    }
                    MotionEvent.ACTION_MOVE -> {
                        newX = event.x
                        newY = event.y

                        card.x += newX - touchPos[0]
                        card.y += newY - touchPos[1]
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        Log.d("shark", "CANCELED")
                    }
                    MotionEvent.ACTION_UP -> {
                        if (card.x > pieChartPositionArr[0]
                            && card.x < pieChartPositionArr[0] + pieChart.width/2
                            && card.y > pieChartPositionArr[1]
                            && card.y < pieChartPositionArr[1] + pieChart.height/2) {
                            onDragCardToChart(card)
                        } else {
                            card.x = foodCardInitialPosArr[index][0]
                            card.y = foodCardInitialPosArr[index][1]
                        }
                        Log.d("shark", "UP")
                    }
                }
                true
            }
        }
    }

    private fun onDragCardToChart(card: CardView) {
        when (card.id) {
            binding.vegetableCard.id -> {
                addPortion("vegetables")
            }
            binding.fruitCard.id -> {
                addPortion("fruits")
            }
            binding.grainCard.id -> {
                addPortion("grains")
            }
            binding.fishCard.id -> {
                addPortion("seafood")
            }
            binding.poultryCard.id -> {
                addPortion("poultry")
            }
            binding.redMeatCard.id -> {
                addPortion("meat")
            }
            binding.oilCard.id -> {
                addPortion("oils")
            }
            binding.dairyCard.id -> {
                addPortion("dairy")
            }
            else -> {
                Toast.makeText(this, "INVALID CARD DRAGGED ONTO CHART", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addPortion(foodGroup: String) {
        if (!servingsMap.containsKey(foodGroup)) return

        servingsMap[foodGroup] = servingsMap[foodGroup]!!.plus(1)
        totalServings++

        setPieChartData()
    }
}