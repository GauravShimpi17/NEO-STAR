package com.example.registration.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.registration.R
import com.example.registration.data.model.PrimaryDataWithDetails
import com.example.registration.databinding.ProfileCardBinding
import com.example.registration.view.util.load
import com.google.android.material.imageview.ShapeableImageView

class GetAllAdapter() : RecyclerView.Adapter<GetAllAdapter.MyViewHolder>() {

    companion object{
        private const val TAG = "GetAllAdapter"
    }

    var dataList: List<PrimaryDataWithDetails>
        get() = differ.currentList
        set(value) = differ.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ProfileCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataList[position]
        Log.d(TAG,item.toString())
        Log.d(TAG, item.toString())
        with(holder.binding) {
            // Load image using Glide
            Glide.with(imgProfile.context)
                .load(item.primaryData.imageUri)
                .placeholder(R.drawable.profile) // Add a placeholder image if needed
                .error(R.drawable.profile) // Add an error image if needed
                .into(imgProfile)
            txtUserName.text = item.primaryData.firstName
            txtUserEducation.text = item.professionalData?.education ?: "N/A"
            txtUserExperience.text = item.professionalData?.experience ?: "N/A"
            txtUserAddress.text = item.addressData?.address ?: "N/A"
        }
    }

    override fun getItemCount(): Int = dataList.size

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<PrimaryDataWithDetails>() {
        override fun areItemsTheSame(oldItem: PrimaryDataWithDetails, newItem: PrimaryDataWithDetails): Boolean {
            return oldItem.primaryData.primaryInfoId == newItem.primaryData.primaryInfoId
        }

        override fun areContentsTheSame(oldItem: PrimaryDataWithDetails, newItem: PrimaryDataWithDetails): Boolean {
            return oldItem == newItem
        }
    }

    private val differ =  AsyncListDiffer(this,DiffUtil())


    class MyViewHolder(val binding: ProfileCardBinding) : RecyclerView.ViewHolder(binding.root)
}
