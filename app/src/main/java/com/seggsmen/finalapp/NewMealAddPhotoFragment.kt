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
import com.seggsmen.finalapp.databinding.FragmentNewMealAddPhotoBinding
import androidx.core.app.ActivityCompat.startActivityForResult




class NewMealAddPhotoFragment : Fragment() {
    lateinit var binding: FragmentNewMealAddPhotoBinding
    lateinit var pager: ViewPager2

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            pager.currentItem = pager.currentItem+1
//            Log.d("shark", "Epic")
//            binding.imageSelectionLayout.visibility = View.GONE
//            binding.cameraTakenLayout.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentNewMealAddPhotoBinding.inflate (layoutInflater);
        binding.skipButton.setOnClickListener {onSkipClick()}

        var activity = activity as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pager = activity?.findViewById(R.id.pager)!!
        binding.toolbar.setNavigationOnClickListener {pager.currentItem = pager.currentItem-1}
        binding.galleryButton.setOnClickListener {onGalleryClick()}
        binding.cameraButton.setOnClickListener {onCameraClick()}
//        binding.retakeButton.setOnClickListener {onRetakeClick()}
//        binding.nextButton.setOnClickListener {onNextClick()}

        return binding.root
    }

//    private fun onRetakeClick() {
//        Toast.makeText(activity, "Retake!", Toast.LENGTH_SHORT).show()
//    }
//
//    private fun onNextClick() {
//        Toast.makeText(activity, "Next!", Toast.LENGTH_SHORT).show()
//    }

    private fun onGalleryClick() {
        Toast.makeText(activity, "Gallery!", Toast.LENGTH_SHORT).show()
    }

    private fun onCameraClick() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(takePictureIntent)
    }

    private fun onSkipClick() {
        pager.setCurrentItem(pager.currentItem+2, true)
//        pager.currentItem = pager.currentItem+1
    }
}