package com.seggsmen.finalapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.seggsmen.finalapp.databinding.EvolutionPageBinding
import com.seggsmen.finalapp.logic.Const
import com.seggsmen.finalapp.logic.EvoStats
import com.seggsmen.finalapp.logic.PetStats
import kotlin.math.max

class EvolutionActivity : AppCompatActivity() {
    lateinit var binding: EvolutionPageBinding
    lateinit var evolMsg: String

    var petStats = PetStats()
    var evoStats = EvoStats()
    lateinit var userKey: String

    lateinit var sharedPrefs: SharedPreferences
    lateinit var petStatsRef: DatabaseReference
    lateinit var evoStatsRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadPetStats()
        binding = EvolutionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        transition = binding.transitionScreen
//        bjingus = binding.evoBjingus1

        binding.evolMsg.text = "Your pet is evolving!"

        binding.transitionScreen.setOnClickListener { evolveAnim() }

    }

    private fun fadeIn() {
        binding.star.alpha = 0f
        binding.star1.alpha = 0f
        binding.star2.alpha = 0f
        binding.star3.alpha = 0f
        binding.star4.alpha = 0f
        binding.star5.alpha = 0f
        binding.star6.alpha = 0f
        binding.star7.alpha = 0f

        binding.transitionScreen.alpha = 0f
        binding.transitionScreen.animate().alpha(1f).duration = 1000
        binding.transitionScreen.animate().alpha(1f).startDelay = 2250

        binding.tapText.alpha = 0f
        binding.tapText.animate().alpha(0.5f).duration = 300
    }

    private fun evolveAnim(){
        binding.transitionScreen.setOnClickListener(null)
        binding.evolMsg.alpha = 0f
        binding.evolMsg.animate().alpha(1f).duration = 750
        binding.evolMsg.animate().alpha(1f).startDelay = 1500
        evolMsg = "Your pet has evolved!"
        binding.finishedButton.visibility = View.VISIBLE
        binding.star.visibility = View.VISIBLE
        binding.star1.visibility = View.VISIBLE
        binding.star2.visibility = View.VISIBLE
        binding.star3.visibility = View.VISIBLE
        binding.star4.visibility = View.VISIBLE
        binding.star5.visibility = View.VISIBLE
        binding.star6.visibility = View.VISIBLE
        binding.star7.visibility = View.VISIBLE
        binding.tapText.alpha = 0f
        changePet()

        // evolution animation
        // start by fading out the screen
        binding.transitionScreen.alpha = 1f
        binding.transitionScreen.animate().alpha(0f).duration = 750

        binding.star.alpha = 0f
        binding.star.animate().alpha(1f).duration = 1300
        binding.star.animate().alpha(1f).startDelay = 1500

        binding.star1.alpha = 0f
        binding.star1.animate().alpha(1f).duration = 1300
        binding.star1.animate().alpha(1f).startDelay = 1500

        binding.star2.alpha = 0f
        binding.star2.animate().alpha(1f).duration = 1300
        binding.star2.animate().alpha(1f).startDelay = 1500

        binding.star3.alpha = 0f
        binding.star3.animate().alpha(1f).duration = 1300
        binding.star3.animate().alpha(1f).startDelay = 1500

        binding.star4.alpha = 0f
        binding.star4.animate().alpha(1f).duration = 1300
        binding.star4.animate().alpha(1f).startDelay = 1500

        binding.star5.alpha = 0f
        binding.star5.animate().alpha(1f).duration = 1300
        binding.star5.animate().alpha(1f).startDelay = 1500

        binding.star6.alpha = 0f
        binding.star6.animate().alpha(1f).duration = 1300
        binding.star6.animate().alpha(1f).startDelay = 1500

        binding.star7.alpha = 0f
        binding.star7.animate().alpha(1f).duration = 1300
        binding.star7.animate().alpha(1f).startDelay = 1500

        binding.finishedButton.alpha = 0f
        binding.finishedButton.animate().alpha(1f).withEndAction {
            binding.finishedButton.setOnClickListener { returnToHomeScreen() }}.duration = 1000
        binding.finishedButton.animate().alpha(1f).startDelay = 2500

        binding.evolMsg.text = "Your pet has evolved!"


    }

    private fun changePet(){
        var evoType: String = Const.STRING_NO_VALUE
        var highestGroup: Long = -1

        // trying to get the highest value to choose which evolution to evolve into
        val servingsList = arrayOf(evoStats.vegetableServings,evoStats.fruitServings,
            evoStats.grainServings, evoStats.fishServings, evoStats.poultryServings,
            evoStats.redMeatServings, evoStats.oilServings, evoStats.dairyServings)
        val sortedList = arrayOf(evoStats.vegetableServings,evoStats.fruitServings,
            evoStats.grainServings, evoStats.fishServings, evoStats.poultryServings,
            evoStats.redMeatServings, evoStats.oilServings, evoStats.dairyServings)
        sortedList.sortDescending()
        highestGroup = sortedList[0]

        // check highest group value
        println(highestGroup)

        if (highestGroup == servingsList[0]){
            // change drawable to veggie bjingus
            binding.evoBjingus1.setImageResource(R.drawable.bjingusorange)
            evoStats.evoType = Const.EVO_VEGETABLES_MEDIUM
        }
        else if (highestGroup == servingsList[1]) {
            // change drawable to fruit bjingus
            binding.evoBjingus1.setImageResource(R.drawable.bjingusred)
            evoStats.evoType = Const.EVO_FRUITS_MEDIUM
        }
        else if (highestGroup == servingsList[2]) {
            // change drawable to grain bjingus
            binding.evoBjingus1.setImageResource(R.drawable.bjingusred)
            evoStats.evoType = Const.EVO_GRAINS_MEDIUM
        }
        else if (highestGroup == servingsList[3]) {
            // change drawable to fish bjingus
            binding.evoBjingus1.setImageResource(R.drawable.bjingusred)
            evoStats.evoType = Const.EVO_FISH_MEDIUM
        }
        else if (highestGroup == servingsList[4]) {
            // change drawable to poultry bjingus
            binding.evoBjingus1.setImageResource(R.drawable.bjingusorange)
            evoStats.evoType = Const.EVO_POULTRY_MEDIUM
        }
        else if (highestGroup == servingsList[5]) {
            // change drawable to red meat bjingus
            binding.evoBjingus1.setImageResource(R.drawable.bjingusred)
            evoStats.evoType = Const.EVO_REDMEAT_MEDIUM
        }
        else if (highestGroup == servingsList[6]) {
            // change drawable to oil bjingus
            binding.evoBjingus1.setImageResource(R.drawable.bjingusred)
            evoStats.evoType = Const.EVO_OIL_MEDIUM
        }
        else if (highestGroup == servingsList[7]) {
            // change drawable to dairy bjingus
            binding.evoBjingus1.setImageResource(R.drawable.bjingusred)
            evoStats.evoType = Const.EVO_DAIRY_MEDIUM
        }
        evoStatsRef.setValue(evoStats)
    }

    private fun loadPetStats() {
        sharedPrefs = this.getSharedPreferences(Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        userKey = sharedPrefs.getString(Const.USER_KEY, Const.STRING_NO_VALUE)!!

        petStatsRef = Firebase.database.getReference(Const.DB_USERS).child(userKey).child(Const.DB_PET_STATS)

        petStatsRef.addListenerForSingleValueEvent( object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val dbPetStats = snapshot.value as HashMap<*, *>
                petStats.petName = dbPetStats [Const.DB_PETNAME] as String
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

                loadEvoData()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    private fun loadEvoData() {
        userKey = sharedPrefs.getString(Const.USER_KEY, Const.STRING_NO_VALUE)!!

        evoStatsRef = Firebase.database.getReference(Const.DB_USERS).child(userKey).child(Const.DB_EVO_STATS)
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

                fadeIn()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun returnToHomeScreen() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}