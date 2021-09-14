package com.example.vaccinator.ui.vaccination

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import com.example.utils.showDLog
import com.example.vaccinator.R
import com.example.vaccinator.data.models.StatesResponse.*
import com.example.vaccinator.databinding.FragmentDistrictBinding
import com.example.vaccinator.viewmodels.VaccinationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DistrictFragment : Fragment() {
    private lateinit var binding: FragmentDistrictBinding

    private val viewModel: VaccinationViewModel by activityViewModels()

    private var states: ArrayList<String> = ArrayList()
    private var statesObjects: ArrayList<State> = ArrayList()
    private var districts: ArrayList<String> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDistrictBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getStates()
        initObservers()
        Log.d("District","initobsever below $states")

        val adapterStates = ArrayAdapter(
            requireActivity(),
            R.layout.support_simple_spinner_dropdown_item,
            states
        )

        binding.statesSpinner.adapter = adapterStates
        adapterStates.notifyDataSetChanged()


        binding.statesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.tvState.text =states[position]
                Log.d("District","onitem $statesObjects")
                for (i in statesObjects){
                    Log.d("District","${i.stateName} and ${parent?.getItemAtPosition(position).toString()}")
                    if (i.stateName==parent?.getItemAtPosition(position).toString()){
                        viewModel.setSelectedState(i.stateId.toString())
                        return
                    }
                }
                Log.d("state id","${viewModel.selectedStateId}")
                viewModel.selectedStateId.value?.let { viewModel.getDistricts(it) }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //no ops
            }
        }

        val adapterDistricts = ArrayAdapter<String>(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            districts
        )

        binding.districtSpinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.tvDistricts.text= parent?.get(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //no ops
            }
        }

    }

    private fun initObservers() {
        viewModel.statesList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                for (i in it) {
                    statesObjects.add(i)
                    states.add(i.stateName)
                }
            }

        }
        viewModel.districtsList.observe(viewLifecycleOwner) {
            districts.clear()
            if (it.isNotEmpty()) {
                for (i in it) {
                    districts.add(i.districtName)
                }
            }
        }
    }


}