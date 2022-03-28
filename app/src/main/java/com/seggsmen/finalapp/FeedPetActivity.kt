package com.seggsmen.finalapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.seggsmen.finalapp.databinding.ActivityFeedPetBinding
import com.seggsmen.finalapp.logic.*
import java.time.Instant
import java.time.ZoneId
import java.util.*

class FeedPetActivity : AppCompatActivity() {
    lateinit var binding: ActivityFeedPetBinding
    lateinit var snack: ImageView
    lateinit var bjingus: ImageView
    val animationDuration = (500).toLong()
    var initialX = 0f
    var initialY = 0f
    var touchX = 0f
    var touchY = 0f

    var petStats = PetStats()
    lateinit var userKey: String
    lateinit var petStatsRef: DatabaseReference

    lateinit var newMeal: NewMeal
    lateinit var servingsMap: MutableMap<String, Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newMeal = intent.getParcelableExtra<NewMeal>(Const.EXTRA_CODE_NEW_MEAL)!!

        loadPetStats()

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

    private fun loadPetStats() {
        val sharedPrefs = this.getSharedPreferences(Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        // getString args: string key, default value if key is incorrect
        userKey = sharedPrefs.getString(Const.USER_KEY, Const.STRING_NO_VALUE)!!

        petStatsRef = Firebase.database.getReference(Const.DB_USERS).child(userKey).child(Const.DB_PET_STATS)

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
                petStats.petName = dbPetStats [Const.DB_PETNAME] as String

                binding.clickToFeedText.text = "Drag to feed ${petStats.petName}!"
                binding.bjingusHappyText.text = "${petStats.petName} is happy!"
            }

            //todo we have to have this because yes
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun onSnackEat() {
        snack.visibility = View.GONE
        binding.clickToFeedText.visibility = View.INVISIBLE
        bjingus.setImageResource(R.drawable.bjingus_smiling_anim)

        binding.vegetableValueText.text = "${petStats.vegetableServings - newMeal!!.vegetableServings}"
        binding.fruitValueText.text = "${petStats.fruitServings - newMeal!!.fruitServings}"
        binding.grainValueText.text = "${petStats.grainServings - newMeal!!.grainServings}"
        binding.fishValueText.text = "${petStats.fishServings - newMeal!!.fishServings}"
        binding.poultryValueText.text = "${petStats.poultryServings - newMeal!!.poultryServings}"
        binding.redMeatValueText.text = "${petStats.redMeatServings - newMeal!!.redMeatServings}"
        binding.oilValueText.text = "${petStats.oilServings - newMeal!!.oilServings}"
        binding.dairyValueText.text = "${petStats.dairyServings - newMeal!!.dairyServings}"

        binding.bjingusHappyText.visibility = View.VISIBLE
        binding.bjingusHappyText.alpha = 0f
        binding.bjingusHappyText.animate().alpha(1f).duration = animationDuration

//        binding.affectionText.visibility = View.VISIBLE
//        binding.affectionText.alpha = 0f
//        binding.affectionText.animate().alpha(1f).duration = animationDuration

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
        if(newMeal.vegetableServings > 0){
            binding.vegetableValueText.text = "${petStats.vegetableServings}"
            binding.vegetableValueText.scaleX = 1.5f
            binding.vegetableValueText.scaleY = 1.5f
        }

        if(newMeal.fruitServings > 0){
        binding.fruitValueText.text = "${petStats.fruitServings}"
        binding.fruitValueText.scaleX = 1.5f
        binding.fruitValueText.scaleY = 1.5f
        }

        if(newMeal.grainServings > 0) {
            binding.grainValueText.text = "${petStats.grainServings}"
            binding.grainValueText.scaleX = 1.5f
            binding.grainValueText.scaleY = 1.5f
        }

        if(newMeal.fishServings > 0) {
            binding.fishValueText.text = "${petStats.fishServings}"
            binding.fishValueText.scaleX = 1.5f
            binding.fishValueText.scaleY = 1.5f
        }

        if(newMeal.poultryServings > 0) {
            binding.poultryValueText.text = "${petStats.poultryServings}"
            binding.poultryValueText.scaleX = 1.5f
            binding.poultryValueText.scaleY = 1.5f
            Log.d(Const.LOG, "${newMeal.poultryServings}")
        }

        if(newMeal.oilServings > 0) {
            binding.oilValueText.text = "${petStats.oilServings}"
            binding.oilValueText.scaleX = 1.5f
            binding.oilValueText.scaleY = 1.5f
        }

        if(newMeal.redMeatServings > 0) {
            binding.redMeatValueText.text = "${petStats.redMeatServings}"
            binding.redMeatValueText.scaleX = 1.5f
            binding.redMeatValueText.scaleY = 1.5f
        }

        if(newMeal.dairyServings > 0) {
            binding.dairyValueText.text = "${petStats.dairyServings}"
            binding.dairyValueText.scaleX = 1.5f
            binding.dairyValueText.scaleY = 1.5f
        }

        binding.invisibleValueText.animate().scaleX(1.5f).withEndAction{
            animateFoodStats()
        }.duration = animationDuration * (1.5).toLong()
    }

    private fun animateFoodStats() {
        if(newMeal.vegetableServings > 0){
            binding.vegetableValueText.animate().scaleX(1f).duration = animationDuration
            binding.vegetableValueText.animate().scaleY(1f).duration = animationDuration
        }
        if(newMeal.fruitServings > 0){
            binding.fruitValueText.animate().scaleX(1f).duration = animationDuration
            binding.fruitValueText.animate().scaleY(1f).duration = animationDuration
        }
        if(newMeal.grainServings > 0){
            binding.grainValueText.animate().scaleX(1f).duration = animationDuration
            binding.grainValueText.animate().scaleY(1f).duration = animationDuration
        }
        if(newMeal.fishServings > 0){
            binding.fishValueText.animate().scaleX(1f).duration = animationDuration
            binding.fishValueText.animate().scaleY(1f).duration = animationDuration
        }
        if(newMeal.poultryServings > 0){
            binding.poultryValueText.animate().scaleX(1f).duration = animationDuration
            binding.poultryValueText.animate().scaleY(1f).duration = animationDuration
        }
        if(newMeal.oilServings > 0){
            binding.oilValueText.animate().scaleX(1f).duration = animationDuration
            binding.oilValueText.animate().scaleY(1f).duration = animationDuration
        }
        if(newMeal.redMeatServings > 0){
            binding.redMeatValueText.animate().scaleX(1f).duration = animationDuration
            binding.redMeatValueText.animate().scaleY(1f).duration = animationDuration
        }
        if(newMeal.dairyServings > 0){
            binding.dairyValueText.animate().scaleX(1f).duration = animationDuration
            binding.dairyValueText.animate().scaleY(1f).duration = animationDuration
        }

        binding.invisibleValueText.animate().scaleX(1f).duration = animationDuration
        binding.invisibleValueText.animate().scaleY(1f).withEndAction {
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