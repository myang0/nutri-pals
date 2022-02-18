package com.seggsmen.finalapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.seggsmen.finalapp.databinding.ActivityFeedPetBinding

class FeedPetActivity : AppCompatActivity() {
    lateinit var binding: ActivityFeedPetBinding
    lateinit var snack: ImageView
    lateinit var bjingus: ImageView
    val animationDuration = (500).toLong()
    var initialX = 0f
    var initialY = 0f
    var touchX = 0f
    var touchY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFeedPetBinding.inflate (layoutInflater)
        setContentView(binding.root)
        snack = binding.snack
        bjingus = binding.bjingus
        snack.post {
            setSnackDraggable()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("shark", "Click: " + event.x + ":" + event.y)
                true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun onSnackEat() {
        snack.visibility = View.GONE
        binding.dragToFeedText.visibility = View.INVISIBLE
        bjingus.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bjingus_smiling_anim))

        binding.bjingusHappyText.visibility = View.VISIBLE
        binding.bjingusHappyText.alpha = 0f
        binding.bjingusHappyText.animate().alpha(1f).duration = animationDuration

        binding.affectionText.visibility = View.VISIBLE
        binding.affectionText.alpha = 0f
        binding.affectionText.animate().alpha(1f).duration = animationDuration

        binding.heartFarLeft.animate().scaleX(1f).duration = animationDuration
        binding.heartFarLeft.animate().scaleY(1f).duration = animationDuration
        binding.heartFarLeft.animate().x(150f).duration = animationDuration
        binding.heartFarLeft.animate().y(250f).duration = animationDuration

        binding.heartLeft.animate().scaleX(1.2f).duration = animationDuration
        binding.heartLeft.animate().scaleY(1.2f).duration = animationDuration
        binding.heartLeft.animate().x(310f).duration = animationDuration
        binding.heartLeft.animate().y(100f).duration = animationDuration

        binding.heartMiddle.animate().scaleX(0.5f).duration = animationDuration
        binding.heartMiddle.animate().scaleY(0.5f).duration = animationDuration
        binding.heartMiddle.animate().x(390f).duration = animationDuration
        binding.heartMiddle.animate().y(310f).duration = animationDuration

        binding.heartRight.animate().scaleX(0.8f).duration = animationDuration
        binding.heartRight.animate().scaleY(0.8f).duration = animationDuration
        binding.heartRight.animate().x(675f).duration = animationDuration
        binding.heartRight.animate().y(260f).duration = animationDuration

        binding.heartFarRight.animate().scaleX(0.6f).duration = animationDuration
        binding.heartFarRight.animate().scaleY(0.6f).duration = animationDuration
        binding.heartFarRight.animate().x(775f).duration = animationDuration
        binding.heartFarRight.animate().y(380f).withEndAction{
            binding.screenLayout.setOnClickListener {onAffectionScreenClick()}
        }.duration = animationDuration
    }

    private fun onAffectionScreenClick() {
        binding.screenLayout.setOnClickListener(null)
        binding.affectionText.animate().alpha(0f).duration = animationDuration
        binding.bjingusHappyText.animate().translationYBy(-60f).duration = animationDuration
        binding.foodStatLayout.alpha = 0f
        binding.foodStatLayout.visibility = View.VISIBLE
        binding.foodStatLayout.animate().alpha(1f).duration = animationDuration
        binding.foodStatLayout.animate().translationYBy(-60f).withEndAction{
            changeFoodStats()
        }.duration = animationDuration
    }

    private fun changeFoodStats() {
        binding.vegetableValueText.text = "20"
        binding.vegetableValueText.scaleX = 1.5f
        binding.vegetableValueText.scaleY = 1.5f

        binding.fruitValueText.text = "20"
        binding.fruitValueText.scaleX = 1.5f
        binding.fruitValueText.scaleY = 1.5f

        binding.grainValueText.text = "20"
        binding.grainValueText.scaleX = 1.5f
        binding.grainValueText.scaleY = 1.5f
        binding.grainValueText.animate().scaleX(1.5f).withEndAction{
            animateFoodStats()
        }.duration = animationDuration * (1.5).toLong()
    }

    private fun animateFoodStats() {
        binding.vegetableValueText.animate().scaleX(1f).duration = animationDuration
        binding.vegetableValueText.animate().scaleY(1f).duration = animationDuration

        binding.fruitValueText.animate().scaleX(1f).duration = animationDuration
        binding.fruitValueText.animate().scaleY(1f).duration = animationDuration

        binding.grainValueText.animate().scaleX(1f).duration = animationDuration
        binding.grainValueText.animate().scaleY(1f).withEndAction {
            binding.screenLayout.setOnClickListener { returnToHomeScreen() }
        }.duration = animationDuration
    }

    private fun returnToHomeScreen() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {}

    @SuppressLint("ClickableViewAccessibility")
    private fun setSnackDraggable() {
        var bjingusPositionArr = IntArray(2)
        bjingus.getLocationOnScreen(bjingusPositionArr)

        var snackLocationArr = IntArray(2)
        snack.getLocationOnScreen(snackLocationArr)

        initialX = snackLocationArr[0].toFloat()
        initialY = snackLocationArr[1].toFloat()-snack.height/4

        snack.setOnTouchListener { view, event ->
            var newX = 0f
            var newY = 0f

             when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    touchX = event.x
                    touchY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    newX = event.x
                    newY = event.y

                    snack.x += newX - touchX
                    snack.y += newY - touchY
                }
                MotionEvent.ACTION_CANCEL -> {
                    snack.x = initialX
                    snack.y = initialY
                }
                MotionEvent.ACTION_UP -> {
                    if (snack.x > bjingusPositionArr[0]
                        && snack.x < bjingusPositionArr[0] + bjingus.width/2
                        && snack.y > bjingusPositionArr[1]
                        && snack.y < bjingusPositionArr[1] + bjingus.height/2) {
                        onSnackEat()
                    } else {
                        snack.x = initialX
                        snack.y = initialY
                    }
                }
            }
            true
        }
    }
}