package com.seggsmen.finalapp

import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.seggsmen.finalapp.databinding.FragmentNewMealAddMealBinding
import android.R.string.no
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.seggsmen.finalapp.databinding.ActivityNewMealBinding

class NewMealAddMealFragment : Fragment() {
    lateinit var binding: FragmentNewMealAddMealBinding
    lateinit var pager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewMealAddMealBinding.inflate (layoutInflater);
        binding.toolbar?.title = "Meal Info"
        binding.toolbar?.subtitle = "Track Meal"
        binding.nextButton.setOnClickListener {navigateToNextScreen()}
        pager = activity?.findViewById(R.id.pager)!!
        return binding.root
    }

    private fun navigateToNextScreen() {
        pager.currentItem = pager.currentItem+1
    }
}