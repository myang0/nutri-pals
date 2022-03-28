package com.seggsmen.finalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.seggsmen.finalapp.databinding.EvolutionPageBinding
import com.seggsmen.finalapp.logic.Const
import kotlin.math.max

class EvolutionActivity : AppCompatActivity() {
    lateinit var binding: EvolutionPageBinding
    lateinit var evolMsg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EvolutionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        transition = binding.transitionScreen
//        bjingus = binding.evoBjingus1

        binding.evolMsg.text = "Your pet is evolving!"

        fadeIn()
        binding.transitionScreen.setOnClickListener { evolveAnim() }
        binding.finishedButton.setOnClickListener { returnToHomeScreen() }
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
        binding.transitionScreen.animate().alpha(1f).startDelay = 2500

        binding.tapText.alpha = 0f
        binding.tapText.animate().alpha(0.5f).duration = 300
    }

    private fun evolveAnim(){
        binding.evolMsg.alpha = 0f
        binding.evolMsg.animate().alpha(1f).duration = 750
        binding.evolMsg.animate().alpha(1f).startDelay = 1500
        evolMsg = "Your pet has evolved!"
        binding.finishedButton.visibility = View.VISIBLE
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
        binding.finishedButton.animate().alpha(1f).duration = 1000
        binding.finishedButton.animate().alpha(1f).startDelay = 2500

        binding.evolMsg.text = "Your pet has evolved!"
    }

    private fun changePet(){
        var evoType: String = Const.STRING_NO_VALUE
        var highestGroup: Long = -1

        // random hardcoded values for testing
        var vegetableServings: Long = 16
        var fruitServings: Long = 5
        var grainServings: Long = 11
        var fishServings: Long = 7
        var poultryServings: Long = 6
        var redMeatServings: Long = 12
        var oilServings: Long = 6
        var dairyServings: Long = 7

        // trying to get the highest value to choose which evolution to evolve into
        val servingsList = listOf(vegetableServings, fruitServings, grainServings, fishServings, poultryServings, redMeatServings, oilServings, dairyServings)
        servingsList.sorted()
        servingsList.forEach {
            println(servingsList)
        }

//        if (highestGroup == vegetableServings) {
        binding.evoBjingus1.setImageResource(R.drawable.bjingusred)
//        }
    }

    private fun returnToHomeScreen() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}