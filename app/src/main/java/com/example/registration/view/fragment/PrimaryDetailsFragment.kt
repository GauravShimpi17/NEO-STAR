package com.example.registration.view.fragment

import android.app.AlertDialog
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.registration.R
import com.example.registration.databinding.FragmentPrimaryDetailsBinding
import com.example.registration.view.util.bitmapToByteArray
import com.example.registration.view.util.cropImageToAspectRatio
import com.example.registration.view.util.getBitmapFromUri
import com.example.registration.viewModel.UserViewModel
import com.google.android.material.imageview.ShapeableImageView
import java.io.File

class PrimaryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPrimaryDetailsBinding
    private var isPasswordVisible = false


    private var pos = 1

    private lateinit var imageUri: Uri
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
        setupViewModel()
        showPassword()
        setOnClickListener()
        profileImage()
        initializeActivityResultLaunchers()

        restoreGenderSelection()

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        binding.primaryData = viewModel

    }

    private fun restoreGenderSelection() {
        viewModel.gender.observe(viewLifecycleOwner) { gender ->
            Log.d("GenderSelection", "Observed gender: $gender")
            when (gender) {
                "Male" -> binding.rgGender.check(binding.rbMale.id)
                "Female" -> binding.rgGender.check(binding.rbFemale.id)
                else -> binding.rgGender.clearCheck()
            }
        }

        binding.rgGender.setOnCheckedChangeListener { _, checkedId ->
            val gender = when (checkedId) {
                R.id.rbMale -> "Male"
                R.id.rbFemale -> "Female"
                else -> ""
            }
            viewModel.setGender(gender)
        }
    }


    private fun profileImage() {
        binding.btnSelectProfile.setOnClickListener {
            showImageSourceDialog()
        }
    }


    private fun showImageSourceDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.layout_image_picker, null)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Select Image Source")
            .setView(dialogView)
            .create()

        val btnCamera = dialogView.findViewById<ConstraintLayout>(R.id.openCamera)
        val btnGallery = dialogView.findViewById<ConstraintLayout>(R.id.openGallery)

        btnCamera.setOnClickListener {
            openCamera()
            alertDialog.dismiss()
        }
        btnGallery.setOnClickListener {
            openGallery()
            alertDialog.dismiss()
        }

        alertDialog.show()
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
        val bitmap: Bitmap = uri.getBitmapFromUri(requireContext())
        val aspectRatio = 1f
        val croppedBitmap = bitmap.cropImageToAspectRatio(aspectRatio)

        val byteArray = bitmapToByteArray(croppedBitmap)
        viewModel.setImage(byteArray)

        val imageView: ShapeableImageView = binding.profileImage
        imageView.setImageBitmap(croppedBitmap)
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
                .replace(R.id.frameRegistration, ExperienceDetailsFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}