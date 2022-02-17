package com.seggsmen.finalapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.seggsmen.finalapp.databinding.FragmentNewMealAddPhotoBinding

class NewMealAddPhotoFragment : Fragment() {
    lateinit var binding: FragmentNewMealAddPhotoBinding
    lateinit var pager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewMealAddPhotoBinding.inflate (layoutInflater);
        binding.skipButton.setOnClickListener {navigateToNextScreen()}

        var activity = activity as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pager = activity?.findViewById(R.id.pager)!!
        binding.toolbar.setNavigationOnClickListener {pager.currentItem = pager.currentItem-1}

        return binding.root
    }

    private fun navigateToNextScreen() {
        pager.currentItem = pager.currentItem+1
    }
}