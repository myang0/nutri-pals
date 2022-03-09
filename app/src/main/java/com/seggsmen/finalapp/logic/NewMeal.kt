package com.seggsmen.finalapp.logic

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewMeal(

    var name: String = "<no name>",
    var image: Int = -1,
    var portionSize: String = Const.NO_VALUE_SELECTED,
    var feeling: String = Const.NO_VALUE_SELECTED,
    var isAddToSavedMeal: Boolean = false,
    var vegetableServings: Int = -1,
    var fruitServings: Int = -1,
    var grainServings: Int = -1,
    var fishServings: Int = -1,
    var poultryServings: Int = -1,
    var redMeatServings: Int = -1,
    var oilServings: Int = -1,
    var dairyServings: Int = -1,
    var timesEaten: Int = -1,
) : Parcelable