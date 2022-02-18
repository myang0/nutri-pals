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
import android.R.string.no
import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.seggsmen.finalapp.databinding.ActivityNewMealAddMealBinding
import com.seggsmen.finalapp.databinding.ActivityNewMealBinding

class NewMealAddMealActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewMealAddMealBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityNewMealAddMealBinding.inflate (layoutInflater);
        setContentView(binding.root)
        binding.nextButton.setOnClickListener {navigateToNextScreen()}

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {onBackPressed()}

    }

    private fun navigateToNextScreen() {
        val intent: Intent = Intent(this, NewMealAddPhotoActivity::class.java)
        startActivity(intent)
    }
}