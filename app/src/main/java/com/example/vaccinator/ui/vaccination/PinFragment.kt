package com.example.vaccinator.ui.vaccination

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.vaccinator.databinding.FragmentPinBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PinFragment : Fragment() {
    private lateinit var binding: FragmentPinBinding

    private lateinit var slotsAdapter: SlotsAdapter
    private  val viewModel: VaccinationViewModel by activityViewModels()

    private var date: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPinBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpClickListeners()
        initObservers()
    }

    private fun setUpClickListeners() {
        binding.searchBtn.setOnClickListener {
            val pincode=binding.pinCodeEt.text.toString()
            if (date!=null && pincode!=null){
                viewModel.getFeed(pincode, date!!)
            }

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

        private fun initObservers() {
            viewModel.slotsList.observe(viewLifecycleOwner) {
                Log.d("PinFragment", "$it")
                slotsAdapter = SlotsAdapter(it)
                binding.rvSlots.adapter = slotsAdapter
                slotsAdapter.submitList(it)
            }
        }
    }