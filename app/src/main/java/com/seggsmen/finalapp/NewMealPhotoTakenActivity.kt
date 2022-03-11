package com.seggsmen.finalapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.seggsmen.finalapp.databinding.ActivityNewMealPhotoTakenBinding
import com.seggsmen.finalapp.logic.Const
import com.seggsmen.finalapp.logic.NewMeal
import java.text.SimpleDateFormat
import java.util.*

class NewMealPhotoTakenActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewMealPhotoTakenBinding
    lateinit var tempImageUri: Uri

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
            binding.photoTakenPreview.setImageURI(loadedIntent.data!!)
            tempImageUri = loadedIntent.data!!

            // No image uri aka from camera
        } else {
            //TODO: Get URI from Camera because rn it just crash oof.
            binding.photoTakenPreview.setImageBitmap(loadedIntent.extras?.get("data") as Bitmap)
            tempImageUri = loadedIntent.data!!
        }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            //TODO: Get URI from Camera because rn it just crash oof.
            val imageData: Intent? = result.data
            binding.photoTakenPreview.setImageBitmap(imageData?.extras?.get("data") as Bitmap)
            tempImageUri = imageData.data!!
        }
    }

    private fun retakePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(takePictureIntent)
    }

    private fun loadNextActivity() {
        val newMeal = intent.getParcelableExtra<NewMeal>(Const.EXTRA_CODE_NEW_MEAL)
        //TODO: Upload image from binding.photoTakenPreview to Firebase
        //      Save image key to NewMeal object
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading your sick food pic...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        //Name new file
        val fileName = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault()).format(Date())
        val imageRef = Firebase.storage.getReference(fileName)

        imageRef.putFile(tempImageUri).addOnSuccessListener {
            Log.d(Const.LOG, "Uploaded new pic")

            if (progressDialog.isShowing) progressDialog.dismiss()

            newMeal!!.imageName = fileName

            val newIntent = Intent(this, NewMealServingActivity::class.java)
            newIntent.putExtra(Const.EXTRA_CODE_NEW_MEAL, newMeal)
            startActivity(newIntent)

        }.addOnFailureListener {
            if (progressDialog.isShowing) progressDialog.dismiss()
            Log.d(Const.LOG, "Failed to upload new pic")
        }
    }

    override fun onBackPressed() {

    }
}