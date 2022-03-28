package com.seggsmen.finalapp.logic

object Const {
    // Maximum sizes for images
    const val MAX_BITMAP_SIZE = 1024 * 1024 * 10
    const val MAX_IMAGE_STRING_SIZE = 25000

    // Feeling buttons for new meal
    const val MEAL_SKULL = "SKULL"
    const val MEAL_CONFUSED = "CONFUSED"
    const val MEAL_NEUTRAL = "NEUTRAL"
    const val MEAL_HAPPY = "HAPPY"
    const val MEAL_SMILING = "SMILING"

    // Portion sizes for new meal
    const val PORTION_SMALL = "SMALL"
    const val PORTION_MEDIUM = "MEDIUM"
    const val PORTION_LARGE = "LARGE"

    // Intent codes for new meal
    const val EXTRA_CODE_NEW_MEAL = "NEW MEAL"
    const val EXTRA_CODE_IMAGE_TAKEN = "IMAGE TAKEN"
    const val STRING_NO_VALUE = "NO VALUE SELECTED"

    // Shared Preferences
    const val SHARED_PREFS_NAME = "IAT359Final"
    const val USER_KEY = "USER KEY"

    // Log debug key
    const val LOG = "LOGKEY"

    // Firebase Database Keys
    const val DB_USERS = "Users"
    const val DB_PETNAMES = "PetName"
    const val DB_PAST_MEALS = "PastMeals"
    const val DB_PET_STATS = "PetStats"
    const val DB_NAME = "name"
    const val DB_SAVED = "saved"
    const val DB_IMAGE_STRING = "imageString"
    const val DB_VEGETABLE = "vegetableServings"
    const val DB_FRUIT = "fruitServings"
    const val DB_GRAIN = "grainServings"
    const val DB_FISH = "fishServings"
    const val DB_POULTRY = "poultryServings"
    const val DB_REDMEAT = "redMeatServings"
    const val DB_OIL = "oilServings"
    const val DB_DAIRY = "dairyServings"
    const val DB_TIMES_EATEN = "timesEaten"
    const val DB_LAST_EATEN = "timeLastEaten"
    const val DB_LAST_DECAY = "timeLastDecay"
    const val DB_FEELING = "feeling"
    const val DB_PORTION = "portionSize"
    const val DB_EVO_STATS = "EvoStats"
    const val DB_EVO_TYPE = "evoType"
    const val DB_LAST_EVO = "timeLastEvo"
    const val DB_TOTAL_SERVINGS = "totalServings"
    const val DB_STARVED_SERVINGS = "starvedServings"

    // Feelings for pet
    const val FEELING_HAPPY = "Happy"
    const val FEELING_NEUTRAL = "Neutral"
    const val FEELING_SAD = "Sad"

    // Evolution Types
    const val EVO_INITIAL = "preEvolution"
    const val EVO_VEGETABLES_SMALL = "vegetableSmall"
    const val EVO_VEGETABLES_MEDIUM = "vegetableMedium"
    const val EVO_VEGETABLES_LARGE = "vegetableLarge"
    const val EVO_FRUITS_SMALL = "fruitsSmall"
    const val EVO_FRUITS_MEDIUM = "fruitsMedium"
    const val EVO_FRUITS_LARGE = "fruitsLarge"
    const val EVO_GRAINS_SMALL = "grainsSmall"
    const val EVO_GRAINS_MEDIUM = "grainsMedium"
    const val EVO_GRAINS_LARGE = "grainsLarge"
    const val EVO_FISH_SMALL = "fishSmall"
    const val EVO_FISH_MEDIUM = "fishMedium"
    const val EVO_FISH_LARGE = "fishLarge"
    const val EVO_POULTRY_SMALL = "poultrySmall"
    const val EVO_POULTRY_MEDIUM = "poultryMedium"
    const val EVO_POULTRY_LARGE = "poultryLarge"
    const val EVO_REDMEAT_SMALL = "redMeatSmall"
    const val EVO_REDMEAT_MEDIUM = "redMeatMedium"
    const val EVO_REDMEAT_LARGE = "redMeatLarge"
    const val EVO_OIL_SMALL = "oilSmall"
    const val EVO_OIL_MEDIUM = "oilMedium"
    const val EVO_OIL_LARGE = "oilLarge"
    const val EVO_DAIRY_SMALL = "dairySmall"
    const val EVO_DAIRY_MEDIUM = "dairyMedium"
    const val EVO_DAIRY_LARGE = "dairyLarge"

}