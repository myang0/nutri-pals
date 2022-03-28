package com.seggsmen.finalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.seggsmen.finalapp.databinding.EvolutionPageBinding
import com.seggsmen.finalapp.logic.Const

class EvolutionActivity : AppCompatActivity() {
    lateinit var binding: EvolutionPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EvolutionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        transition = binding.transitionScreen
//        bjingus = binding.evoBjingus1

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
        binding.finishedButton.alpha = 0f

        binding.transitionScreen.alpha = 0f
        binding.transitionScreen.animate().alpha(1f).duration = 1000
        binding.transitionScreen.animate().alpha(1f).startDelay = 2000

        binding.tapText.alpha = 0f
        binding.tapText.animate().alpha(0.5f).duration = 300
        binding.tapText.animate().alpha(0.5f).startDelay = 2500
    }

    private fun evolveAnim(){
        binding.tapText.alpha = 0f
        changePet()

        // evolution animation
        // start by fading out the screen
        binding.transitionScreen.alpha = 1f
        binding.transitionScreen.animate().alpha(0f).duration = 750

        binding.star.alpha = 0f
        binding.star.animate().alpha(1f).duration = 1000
        binding.star.animate().alpha(1f).startDelay = 1500

        binding.star1.alpha = 0f
        binding.star1.animate().alpha(1f).duration = 1000
        binding.star1.animate().alpha(1f).startDelay = 1500

        binding.star2.alpha = 0f
        binding.star2.animate().alpha(1f).duration = 1000
        binding.star2.animate().alpha(1f).startDelay = 1500

        binding.star3.alpha = 0f
        binding.star3.animate().alpha(1f).duration = 1000
        binding.star3.animate().alpha(1f).startDelay = 1500

        binding.star4.alpha = 0f
        binding.star4.animate().alpha(1f).duration = 1000
        binding.star4.animate().alpha(1f).startDelay = 1500

        binding.star5.alpha = 0f
        binding.star5.animate().alpha(1f).duration = 1000
        binding.star5.animate().alpha(1f).startDelay = 1500

        binding.star6.alpha = 0f
        binding.star6.animate().alpha(1f).duration = 1000
        binding.star6.animate().alpha(1f).startDelay = 1500

        binding.star7.alpha = 0f
        binding.star7.animate().alpha(1f).duration = 1000
        binding.star7.animate().alpha(1f).startDelay = 1500

        binding.finishedButton.alpha = 0f
        binding.finishedButton.animate().alpha(1f).duration = 1000
        binding.finishedButton.animate().alpha(1f).startDelay = 2500
    }

    private fun changePet(){
        var evoType: String = Const.STRING_NO_VALUE
        var vegetableServings: Float
        var fruitServings: Long
        var grainServings: Long
        var fishServings: Long
        var poultryServings: Long
        var redMeatServings: Long
        var oilServings: Long
        var dairyServings: Long

        //if vegetableServings >=

        binding.evoBjingus1.setImageResource(R.drawable.bjingusred)
    }

    private fun returnToHomeScreen() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}