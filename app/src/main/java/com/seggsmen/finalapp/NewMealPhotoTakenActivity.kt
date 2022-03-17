package com.seggsmen.finalapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.seggsmen.finalapp.databinding.ActivityNewMealPhotoTakenBinding
import com.seggsmen.finalapp.logic.Const
import com.seggsmen.finalapp.logic.NewMeal
import com.seggsmen.finalapp.util.BitmapConverter
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class NewMealPhotoTakenActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewMealPhotoTakenBinding

    lateinit var imageBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityNewMealPhotoTakenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadImageFromPrevActivity()

        binding.toolbar.setNavigationOnClickListener {onBackPressed()}
        binding.nextButton.setOnClickListener {loadNextActivity()}
        binding.retakeButton.setOnClickListener {retakePhoto()}
    }

    private fun loadImageFromPrevActivity() {
        val loadedIntent = intent.getParcelableExtra<Intent?>(Const.EXTRA_CODE_IMAGE_TAKEN)
        if (loadedIntent!!.data != null) {
            imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, loadedIntent.data!!)
            binding.photoTakenPreview.setImageBitmap(imageBitmap)
        } else {
            imageBitmap = loadedIntent.extras?.get("data") as Bitmap
            binding.photoTakenPreview.setImageBitmap(imageBitmap)
        }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            imageBitmap = result.data?.extras?.get("data") as Bitmap
            binding.photoTakenPreview.setImageBitmap(imageBitmap)
        }
    }

    private fun retakePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(takePictureIntent)
    }

    private fun loadNextActivity() {
        val newIntent = Intent(this, NewMealServingActivity::class.java)

        val newMeal = intent.getParcelableExtra<NewMeal>(Const.EXTRA_CODE_NEW_MEAL)

        val bitmapAsString = BitmapConverter.convertBitmapToString(imageBitmap)
        newMeal!!.imageString = bitmapAsString

        newIntent.putExtra(Const.EXTRA_CODE_NEW_MEAL, newMeal)

        startActivity(newIntent)
    }

    override fun onBackPressed() {

    }
}