package com.seggsmen.finalapp.logic

import android.content.Context
import android.content.Intent
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.seggsmen.finalapp.R
import com.seggsmen.finalapp.ViewPastMealActivity
import com.seggsmen.finalapp.databinding.CardSavedMealRowBinding

class SavedMealListAdapter(private val meals: List<SavedMeal>, private val context: Context) :

    RecyclerView.Adapter<SavedMealListAdapter.ViewHolder>() {
    var selectedPos = RecyclerView.NO_ID.toInt()

    inner class ViewHolder(binding: CardSavedMealRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val mealName: TextView = binding.textView15
        val mealImage: ImageView = binding.imageView2
        val cardView: CardView = binding.cardView
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardSavedMealRowBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.mealName.text = meals[position].name
        viewHolder.mealImage.setImageDrawable(ContextCompat.getDrawable(viewHolder.mealImage.context, meals[position].image))
//        viewHolder.cardView.isSelected = selectedPos == position


        viewHolder.itemView.setOnClickListener {
            val intent: Intent = Intent(context, ViewPastMealActivity::class.java)
            intent.putExtra("name", meals[position].name)
            intent.putExtra("image_id", meals[position].image)
            intent.putExtra("vegetable", meals[position].vegetableServings)
            intent.putExtra("fruit", meals[position].fruitServings)
            intent.putExtra("grain", meals[position].grainServings)
            intent.putExtra("fish", meals[position].fishServings)
            intent.putExtra("poultry", meals[position].poultryServings)
            intent.putExtra("red_meat", meals[position].redMeatServings)
            intent.putExtra("oil", meals[position].oilServings)
            intent.putExtra("dairy", meals[position].dairyServings)
            intent.putExtra("times_eaten", meals[position].timesEaten)
            context.startActivity(intent)
//            Toast.makeText(context, meals[position].name, Toast.LENGTH_SHORT).show()
////            notifyItemChanged(position)
//            selectedPos = position
//            viewHolder.itemView.isSelected = selectedPos == position
//            selectedPos = position
//            if (selectedPos == position) {
//                viewHolder.itemView.backgroundTintList = ContextCompat.getColorStateList(context, R.color.dark_peach)
//            } else {
//                viewHolder.itemView.backgroundTintList = ContextCompat.getColorStateList(context, R.color.peach)
//            }
        }
    }

    override fun getItemCount() = meals.size
}