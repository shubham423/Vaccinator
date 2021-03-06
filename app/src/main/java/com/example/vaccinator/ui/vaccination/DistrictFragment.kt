package com.example.vaccinator.ui.vaccination

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.utils.showDLog
import com.example.vaccinator.R
import com.example.vaccinator.adapters.SlotsAdapter
import com.example.vaccinator.data.models.DistrictsResponse.District
import com.example.vaccinator.data.models.StatesResponse.*
import com.example.vaccinator.databinding.FragmentDistrictBinding
import com.example.vaccinator.viewmodels.VaccinationViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class DistrictFragment : Fragment() {
    private lateinit var binding: FragmentDistrictBinding

    private val viewModel: VaccinationViewModel by activityViewModels()

    private var states: ArrayList<String> = ArrayList()
    private var statesObjects: ArrayList<State> = ArrayList()
    private var districtsObjects: ArrayList<District> = ArrayList()
    private var districts: ArrayList<String> = ArrayList()
    private var selectedDistrict: District?=null
    private lateinit var slotsAdapter: SlotsAdapter

    private var date: String? = null


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
        Log.d("District", "initobsever below $states")

        val stateAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, states)
        binding.statesSpinnerTv.setAdapter(stateAdapter)
        binding.statesSpinnerTv.setOnItemClickListener{_, _, position, _ ->
            Toast.makeText(requireContext(), states[position], Toast.LENGTH_SHORT).show()
            binding.districtSpinnerTv.isEnabled = true
            districts.clear()
            districtsObjects.clear()
            handleDistricts(position)
            binding.statesSpinnerTv.error = null

        }

        initCLickListeners()

    }

    private fun initCLickListeners() {
        binding.button.setOnClickListener {
            showDLog("selected district is $selectedDistrict")
            viewModel.getSlotsByDistrict(selectedDistrict?.districtId.toString(),date.toString())
        }

        binding.dateTv.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this.requireContext(), { _, year, monthOfYear, dayOfMonth ->
                    val dateStr = """$dayOfMonth-${monthOfYear + 1}-${year}"""
                    date = dateStr
                    binding.dateTv.text = dateStr
                },
                year,
                month,
                day
            )
            dpd.show()

        }
    }

    private fun handleDistricts(position: Int) {
        viewModel.getDistricts(position.toString())

        val districtsAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, districts)
        binding.districtSpinnerTv.apply {
            setAdapter(districtsAdapter)
        }

        binding.districtSpinnerTv.setOnItemClickListener { parent, view, position, id ->
            binding.districtSpinnerTv.error = null
            selectedDistrict=districtsObjects[position]
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
                    districtsObjects.add(i)
                }
            }
        }

        viewModel.slotsListByDistrict.observe(viewLifecycleOwner){
            showDLog("$$$$$$$$$$$$$$ $it")
            slotsAdapter= SlotsAdapter(it)
            binding.rvSlots.adapter=slotsAdapter
           slotsAdapter.submitList(it)
        }
    }
}