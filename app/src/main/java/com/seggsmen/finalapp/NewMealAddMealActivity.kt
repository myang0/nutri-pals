package com.seggsmen.finalapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import com.seggsmen.finalapp.databinding.ActivityNewMealAddMealBinding
import com.seggsmen.finalapp.logic.Const
import com.seggsmen.finalapp.logic.NewMeal

class NewMealAddMealActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewMealAddMealBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityNewMealAddMealBinding.inflate (layoutInflater);
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupEditTextListeners()
        binding.smallPortionCard.setOnClickListener {onPortionClick(Const.PORTION_SMALL)}
        binding.mediumPortionCard.setOnClickListener {onPortionClick(Const.PORTION_MEDIUM)}
        binding.largePortionCard.setOnClickListener {onPortionClick(Const.PORTION_LARGE)}
        binding.skullFeelingButton.setOnClickListener {onFeelingClick(Const.MEAL_SKULL)}
        binding.confusedFeelingButton.setOnClickListener {onFeelingClick(Const.MEAL_CONFUSED)}
        binding.neutralFeelingButton.setOnClickListener {onFeelingClick(Const.MEAL_NEUTRAL)}
        binding.happyFeelingButton.setOnClickListener {onFeelingClick(Const.MEAL_HAPPY)}
        binding.smilingFeelingButton.setOnClickListener {onFeelingClick(Const.MEAL_SMILING)}
        binding.toolbar.setNavigationOnClickListener {onBackPressed()}
        binding.indicator.createIndicators(3, 0)
    }

    private fun setupEditTextListeners() {
        binding.mealNameInput.setOnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.mealNameInput.clearFocus()
                return@setOnEditorActionListener true
            }
            false
        }

        binding.mealNameInput.addTextChangedListener {
            updateNextButtonState()
        }
    }

    private fun updateNextButtonState() {
        if (binding.mealNameInput.text.isNotEmpty()
            and (getPortionSize() != Const.STRING_NO_VALUE)
            and (getFeeling() != Const.STRING_NO_VALUE)) {

            binding.nextButton.alpha = 1f
            binding.nextButton.isClickable = true
            binding.nextButton.setOnClickListener {onNextButtonClick()}
        } else {
            binding.nextButton.alpha = 0.5f
            binding.nextButton.isClickable = false
            binding.nextButton.setOnClickListener {null}
        }
    }

    private fun onFeelingClick(feeling: String) {
        binding.skullFeelingButton.alpha = 0.5f
        binding.confusedFeelingButton.alpha = 0.5f
        binding.neutralFeelingButton.alpha = 0.5f
        binding.happyFeelingButton.alpha = 0.5f
        binding.smilingFeelingButton.alpha = 0.5f
        when (feeling) {
            Const.MEAL_SKULL -> binding.skullFeelingButton.alpha = 1f
            Const.MEAL_CONFUSED -> binding.confusedFeelingButton.alpha = 1f
            Const.MEAL_NEUTRAL -> binding.neutralFeelingButton.alpha = 1f
            Const.MEAL_HAPPY -> binding.happyFeelingButton.alpha = 1f
            Const.MEAL_SMILING -> binding.smilingFeelingButton.alpha = 1f
        }
        updateNextButtonState()
    }

    private fun onPortionClick(size: String) {
        binding.smallPortionCard.alpha = 0.5f
        binding.mediumPortionCard.alpha = 0.5f
        binding.largePortionCard.alpha = 0.5f
        when (size) {
            Const.PORTION_SMALL -> binding.smallPortionCard.alpha = 1f
            Const.PORTION_MEDIUM -> binding.mediumPortionCard.alpha = 1f
            Const.PORTION_LARGE -> binding.largePortionCard.alpha = 1f
        }
        updateNextButtonState()
    }

    private fun onNextButtonClick() {
        val newMeal = NewMeal()
        newMeal.name = binding.mealNameInput.text.toString()
        newMeal.portionSize = getPortionSize()
        newMeal.feeling = getFeeling()
        newMeal.isSaved = binding.savedMealCheckbox.isChecked

        val intent = Intent(this, NewMealAddPhotoActivity::class.java)
        intent.putExtra(Const.EXTRA_CODE_NEW_MEAL, newMeal)
        startActivity(intent)
    }

    private fun getPortionSize(): String {
        when {
            binding.smallPortionCard.alpha == 1f -> return Const.PORTION_SMALL
            binding.mediumPortionCard.alpha == 1f -> return Const.PORTION_MEDIUM
            binding.largePortionCard.alpha == 1f -> return Const.PORTION_LARGE
        }
        return Const.STRING_NO_VALUE
    }

    private fun getFeeling(): String {
        when {
            binding.skullFeelingButton.alpha == 1f -> return Const.MEAL_SKULL
            binding.confusedFeelingButton.alpha == 1f -> return Const.MEAL_CONFUSED
            binding.neutralFeelingButton.alpha == 1f -> return Const.MEAL_NEUTRAL
            binding.happyFeelingButton.alpha == 1f -> return Const.MEAL_HAPPY
            binding.smilingFeelingButton.alpha == 1f -> return Const.MEAL_SMILING
        }
        return Const.STRING_NO_VALUE
    }
}