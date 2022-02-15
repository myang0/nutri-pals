package com.seggsmen.finalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.seggsmen.finalapp.databinding.ActivityListPastMealsBinding

class ListPastMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivityListPastMealsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_past_meals)

        var actionBar : ActionBar? = this.supportActionBar

        actionBar?.setTitle("Meal Library")
        actionBar?.setSubtitle("Saved Meals")
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}