package com.seggsmen.finalapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.seggsmen.finalapp.OnboardingListener
import com.seggsmen.finalapp.R

interface PetNameListener {
    fun setPetName(petName: String)
}

class FifthOnboardingPageFragment : Fragment(), PetNameListener {
    lateinit var title: TextView
    lateinit var subtitle: TextView

    lateinit var finishButton: Button

    lateinit var endOnboardingCaller: OnboardingListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("fifth", "here")

        val fragmentView = inflater.inflate(R.layout.onboarding_page_5, container, false)

        title = fragmentView.findViewById<TextView>(R.id.onboardingPetTitle)
        title.text = "Bjingus"

        subtitle = fragmentView.findViewById<TextView>(R.id.onboardingPetSubtitle)
        subtitle.text = "Bjingus loves their name!"

        finishButton = fragmentView.findViewById<Button>(R.id.onboardingFinishButton)
        finishButton.setOnClickListener { endOnboardingCaller.onFinish() }

        return fragmentView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnboardingListener) {
            endOnboardingCaller = context
        }
    }

    override fun setPetName(petName: String) {
        title?.text = petName
        subtitle?.text = "$petName loves their name!"
    }

    companion object {
        fun newInstance(): FifthOnboardingPageFragment {
            return FifthOnboardingPageFragment()
        }
    }
}