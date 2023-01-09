package com.reift.storyapp.presentation.post

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.reift.storyapp.R
import com.reift.storyapp.databinding.ActivityPostBinding
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.presentation.main.MainActivity
import com.reift.storyapp.utils.MediaUtils.createCustomTempFile
import com.reift.storyapp.utils.MediaUtils.reduceFileImage
import com.reift.storyapp.utils.MediaUtils.uriToFile
import java.io.File
import org.koin.android.viewmodel.ext.android.viewModel

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding

    private lateinit var currentPhotoPath: String

    private val viewModel: PostViewModel by viewModel()

    private var photoFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpView()
    }

    private fun setUpView() {
        binding.apply {
            fabGallery.setOnClickListener{
                startGallery()
            }
            fabCamera.setOnClickListener{
                startCamera()
            }
            btnUpload.setOnClickListener {
                uploadImage()
            }
            btnBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun uploadImage() {
        val description = binding.edtDescription.text.toString()
        if (photoFile == null) {
            Toast.makeText(this, "Silakan masukkan berkas gambar terlebih dahulu.", Toast.LENGTH_SHORT).show()
        } else if (description.isNullOrEmpty()) {
            Toast.makeText(this, "Silakan isi deskripsi terlebih dahulu", Toast.LENGTH_SHORT).show()
        } else {
            val file = reduceFileImage(photoFile as File)
            viewModel.postStory(description, file)
            viewModel.postResponse.observe(this){ resource ->
                when(resource){
                    is Resource.Success -> {
                        Toast.makeText(this, "Finish upload story", Toast.LENGTH_SHORT).show()
                        finishAffinity()
                        startActivity(Intent(this, MainActivity::class.java))
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

}