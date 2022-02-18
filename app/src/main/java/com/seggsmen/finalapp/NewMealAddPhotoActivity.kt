package com.seggsmen.finalapp

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.core.app.ActivityCompat.startActivityForResult
import com.seggsmen.finalapp.databinding.ActivityNewMealAddPhotoBinding

class NewMealAddPhotoActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewMealAddPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMealAddPhotoBinding.inflate (layoutInflater);
        binding.skipButton.setOnClickListener {onSkipClick()}

        setSupportActionBar(binding.toolbar)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {onBackPressed()}
        binding.galleryButton.setOnClickListener {onGalleryClick()}
        binding.cameraButton.setOnClickListener {onCameraClick()}

    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            val intent: Intent = Intent(this, NewMealPhotoTakenActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onGalleryClick() {
        Toast.makeText(this, "Gallery!", Toast.LENGTH_SHORT).show()
    }

    private fun onCameraClick() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(takePictureIntent)
    }

    private fun onSkipClick() {
        val intent: Intent = Intent(this, NewMealServingActivity::class.java)
        startActivity(intent)
    }
}