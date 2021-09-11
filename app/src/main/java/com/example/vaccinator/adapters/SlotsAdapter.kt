package com.example.vaccinator.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vaccinator.data.models.SlotResponse.Session
import com.example.vaccinator.databinding.ItemSlotBinding

class SlotsAdapter(private val list: List<Session>) :
    ListAdapter<Session, SlotsAdapter.SlotsViewHolder>(
        object : DiffUtil.ItemCallback<Session>() {
            override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Session, newItem: Session): Boolean {
                return oldItem.toString() == newItem.toString()
            }
        }
    ) {
    inner class SlotsViewHolder(binding : ItemSlotBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotsViewHolder {
        val binding = ItemSlotBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SlotsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlotsViewHolder, position: Int) {
        ItemSlotBinding.bind(holder.itemView).apply {
            val slot= getItem(position)
            vCentreName.text = slot.centerId.toString()
            vCentreAddress.text = slot.address
            vaccination.text = slot.name
            vcPinCode.text = "Pin Code: " + slot.pincode
            vcstate.text = "State: " + slot.stateName
            vcAgeLimit.text = "Age Limit: " + slot.minAgeLimit
            vcType.text = "Type: " + slot.vaccine
            vcDoseOne.text = "Dose 01: " + slot.availableCapacityDose1
            vcDoseTwo.text = "Dose 02: " + slot.availableCapacityDose2
            vcDosetotal.text = "Total: " + slot.availableCapacity
        }

    }

}