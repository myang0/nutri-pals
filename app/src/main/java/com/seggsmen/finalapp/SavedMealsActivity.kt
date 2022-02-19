package com.seggsmen.finalapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seggsmen.finalapp.databinding.ActivitySavedMealsBinding
import com.seggsmen.finalapp.logic.SavedMealListAdapter
import com.seggsmen.finalapp.logic.sampleFoodsList

class SavedMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySavedMealsBinding
    lateinit var list: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivitySavedMealsBinding.inflate (layoutInflater);
        setContentView(binding.root)

        setSupportActionBar(binding.savedMealsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.savedMealsToolbar.setNavigationOnClickListener {onBackPressed()}
        binding.savedMealsSelectButton.setOnClickListener {onSelect()}
        binding.savedMealsDetailsButton.setOnClickListener {onDetail()}
        list = binding.savedMealsList
        list.adapter = SavedMealListAdapter(sampleFoodsList(resources), this)
        list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun onSelect() {

    }

    private fun onDetail() {

    }


}