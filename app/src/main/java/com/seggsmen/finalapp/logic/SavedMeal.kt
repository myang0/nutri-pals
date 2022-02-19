package com.seggsmen.finalapp.logic

data class SavedMeal(

    val name: String = "<no name>",
    val image: Int = -1,
    val vegetableServings: Int = -1,
    val fruitServings: Int = -1,
    val grainServings: Int = -1,
    val fishServings: Int = -1,
    val poultryServings: Int = -1,
    val redMeatServings: Int = -1,
    val oilServings: Int = -1,
    val dairyServings: Int = -1,
    val timesEaten: Int = -1,
)