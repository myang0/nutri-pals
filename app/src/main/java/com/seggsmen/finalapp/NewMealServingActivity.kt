package com.seggsmen.finalapp

import android.annotation.SuppressLint
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




class NewMealServingActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewMealServingBinding
    lateinit var pieChart: PieChart

    @SuppressLint("ClickableViewAccessibility")
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
        binding.dairyCard.post {
            setFoodTilesDraggable()
        }
    }
    private fun navigateToNextScreen() {
        val intent: Intent = Intent(this, FeedPetActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
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
                Toast.makeText(this, "Vegetable", Toast.LENGTH_SHORT).show()
            }
            binding.fruitCard.id -> {
                Toast.makeText(this, "Fruit", Toast.LENGTH_SHORT).show()
            }
            binding.grainCard.id -> {
                Toast.makeText(this, "Grain", Toast.LENGTH_SHORT).show()
            }
            binding.fishCard.id -> {
                Toast.makeText(this, "Fish", Toast.LENGTH_SHORT).show()
            }
            binding.poultryCard.id -> {
                Toast.makeText(this, "Poultry", Toast.LENGTH_SHORT).show()
            }
            binding.redMeatCard.id -> {
                Toast.makeText(this, "Red Meat", Toast.LENGTH_SHORT).show()
            }
            binding.oilCard.id -> {
                Toast.makeText(this, "Healthy Oil", Toast.LENGTH_SHORT).show()
            }
            binding.dairyCard.id -> {
                Toast.makeText(this, "Dairy", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "INVALID CARD DRAGGED ONTO CHART", Toast.LENGTH_SHORT).show()
            }
        }
    }
}