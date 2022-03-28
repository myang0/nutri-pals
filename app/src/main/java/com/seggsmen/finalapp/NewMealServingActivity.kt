package com.seggsmen.finalapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.seggsmen.finalapp.databinding.ActivityNewMealServingBinding
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.seggsmen.finalapp.logic.Const
import com.seggsmen.finalapp.logic.EvoStats
import com.seggsmen.finalapp.logic.NewMeal
import com.seggsmen.finalapp.logic.PetStats
import java.time.Instant
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.floor

class NewMealServingActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewMealServingBinding
    lateinit var pieChart: PieChart
    lateinit var newMeal: NewMeal
    lateinit var sharedPrefs: SharedPreferences
    lateinit var userKey: String

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
    private fun saveMealDataAndNext() {
        newMeal = intent.getParcelableExtra(Const.EXTRA_CODE_NEW_MEAL)!!

        newMeal.timesEaten = 1
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

        sharedPrefs = this.getSharedPreferences(Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        userKey = sharedPrefs.getString(Const.USER_KEY, Const.STRING_NO_VALUE)!!

        val uuid: String = UUID.randomUUID().toString()
        userDataRef.child(userKey).child(Const.DB_PAST_MEALS).child("Meal_${uuid}").setValue(newMeal)


        // Update pet stats
        val petStatsRef = userDataRef.child(userKey).child(Const.DB_PET_STATS)
        val petStats = PetStats()

        petStatsRef.addListenerForSingleValueEvent( object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dbPetStats = snapshot.value as HashMap<*, *>
                petStats.feeling = dbPetStats [Const.DB_FEELING] as String
                petStats.timeLastEaten = dbPetStats [Const.DB_LAST_EATEN] as String
                petStats.timeLastDecay = dbPetStats [Const.DB_LAST_DECAY] as String
                petStats.vegetableServings = dbPetStats [Const.DB_VEGETABLE] as Long
                petStats.fruitServings = dbPetStats [Const.DB_FRUIT] as Long
                petStats.grainServings = dbPetStats [Const.DB_GRAIN] as Long
                petStats.fishServings = dbPetStats [Const.DB_FISH] as Long
                petStats.poultryServings = dbPetStats [Const.DB_POULTRY] as Long
                petStats.redMeatServings = dbPetStats [Const.DB_REDMEAT] as Long
                petStats.oilServings = dbPetStats [Const.DB_OIL] as Long
                petStats.dairyServings = dbPetStats [Const.DB_DAIRY] as Long

                petStats.timeLastEaten = Instant.ofEpochMilli(System.currentTimeMillis())
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDateTime().toString()
                petStats.vegetableServings += newMeal.vegetableServings
                petStats.fruitServings += newMeal.fruitServings
                petStats.grainServings += newMeal.grainServings
                petStats.fishServings += newMeal.fishServings
                petStats.poultryServings += newMeal.poultryServings
                petStats.redMeatServings += newMeal.redMeatServings
                petStats.oilServings += newMeal.oilServings
                petStats.dairyServings += newMeal.dairyServings

                petStatsRef.setValue(petStats)
                saveEvoDataAndNext()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun saveEvoDataAndNext() {
        val evoStatsRef = Firebase.database.getReference(Const.DB_USERS).child(userKey).child(Const.DB_EVO_STATS)
        val evoStats = EvoStats()

        evoStatsRef.addListenerForSingleValueEvent( object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val dbEvoStats = snapshot.value as HashMap<*, *>
                evoStats.evoType = dbEvoStats [Const.DB_EVO_TYPE] as String
                evoStats.timeLastEvo = dbEvoStats [Const.DB_LAST_EVO] as String
                evoStats.totalServings = dbEvoStats [Const.DB_TOTAL_SERVINGS] as Long
                evoStats.starvedServings = dbEvoStats [Const.DB_STARVED_SERVINGS] as Long
                evoStats.vegetableServings = dbEvoStats [Const.DB_VEGETABLE] as Long
                evoStats.fruitServings = dbEvoStats [Const.DB_FRUIT] as Long
                evoStats.grainServings = dbEvoStats [Const.DB_GRAIN] as Long
                evoStats.fishServings = dbEvoStats [Const.DB_FISH] as Long
                evoStats.poultryServings = dbEvoStats [Const.DB_POULTRY] as Long
                evoStats.redMeatServings = dbEvoStats [Const.DB_REDMEAT] as Long
                evoStats.oilServings = dbEvoStats [Const.DB_OIL] as Long
                evoStats.dairyServings = dbEvoStats [Const.DB_DAIRY] as Long

                evoStats.totalServings += newMeal.vegetableServings + newMeal.fruitServings +
                        newMeal.grainServings + newMeal.fishServings +
                        newMeal.poultryServings + newMeal.redMeatServings +
                        newMeal.oilServings + newMeal.dairyServings
                evoStats.vegetableServings += newMeal.vegetableServings
                evoStats.fruitServings += newMeal.fruitServings
                evoStats.grainServings += newMeal.grainServings
                evoStats.fishServings += newMeal.fishServings
                evoStats.poultryServings += newMeal.poultryServings
                evoStats.redMeatServings += newMeal.redMeatServings
                evoStats.oilServings += newMeal.oilServings
                evoStats.dairyServings += newMeal.dairyServings

                evoStatsRef.setValue(evoStats)
                navigateToNextScreen()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun navigateToNextScreen() {
        val intent = Intent(this@NewMealServingActivity, FeedPetActivity::class.java)
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

        if (!binding.nextButton.isClickable) {
            binding.nextButton.isClickable = true
            binding.nextButton.alpha = 1f
            binding.nextButton.setOnClickListener {
                binding.nextButton.setOnClickListener(null)
                saveMealDataAndNext()
            }
        }

        setPieChartData()
    }

    inner class IntFormatter(): ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return floor(value).toInt().toString()
        }
    }
}