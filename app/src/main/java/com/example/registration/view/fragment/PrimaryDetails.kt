package com.example.registration.view.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.registration.R
import com.example.registration.databinding.FragmentPrimaryDetailsBinding
import com.example.registration.viewModel.UserViewModel
import com.google.android.material.imageview.ShapeableImageView
import java.io.File
import java.io.InputStream

class PrimaryDetails : Fragment() {

    private lateinit var binding: FragmentPrimaryDetailsBinding
    private var isPasswordVisible = false

    private lateinit var imageUri: Uri

    private var pos = 1

    private lateinit var cameraResultLauncher: ActivityResultLauncher<Uri>
    private lateinit var galleryResultLauncher: ActivityResultLauncher<String>

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_primary_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showPassword()
        setOnClickListener()
        profileImage()

        viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        binding.primaryData = viewModel
    }

    private fun profileImage() {
        initializeActivityResultLaunchers()

        binding.btnSelectProfile.setOnClickListener {
            showImageSourceDialog()
        }
    }

    private fun showImageSourceDialog() {
        val options = arrayOf("Open Camera", "Open Gallery")
        AlertDialog.Builder(requireContext())
            .setTitle("Select Image Source")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()  // Open Camera
                    1 -> openGallery() // Open Gallery
                }
            }
            .show()
    }

    private fun initializeActivityResultLaunchers() {
        cameraResultLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
                if (isSuccess) {
                    handleImage(imageUri)
                }
            }

        galleryResultLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    handleImage(it)
                }
            }
    }

    private fun handleImage(uri: Uri) {
        // Convert the Uri to a Bitmap
        val bitmap = getBitmapFromUri(uri)

        // Crop the Bitmap based on aspect ratio
        val aspectRatio = 1f // Example aspect ratio
        val croppedBitmap = cropImageToAspectRatio(bitmap, aspectRatio)

        // Display the cropped Bitmap in ImageView
        val imageView: ShapeableImageView = binding.profileImage
        imageView.setImageBitmap(croppedBitmap)
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val inputStream: InputStream? = requireContext().contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream)
    }

    private fun cropImageToAspectRatio(bitmap: Bitmap, aspectRatio: Float): Bitmap {
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height

        // Calculate target dimensions
        val targetWidth: Int
        val targetHeight: Int

        if (originalWidth / originalHeight > aspectRatio) {
            // Wider than target aspect ratio, crop width
            targetHeight = originalHeight
            targetWidth = (targetHeight * aspectRatio).toInt()
        } else {
            // Taller than target aspect ratio, crop height
            targetWidth = originalWidth
            targetHeight = (targetWidth / aspectRatio).toInt()
        }

        // Calculate the cropping rectangle
        val cropLeft = ((originalWidth - targetWidth) / 2).coerceAtLeast(0)
        val cropTop = ((originalHeight - targetHeight) / 2).coerceAtLeast(0)

        return Bitmap.createBitmap(
            bitmap,
            cropLeft,
            cropTop,
            targetWidth.coerceAtMost(originalWidth - cropLeft),
            targetHeight.coerceAtMost(originalHeight - cropTop)
        )
    }

    private fun createImageUri(): Uri {
        val image = File(requireContext().filesDir, "external_files${pos++}.png")
        return FileProvider.getUriForFile(
            requireContext(),
            "com.example.registration.fragment.fileProvider",
            image
        )
    }

    private fun openCamera() {
        imageUri = createImageUri()
        cameraResultLauncher.launch(imageUri)
    }

    private fun openGallery() {
        galleryResultLauncher.launch("image/*")
    }

    private fun showPassword() {
        val showPasswordBtn = binding.ibShowPassword
        val passwordInput = binding.txtPassword

        showPasswordBtn.setOnClickListener {
            if (isPasswordVisible) {
                passwordInput.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPasswordBtn.setImageResource(R.drawable.ic_show_password)
            } else {
                passwordInput.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPasswordBtn.setImageResource(R.drawable.ic_eye_off)
            }
            passwordInput.setSelection(passwordInput.text.length)
            isPasswordVisible = !isPasswordVisible
        }
    }

    private fun setOnClickListener() {
        binding.btnNext.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameRegistration, ExperienceDetails())
                .addToBackStack(null)
                .commit()
        }
    }
}


/*
package com.example.registration.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.registration.R
import com.example.registration.databinding.FragmentPrimaryDetailsBinding
import com.example.registration.viewModel.UserViewModel
import com.google.android.material.imageview.ShapeableImageView
import java.io.File


class PrimaryDetails : Fragment() {

    private lateinit var binding: FragmentPrimaryDetailsBinding
    private var isPasswordVisible = false

    private lateinit var imageUri : Uri

    private var pos = 1


    private lateinit var cameraResultLauncher: ActivityResultLauncher<Uri>
    private lateinit var galleryResultLauncher: ActivityResultLauncher<String>



    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_primary_details, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showPassword()
        setOnClickListener()
        profileImage()

        viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        binding.primaryData = viewModel

    }


    private fun profileImage() {
        initializeActivityResultLaunchers()

        binding.btnSelectProfile.setOnClickListener {
            showImageSourceDialog()
        }
    }

    private fun showImageSourceDialog() {
        val options = arrayOf("Open Camera", "Open Gallery")
        AlertDialog.Builder(requireContext())
            .setTitle("Select Image Source")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()  // Open Camera
                    1 -> openGallery() // Open Gallery
                }
            }
            .show()
    }

    private fun initializeActivityResultLaunchers() {
        cameraResultLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
                if (isSuccess) {
                    handleImage(imageUri)
                }
            }

        galleryResultLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    handleImage(it)
                }
            }
    }

    private fun handleImage(uri: Uri) {
        // Process the image URI (e.g., display it in an ImageView)
        val imageView: ShapeableImageView = binding.profileImage
        imageView.setImageURI(uri)
    }

    private fun createImageUri(): Uri {
        val image = File(requireContext().filesDir, "external_files${pos++}.png")
        return FileProvider.getUriForFile(
            requireContext(),
            "com.example.registration.fragment.fileProvider",
            image
        )
    }

    private fun openCamera() {
        imageUri = createImageUri()
        cameraResultLauncher.launch(imageUri)
    }

    private fun openGallery() {
        galleryResultLauncher.launch("image/*")
    }

    private fun showPassword() {
        val showPasswordBtn = binding.ibShowPassword
        val passwordInput = binding.txtPassword

        showPasswordBtn.setOnClickListener {
            if (isPasswordVisible) {
                passwordInput.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPasswordBtn.setImageResource(R.drawable.ic_show_password)
            } else {
                passwordInput.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPasswordBtn.setImageResource(R.drawable.ic_eye_off)
            }
            passwordInput.setSelection(passwordInput.text.length)
            isPasswordVisible = !isPasswordVisible
        }

    }

    private fun setOnClickListener() {
        binding.btnNext.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameRegistration, ExperienceDetails())
                .addToBackStack(null)
                .commit()
        }
    }
}


*/
/**/