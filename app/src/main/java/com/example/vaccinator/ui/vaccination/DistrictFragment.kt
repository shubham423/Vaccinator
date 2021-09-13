package com.example.vaccinator.ui.vaccination

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.example.vaccinator.R
import com.example.vaccinator.data.models.StatesResponse.*
import com.example.vaccinator.databinding.FragmentDistrictBinding
import com.example.vaccinator.viewmodels.VaccinationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DistrictFragment : Fragment() {
    private lateinit var binding: FragmentDistrictBinding

    private val viewModel: VaccinationViewModel by activityViewModels()

    private lateinit var states: ArrayList<String>
    private lateinit var statesObjects: ArrayList<State>
    private lateinit var districts: ArrayList<String>
    private var selectedStateId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDistrictBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        states = ArrayList()
        statesObjects = ArrayList()
        districts = ArrayList()

        viewModel.getStates()

        initObservers()

        val arrayAdapterStates = ArrayAdapter(requireContext(), R.layout.dropdown_item, states)
        binding.stateTv.setAdapter(arrayAdapterStates)

        Log.d("District","Selected state is ${binding.stateTv.text}")
        viewModel.setSelectedState("Delhi")

        binding.button.setOnClickListener {

        }

    }

    private fun initObservers() {

        viewModel.statesList.observe(viewLifecycleOwner) {
            states.clear()
            for (i in it) {
                statesObjects.add(i)
                states.add(i.stateName)
            }
        }
        viewModel.districtsList.observe(viewLifecycleOwner) {
            districts.clear()
            for (i in it) {
                districts.add(i.districtName)
            }
            val arrayAdapterDistricts =
                ArrayAdapter(requireContext(), R.layout.dropdown_item, districts)
            binding.districtTv.setAdapter(arrayAdapterDistricts)
        }


    }


}