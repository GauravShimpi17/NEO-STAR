package com.example.registration.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.registration.R
import com.example.registration.databinding.FragmentExperienceDetailsBinding
import com.example.registration.view.util.enumClass.Education
import com.example.registration.view.util.Util
import com.example.registration.view.util.enumClass.Designation
import com.example.registration.view.util.enumClass.Domain
import com.example.registration.viewModel.UserViewModel


class ExperienceDetails : Fragment() {

    private lateinit var binding: FragmentExperienceDetailsBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_experience_details,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setOnClickListener()
        populateSpinner()



        viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        binding.primaryData = viewModel
    }


    private fun setUpToolbar() {

        val toolbar = binding.appbarYourInfo.toolbar
        binding.appbarYourInfo.tbTitle.text = getString(R.string.tb_experience_title)
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowTitleEnabled(false)
            }

            toolbar.setNavigationOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frameRegistration, PrimaryDetails())
                    .commit()
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnProfessionalNext.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameRegistration, AddressDetails())
                .addToBackStack(null)
                .commit()
        }

        binding.btnPrevious.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameRegistration, PrimaryDetails())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun populateSpinner() {
        val spinnerEducation = binding.spnEducation
        val education = Education.entries.map { it.education }.toMutableList()

        val spinnerYear = binding.spnPassingYear
        val years = Util.SpinnerUtils.generateYearList()

        val spinnerDesignation = binding.spnDesignation
        val designation = Designation.entries.map { it.designation }.toMutableList()

        val spinnerDomain = binding.spnDomain
        val domain = Domain.entries.map { it.domain }.toMutableList()

        Util.SpinnerUtils.populateSpinner(
            requireContext(),
            spinnerEducation,
            education,
            hint = "Select Education Level"
        ) {
            var educationLevel = Education.NONE
            if (it != null) {

                educationLevel = Education.valueOf(it.uppercase().replace(" ", "_").replace("/", "_"))
            }
            val oldData = viewModel.userProfessionalData.get() ?: return@populateSpinner
            val newData = oldData.copy(education = educationLevel.toString())
            viewModel.userProfessionalData.set(newData)
            Toast.makeText(requireContext(), "Selected: ${it ?: "None"}", Toast.LENGTH_SHORT).show()
        }

        Util.SpinnerUtils.populateSpinner(
            requireContext(),
            spinnerYear,
            years,
            hint = "Select passing year"
        ) { selectedYear ->
            Toast.makeText(
                requireContext(),
                "Selected Year: ${selectedYear ?: "None"}",
                Toast.LENGTH_SHORT
            ).show()
        }

        Util.SpinnerUtils.populateSpinner(
            requireContext(),
            spinnerDesignation,
            designation,
            hint = "Select Designation"
        ) {
            var designationLevel = Designation.NONE
            if (it != null) {
                designationLevel = Designation.valueOf(it.uppercase().replace(" ", "_"))
            }
            val oldData = viewModel.userProfessionalData.get() ?: return@populateSpinner
            val newData = oldData.copy(designation = designationLevel.toString())
            viewModel.userProfessionalData.set(newData)
            Toast.makeText(requireContext(), "Selected: ${it ?: "None"}", Toast.LENGTH_SHORT).show()
        }

        Util.SpinnerUtils.populateSpinner(
            requireContext(),
            spinnerDomain,
            domain,
            hint = "Select your domain"
        ) {
            var domainLevel = Domain.NONE
            if (it != null) {

                domainLevel = Domain.valueOf(it.uppercase().replace(" ", "_"))
            }
            val oldData = viewModel.userProfessionalData.get() ?: return@populateSpinner
            val newData = oldData.copy(domain = domainLevel.toString())
            viewModel.userProfessionalData.set(newData)
            Toast.makeText(requireContext(), "Selected: ${it ?: "None"}", Toast.LENGTH_SHORT).show()
        }

        /*val educationLevels = mutableListOf(
            *//*"Select Education Level", // Hint item*//*
            *education
        )*/
        /*val adapterEducation = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            educationLevels
        )

        adapterEducation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        spinner.adapter = adapterEducation
        spinner.setSelection(0, false)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    return
                }
                val selectedEducationLevel =
                    Education.entries.find { it.education == educationLevels[position] }
                // Do something with the selected education level
                Toast.makeText(
                    requireContext(),
                    "Selected: ${selectedEducationLevel?.education}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }*/
    }

}