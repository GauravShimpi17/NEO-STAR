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
import com.example.registration.data.model.PrimaryData
import com.example.registration.data.model.ProfessionalData
import com.example.registration.databinding.FragmentExperienceDetailsBinding
import com.example.registration.view.util.enumClass.Education
import com.example.registration.view.util.enumClass.Designation
import com.example.registration.view.util.enumClass.Domain
import com.example.registration.view.util.generateYearList
import com.example.registration.view.util.populateSpinner
import com.example.registration.viewModel.UserViewModel
import java.util.Calendar


class ExperienceDetailsFragment : Fragment() {

    private lateinit var binding: FragmentExperienceDetailsBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_experience_details, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        binding.primaryData = viewModel
        setUpToolbar()
        populateSpinner()
        setOnClickListener()
        setupObservers()
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
                    .replace(R.id.frameRegistration, PrimaryDetailsFragment()).commit()
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnProfessionalNext.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameRegistration, AddressDetailsFragment()).addToBackStack(null)
                .commit()
        }

        binding.btnPrevious.setOnClickListener {
            val professionalData = ProfessionalData(
                experience = binding.txtExperience.text.toString(),
                grade = binding.txtExperience.text.toString(),
                designation = binding.spnDesignation.selectedItem.toString(),
                education = binding.spnEducation.selectedItem.toString(),
            )
            if (viewModel.validateExperienceData(professionalData)) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frameRegistration, PrimaryDetailsFragment()).addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun setupObservers() {
        viewModel.professionalDataError.observe(viewLifecycleOwner) { error ->
            if (error == null) return@observe
            binding.txtGrade.error = error.grade
            binding.txtExperience.error = error.experience
            if (error.education != null) {
                binding.errorTextView.text = error.education
            } else {
                binding.errorTextView.text = "Select education"
            }
            if (error.yearOfPassing != null) {
                binding.errorTextViewPassingYear.text = error.yearOfPassing
            } else {
                binding.errorTextViewPassingYear.text = "Select year of passing"
            }
            if (error.designation != null) {
                binding.errorTextViewDesignation.text = error.yearOfPassing
            } else {
                binding.errorTextViewDesignation.text = "Select designation"
            }
        }
    }

    private fun populateSpinner() {

        val selectedItemEducation = Education.getEducation(
            viewModel.userProfessionalData.get()?.education ?: Education.NONE.toString()
        ).ordinal
        binding.spnEducation.populateSpinner(
            requireContext(),
            Education::class.java,
            selectedItemEducation
        ) {
            val oldData = viewModel.userProfessionalData.get() ?: return@populateSpinner
            val newData = oldData.copy(education = it.toString())
            viewModel.userProfessionalData.set(newData)

            Toast.makeText(requireContext(), "Selected: $it", Toast.LENGTH_SHORT).show()
        }

        val spinnerYear = binding.spnPassingYear
        val years = generateYearList()
        val yearOfPassingStr =
            viewModel.userProfessionalData.get()?.yearOfPassing ?: Calendar.getInstance()
                .get(Calendar.YEAR)
        var selectedItemYear = 0
        generateYearList().forEachIndexed { index, i ->
            if (i.toString() == yearOfPassingStr) {
                selectedItemYear = index
            }
        }

        populateSpinner(
            requireContext(),
            spinnerYear,
            years,
            hint = "Select passing year",
            selectedItem = selectedItemYear + 1
        ) { selectedYear ->
            val oldData = viewModel.userProfessionalData.get() ?: return@populateSpinner
            val newData = oldData.copy(yearOfPassing = selectedYear.toString())
            viewModel.userProfessionalData.set(newData)
        }

        val spinnerDesignation = binding.spnDesignation
        val selectedItemDesignation = Designation.getEducation(
            viewModel.userProfessionalData.get()?.designation ?: Designation.NONE.toString()
        ).ordinal
        spinnerDesignation.populateSpinner(
            requireContext(), Designation::class.java, selectedItemDesignation
        ) {
            val oldData = viewModel.userProfessionalData.get() ?: return@populateSpinner
            val newData = oldData.copy(designation = it.toString())
            viewModel.userProfessionalData.set(newData)

            Toast.makeText(requireContext(), "Selected: $it", Toast.LENGTH_SHORT).show()
        }

        val spinnerDomain = binding.spnDomain
        val selectedItemDomain = Domain.getEducation(
            viewModel.userProfessionalData.get()?.domain ?: Domain.NONE.toString()
        ).ordinal
        spinnerDomain.populateSpinner(
            requireContext(), Domain::class.java, selectedItemDomain
        ) {
            val oldData = viewModel.userProfessionalData.get() ?: return@populateSpinner
            val newData = oldData.copy(domain = it.toString())
            viewModel.userProfessionalData.set(newData)

            Toast.makeText(requireContext(), "Selected: $it", Toast.LENGTH_SHORT).show()
        }

    }
}