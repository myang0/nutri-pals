package com.seggsmen.finalapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.seggsmen.finalapp.databinding.ActivityOnboardingBinding
import com.seggsmen.finalapp.fragments.FifthOnboardingPageFragment
import com.seggsmen.finalapp.fragments.FourthOnboardingPageFragment
import com.seggsmen.finalapp.fragments.PetNameListener
import com.seggsmen.finalapp.fragments.ThirdOnboardingPageFragment
import com.seggsmen.finalapp.logic.Const
import com.seggsmen.finalapp.logic.EvoStats
import com.seggsmen.finalapp.logic.PetStats
import java.time.Instant
import java.time.ZoneId

interface OnboardingListener {
    fun onNextButtonPressed()
    fun onNameConfirmed(petName: String)
    fun onFinish()
}

class OnboardingActivity : AppCompatActivity(), OnboardingListener {
    lateinit var binding: ActivityOnboardingBinding
    lateinit var viewPager: ViewPager2
    var isSkipOnboarding = false

    val onboardingFragments: Array<Fragment> = arrayOf(
        FirstOnboardingPageFragment(),
        SecondOnboardingPageFragment(),
        ThirdOnboardingPageFragment(),
        FourthOnboardingPageFragment(),
        FifthOnboardingPageFragment(),
    )

    lateinit var petNameCaller: PetNameListener

    var petName: String = "Bjingus"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get necessary references
        val sharedPrefs = this.getSharedPreferences(Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        val userKey = sharedPrefs.getString(Const.USER_KEY, Const.STRING_NO_VALUE)
        val usersRef = Firebase.database.getReference(Const.DB_USERS)

        // Query the database for the user key taken from shared prefs
        usersRef.child(userKey!!).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value == null) {
                    Log.d(Const.LOG, "USER KEY [${userKey}] IS INVALID")
                    // Do not show content view
                    isSkipOnboarding = true

                    // Remove the key
                    with(sharedPrefs.edit()) {
                        remove(Const.USER_KEY)
                        apply()
                    }

                    showContentView()
                } else {
                    // Skip onboarding
                    Log.d(Const.LOG, "USER KEY [${userKey}] IS VALID")
                    val intent = Intent(this@OnboardingActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
//        skipOnboardIfUserKeyPresent()

    }

    private fun showContentView() {
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar?.hide()

        viewPager = binding.viewPager
        viewPager.isUserInputEnabled = false
        viewPager.adapter = OnboardingAdapter(onboardingFragments, this)
        binding.indicator.setViewPager(viewPager)
    }

    override fun onNextButtonPressed() {
        viewPager.setCurrentItem(viewPager.currentItem + 1)
    }

    override fun onNameConfirmed(petName: String) {
        viewPager.setCurrentItem(viewPager.currentItem + 1)

        this.petName = petName

        petNameCaller.setPetName(petName)
    }

    override fun onFinish() {
        Log.d(Const.LOG, "OnFinish")
        val intent: Intent = Intent(this, MainActivity::class.java)

        val sharedPrefs = this.getSharedPreferences(Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE) ?: return

        // Get correct directory in Realtime Database
        val databaseRef = Firebase.database
        val userDataRef = databaseRef.getReference(Const.DB_USERS)
        val userKey = userDataRef.push()

        // Save user key to shared prefs
        with(sharedPrefs.edit()) {
            putString(Const.USER_KEY, userKey.key)
            apply()
        }

        // Initialize pet stats
        val petStatRef = userKey.child(Const.DB_PET_STATS)
        val petStats = PetStats()

        petStats.feeling = Const.FEELING_NEUTRAL
        petStats.timeLastEaten = Instant.ofEpochMilli(System.currentTimeMillis())
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime().toString()
        petStats.timeLastDecay = petStats.timeLastEaten
        petStats.vegetableServings = 0
        petStats.fruitServings = 0
        petStats.grainServings = 0
        petStats.redMeatServings = 0
        petStats.poultryServings = 0
        petStats.fishServings = 0
        petStats.oilServings = 0
        petStats.dairyServings = 0

        petStats.petName = this.petName
        petStatRef.setValue(petStats)

        // Initialize evolution stats
        val evoStatsRef = userKey.child(Const.DB_EVO_STATS)
        val evoStats = EvoStats()
        evoStats.evoType = Const.EVO_INITIAL
        evoStats.timeLastEvo = petStats.timeLastEaten
        evoStats.totalServings = 0
        evoStats.starvedServings = 0
        evoStats.vegetableServings = 0
        evoStats.fruitServings = 0
        evoStats.grainServings = 0
        evoStats.redMeatServings = 0
        evoStats.poultryServings = 0
        evoStats.fishServings = 0
        evoStats.oilServings = 0
        evoStats.dairyServings = 0
        evoStatsRef.setValue(evoStats)

        startActivity(intent)
    }

    inner class OnboardingAdapter(
        private val fragments: Array<Fragment>,
        activity: AppCompatActivity,
    ) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            if (position == onboardingFragments.size - 1 && fragments[position] is PetNameListener) {
                petNameCaller = fragments[position] as PetNameListener
            }

            return fragments[position]
        }
    }
}
