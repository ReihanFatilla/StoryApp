package com.reift.storyapp.presentation.post

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.reift.storyapp.R
import com.reift.storyapp.databinding.ActivityPostBinding
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.presentation.MainActivity
import com.reift.storyapp.presentation.dialog.LoadingDialog
import com.reift.storyapp.utils.Locationutils
import com.reift.storyapp.utils.MediaUtils.createCustomTempFile
import com.reift.storyapp.utils.MediaUtils.reduceFileImage
import com.reift.storyapp.utils.MediaUtils.uriToFile
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.util.concurrent.TimeUnit

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding

    private lateinit var currentPhotoPath: String
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: PostViewModel by viewModel()

    private var photoFile: File? = null
    private var currentLocation: Location? = null
    private val locationCallback = object : LocationCallback() {}
    private val locationRequest = LocationRequest.create().apply {
        interval = TimeUnit.SECONDS.toMillis(1)
        maxWaitTime = TimeUnit.SECONDS.toMillis(1)
        priority = Priority.PRIORITY_HIGH_ACCURACY
    }

    private var loadingDialog = LoadingDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setUpView()
    }

    private fun setUpView() {
        binding.apply {
            fabGallery.setOnClickListener {
                startGallery()
            }
            fabCamera.setOnClickListener {
                startCamera()
            }
            btnUpload.setOnClickListener {
                uploadImage()
            }
            btnBack.setOnClickListener {
                finish()
            }
            btnProvideLocation.setOnClickListener {
                if(btnProvideLocation.isChecked){
                    getLocation()
                } else {
                    currentLocation = null
                }
            }
        }
    }

    private fun getLocation() {
        if (Locationutils.checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
            Locationutils.checkPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    currentLocation = it
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.txt_location_not_found),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun uploadImage() {
        val description = binding.edtDescription.text.toString()
        if (photoFile == null) {
            Toast.makeText(this, getString(R.string.import_photo), Toast.LENGTH_SHORT).show()
        } else if (description.isEmpty()) {
            Toast.makeText(this, getString(R.string.fill_description), Toast.LENGTH_SHORT).show()
        } else {
            loadingDialog.startLoadingdialog()
            val file = reduceFileImage(photoFile as File)
            if (currentLocation != null) {
                val latLng =
                    LatLng(currentLocation?.latitude ?: 0.0, currentLocation?.longitude ?: 0.0)
                viewModel.postStory(description, file, latLng)
            } else {
                viewModel.postStory(description, file)
            }
            viewModel.postResponse.observe(this) { resource ->
                when (resource) {
                    is Resource.Success -> {
                        loadingDialog.dismissdialog()
                        Toast.makeText(this, getString(R.string.finish_upload), Toast.LENGTH_SHORT)
                            .show()
                        finishAffinity()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                    is Resource.Loading -> {
                        loadingDialog.startLoadingdialog()
                    }
                    is Resource.Error -> {
                        loadingDialog.dismissdialog()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.reift.storyapp",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            photoFile = myFile
            binding.imgThumbnail.setImageURI(selectedImg)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            val result = BitmapFactory.decodeFile(myFile.path)
            binding.imgThumbnail.setImageBitmap(result)
            photoFile = myFile
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true -> getLocation()
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true -> getLocation()
                else -> {
                    Toast.makeText(
                        this,
                        "Dont have location permission",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    private fun startLocationUpdates() {
        if (Locationutils.checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
            Locationutils.checkPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        stopLocationUpdates()
        super.onPause()
    }


}