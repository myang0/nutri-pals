package com.seggsmen.finalapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.seggsmen.finalapp.databinding.ActivityNewMealBinding

private const val NUM_PAGES = 4

class TrackNewMealActivity  : AppCompatActivity() {
    public lateinit var binding: ActivityNewMealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        binding.pager.adapter = pagerAdapter
        binding.pager.isUserInputEnabled = false
        binding.pager.setPageTransformer(PageViewFadeTransformer())
        supportActionBar?.hide()
    }

    override fun onBackPressed() {
        if (binding.pager.currentItem == 0) {
            super.onBackPressed()
        } else {
            binding.pager.currentItem = binding.pager.currentItem - 1
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return NewMealAddMealFragment()
                1 -> return NewMealAddPhotoFragment()
                2 -> return NewMealPhotoTakenFragment()
                3 -> return NewMealServingFragment()
            }
            return NewMealAddMealFragment()
        }
    }
}