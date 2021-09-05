package com.example.vaccinator.ui.vaccination

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.vaccinator.databinding.FragmentPinBinding


class PinFragment : Fragment() {
    private lateinit var binding: FragmentPinBinding

    private lateinit var slotsAdapter: SlotsAdapter
    private lateinit var viewModel: VaccinationViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPinBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(VaccinationViewModel::class.java)
        setUpClickListeners()

    }

    private fun setUpClickListeners() {
        binding.searchBtn.setOnClickListener {
            viewModel.getFeed(binding.pinCodeEt.text.toString(),"06-09-2021")
        }
        initObservers()
    }

    private fun initObservers() {
        viewModel.slotsList.observe(viewLifecycleOwner){
            Log.d("PinFragment","$it")
            slotsAdapter= SlotsAdapter(it)
            binding.rvSlots.adapter=slotsAdapter
            slotsAdapter.submitList(it)
        }
    }
}