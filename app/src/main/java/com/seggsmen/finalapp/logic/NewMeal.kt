package com.seggsmen.finalapp.logic

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewMeal(

    var name: String = Const.STRING_NO_VALUE,         // Add Meal
    var imageName: String = Const.STRING_NO_VALUE,    // Photo Taken
    var portionSize: String = Const.STRING_NO_VALUE,  // Add Meal
    var feeling: String = Const.STRING_NO_VALUE,      // Add Meal
    var isAddToSavedMeal: Boolean = false,              // Add Meal
    var vegetableServings: Int = -1,                    // Serving
    var fruitServings: Int = -1,                        // Serving
    var grainServings: Int = -1,                        // Serving
    var fishServings: Int = -1,                         // Serving
    var poultryServings: Int = -1,                      // Serving
    var redMeatServings: Int = -1,                      // Serving
    var oilServings: Int = -1,                          // Serving
    var dairyServings: Int = -1,                        // Serving
    var timesEaten: Int = -1,                           // Serving
) : Parcelable