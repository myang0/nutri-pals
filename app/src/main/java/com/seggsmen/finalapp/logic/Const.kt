package com.seggsmen.finalapp.logic

object Const {
    // Maximum sizes for images
    const val MAX_BITMAP_SIZE = 1024 * 1024 * 10
    const val MAX_IMAGE_STRING_SIZE = 25000

    // Feeling buttons for new meal
    const val FEELING_SKULL = "SKULL"
    const val FEELING_CONFUSED = "CONFUSED"
    const val FEELING_NEUTRAL = "NEUTRAL"
    const val FEELING_HAPPY = "HAPPY"
    const val FEELING_SMILING = "SMILING"

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
}