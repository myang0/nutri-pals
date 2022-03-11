package com.seggsmen.finalapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
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

interface OnboardingListener {
    fun onNextButtonPressed()
    fun onNameConfirmed(petName: String)
    fun onFinish()
}

class OnboardingActivity : AppCompatActivity(), OnboardingListener {
    lateinit var binding: ActivityOnboardingBinding

    lateinit var viewPager: ViewPager2

    val onboardingFragments: Array<Fragment> = arrayOf(
        FirstOnboardingPageFragment(),
        SecondOnboardingPageFragment(),
        ThirdOnboardingPageFragment(),
        FourthOnboardingPageFragment(),
        FifthOnboardingPageFragment(),
    )

    lateinit var petNameCaller: PetNameListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        skipOnboardIfUserKeyPresent()
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar?.hide()

        viewPager = binding.viewPager
        viewPager.isUserInputEnabled = false
        viewPager.adapter = OnboardingAdapter(onboardingFragments, this)
        binding.indicator.setViewPager(viewPager)
    }

    private fun skipOnboardIfUserKeyPresent() {
        // Get necessary references
        val sharedPrefs = this.getSharedPreferences(Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        val userKey = sharedPrefs.getString(Const.USER_KEY, Const.NO_VALUE_SELECTED)
        val usersRef = Firebase.database.getReference(Const.DB_USERS)

        // Query the database for the user key taken from shared prefs
        usersRef.child(userKey!!).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value == null) {
                    Log.d(Const.LOG, "USER KEY [${userKey}] IS INVALID")

                    // Remove the key
                    with(sharedPrefs.edit()) {
                        remove(Const.USER_KEY)
                        apply()
                    }
                } else {
                    // Skip onboarding
                    Log.d(Const.LOG, "USER KEY [${userKey}] IS VALID")
                    val intent = Intent(this@OnboardingActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onNextButtonPressed() {
        viewPager.setCurrentItem(viewPager.currentItem + 1)
    }

    override fun onNameConfirmed(petName: String) {
        viewPager.setCurrentItem(viewPager.currentItem + 1)

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
        val petName = userKey.child(Const.DB_PETNAMES)

        // To-do: Set pet name here.
        petName.setValue("Bjingus")

        // Save user key to shared prefs
        with(sharedPrefs.edit()) {
            putString(Const.USER_KEY, userKey.key)
            apply()
        }

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
