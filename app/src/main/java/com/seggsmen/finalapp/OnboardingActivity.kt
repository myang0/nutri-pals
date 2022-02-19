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
import com.seggsmen.finalapp.databinding.ActivityOnboardingBinding
import com.seggsmen.finalapp.fragments.FifthOnboardingPageFragment
import com.seggsmen.finalapp.fragments.FourthOnboardingPageFragment
import com.seggsmen.finalapp.fragments.PetNameListener
import com.seggsmen.finalapp.fragments.ThirdOnboardingPageFragment

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

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar?.hide()

        viewPager = binding.viewPager
        viewPager.isUserInputEnabled = false
        viewPager.adapter = OnboardingAdapter(onboardingFragments, this)
    }

    override fun onNextButtonPressed() {
        viewPager.setCurrentItem(viewPager.currentItem + 1)
    }

    override fun onNameConfirmed(petName: String) {
        viewPager.setCurrentItem(viewPager.currentItem + 1)

        petNameCaller.setPetName(petName)
    }

    override fun onFinish() {
        val intent: Intent = Intent(this, MainActivity::class.java)
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
