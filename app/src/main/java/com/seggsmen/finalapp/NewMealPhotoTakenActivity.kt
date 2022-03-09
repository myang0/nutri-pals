package com.seggsmen.finalapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.seggsmen.finalapp.databinding.ActivityNewMealPhotoTakenBinding
import com.seggsmen.finalapp.logic.Const
import com.seggsmen.finalapp.logic.NewMeal

class NewMealPhotoTakenActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewMealPhotoTakenBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityNewMealPhotoTakenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {onBackPressed()}
        binding.nextButton.setOnClickListener {loadNextActivity()}
        binding.retakeButton.setOnClickListener {retakePhoto()}

        loadImageFromPrevActivity()
    }

    private fun loadImageFromPrevActivity() {
        val loadedIntent = intent.getParcelableExtra<Intent?>(Const.EXTRA_CODE_IMAGE_TAKEN)

        // It is has image uri aka it is from gallery
        if (loadedIntent?.data != null) {
            binding.photoTakenPreview.setImageURI(loadedIntent.data!!)

        // No image uri aka from camera
        } else {
            binding.photoTakenPreview.setImageBitmap(loadedIntent?.extras?.get("data") as Bitmap)
        }

    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val imageData: Intent? = result.data
            binding.photoTakenPreview.setImageBitmap(imageData?.extras?.get("data") as Bitmap)
        }
    }

    private fun retakePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(takePictureIntent)
    }

    private fun loadNextActivity() {
        val newMeal = intent.getParcelableExtra<NewMeal>(Const.EXTRA_CODE_NEW_MEAL)
        //To-do: Upload image from binding.photoTakenPreview to Firebase
        //       Save image key to NewMeal object

        val newIntent = Intent(this, NewMealServingActivity::class.java)
        intent.putExtra(Const.EXTRA_CODE_NEW_MEAL, newMeal)
        startActivity(newIntent)
    }

    override fun onBackPressed() {

    }
}