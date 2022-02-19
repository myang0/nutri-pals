package com.seggsmen.finalapp.fragments

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.TextView
import com.seggsmen.finalapp.OnboardingListener
import com.seggsmen.finalapp.R

class ThirdOnboardingPageFragment : Fragment() {
    private lateinit var title: TextView
    private lateinit var subtitle: TextView
    private lateinit var nextButton: Button

    private lateinit var nextButtonCaller: OnboardingListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.onboarding_page_3, container, false)

        title = fragmentView.findViewById<TextView>(R.id.pageThreeTitle)
        subtitle = fragmentView.findViewById<TextView>(R.id.pageThreeSubtitle)

        nextButton = fragmentView.findViewById<Button>(R.id.thirdNextButton)
        nextButton.setOnClickListener {
            nextButtonCaller.onNextButtonPressed()
        }

        return fragmentView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnboardingListener) {
            nextButtonCaller = context
        }
    }

    override fun onResume() {
        title.visibility = View.VISIBLE
        subtitle.visibility = View.VISIBLE
        nextButton.visibility = View.VISIBLE

        fadeInElements()

        super.onResume()
    }

    private fun fadeInElements() {
        var titleAnim: AlphaAnimation = AlphaAnimation(0.0f, 1.0f)
        titleAnim.duration = 750
        titleAnim.startOffset = 1000

        var subtitleAnim: AlphaAnimation = AlphaAnimation(0.0f, 1.0f)
        subtitleAnim.duration = 750
        subtitleAnim.startOffset = 1750

        var nextButtonAnim: AlphaAnimation = AlphaAnimation(0.0f, 1.0f)
        nextButtonAnim.duration = 750
        nextButtonAnim.startOffset = 2500

        title.startAnimation(titleAnim)
        subtitle.startAnimation(subtitleAnim)
        nextButton.startAnimation(nextButtonAnim)
    }

    companion object {
        fun newInstance() : ThirdOnboardingPageFragment {
            return ThirdOnboardingPageFragment()
        }
    }
}