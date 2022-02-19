package com.seggsmen.finalapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.seggsmen.finalapp.OnboardingListener
import com.seggsmen.finalapp.R

class ThirdOnboardingPageFragment : Fragment() {
    private lateinit var nextButtonCaller: OnboardingListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.onboarding_page_3, container, false)

        val nextButton = fragmentView.findViewById<Button>(R.id.thirdNextButton)
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

    companion object {
        fun newInstance() : ThirdOnboardingPageFragment {
            return ThirdOnboardingPageFragment()
        }
    }
}