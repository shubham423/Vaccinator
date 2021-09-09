package com.example.vaccinator.ui.vaccination

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.vaccinator.R
import com.example.vaccinator.data.models.StatesResponse
import com.example.vaccinator.data.models.StatesResponse.*
import com.example.vaccinator.databinding.FragmentDashboardBinding
import com.example.vaccinator.databinding.FragmentDistrictBinding

class DistrictFragment : Fragment() {
    private lateinit var binding: FragmentDistrictBinding

    private lateinit var viewModel: VaccinationViewModel
    private lateinit var states: ArrayList<String>
    private lateinit var statesObjects: ArrayList<State>
    private lateinit var districts: ArrayList<String>
    private var selectedState=""
    private var selectedStateId=""
    private var selectedDistrict=""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentDistrictBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(VaccinationViewModel::class.java)
        viewModel.getStates()
        states= ArrayList()
        statesObjects= ArrayList()
        districts= ArrayList()

        initObservers()

        val arrayAdapterStates = ArrayAdapter(requireContext(), R.layout.dropdown_item, states)
        binding.stateTv.setAdapter(arrayAdapterStates)



        if (binding.stateTv.text.toString()!=getString(R.string.select_state)){
            selectedState=binding.stateTv.text.toString()
            for (i in statesObjects){
                if (i.stateName==selectedState){
                    selectedStateId=i.stateId.toString()
                    viewModel.getDistricts(selectedStateId)
                    initObservers()
                }
            }
        }

        binding.button.setOnClickListener {
            selectedState= binding.stateTv.text.toString()
        }

    }

    private fun initObservers() {

        viewModel.statesList.observe(viewLifecycleOwner){
            states.clear()
            for (i in it){
                statesObjects.add(i)
                states.add(i.stateName)
            }
        }
        viewModel.districtsList.observe(viewLifecycleOwner){
            districts.clear()
            for (i in it){
                districts.add(i.districtName)
            }
            val arrayAdapterDistricts = ArrayAdapter(requireContext(), R.layout.dropdown_item, districts)
            binding.districtTv.setAdapter(arrayAdapterDistricts)
        }


    }
}