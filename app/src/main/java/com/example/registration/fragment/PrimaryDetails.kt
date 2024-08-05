package com.example.registration.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.registration.R
import com.example.registration.databinding.FragmentPrimaryDetailsBinding
import com.example.registration.viewModel.UserViewModel


class PrimaryDetails : Fragment() {

    private lateinit var binding: FragmentPrimaryDetailsBinding
    private var isPasswordVisible = false

    private lateinit var cameraResultLauncher: ActivityResultLauncher<Intent>
   // private lateinit var galleryResultLauncher: ActivityResultLauncher<String>

    private val launcher: ActivityResultLauncher<PickVisualMediaRequest> = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) {
        if (it == null) {
            Toast.makeText(requireActivity().baseContext, "No image Set", Toast.LENGTH_SHORT)
                .show()
        } else {
            Glide.with(requireActivity().baseContext).load(it).into(binding.profileImage)
        }
    }

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
        // Initialize result launchers
        cameraResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // Handle the camera image here
                    // You can retrieve the image from result.data if needed
                }
            }

        /*galleryResultLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                // Handle the gallery image here
                if (uri != null) {
                    // You can use uri to load the image
                }
            }
*/
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

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResultLauncher.launch(
            takePictureIntent
        )


    }

    private fun openGallery() {
//        galleryResultLauncher.launch("image/*")

        launcher.launch(
            PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                .build()
        )
    }

    /*override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/


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

    /*private fun storeData() {
        viewModel.userData.observe(this) {

        }
    }*/

}