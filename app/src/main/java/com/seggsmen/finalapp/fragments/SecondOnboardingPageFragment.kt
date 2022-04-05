package com.seggsmen.finalapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout

class SecondOnboardingPageFragment : Fragment() {
    private lateinit var trackFoodSection: ConstraintLayout
    private lateinit var foodLogSection: ConstraintLayout
//    private lateinit var petLogSection: ConstraintLayout

    private lateinit var nextButton: Button

    private lateinit var nextButtonCaller: OnboardingListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.onboarding_page_2, container, false)

        trackFoodSection = fragmentView.findViewById<ConstraintLayout>(R.id.trackFoodSection)
        foodLogSection = fragmentView.findViewById<ConstraintLayout>(R.id.foodLogSection)

        nextButton = fragmentView.findViewById<Button>(R.id.secondNextButton)
        nextButton.setOnClickListener {
            nextButtonCaller.onNextButtonPressed()
        }

        fadeInElements()

        return fragmentView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnboardingListener) {
            nextButtonCaller = context
        }
    }

    private fun fadeInElements() {
        var trackFoodSectionAnim: AlphaAnimation = AlphaAnimation(0.0f, 1.0f)
        trackFoodSectionAnim.duration = 750

        var foodLogSectionAnim: AlphaAnimation = AlphaAnimation(0.0f, 1.0f)
        foodLogSectionAnim.duration = 750
        foodLogSectionAnim.startOffset = 750

        var nextButtonAnim: AlphaAnimation = AlphaAnimation(0.0f, 1.0f)
        nextButtonAnim.duration = 750
        nextButtonAnim.startOffset = 1500

        trackFoodSection.startAnimation(trackFoodSectionAnim)
        foodLogSection.startAnimation(foodLogSectionAnim)
        nextButton.startAnimation(nextButtonAnim)
    }

    companion object {
        fun newInstance() : SecondOnboardingPageFragment {
            return SecondOnboardingPageFragment()
        }
    }
}