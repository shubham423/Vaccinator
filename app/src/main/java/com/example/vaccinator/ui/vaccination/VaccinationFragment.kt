package com.example.vaccinator.ui.vaccination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vaccinator.R
import com.example.vaccinator.databinding.FragmentVaccinationBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VaccinationFragment : Fragment() {

    private var _binding: FragmentVaccinationBinding? = null
    private val binding get() = _binding!!

    private lateinit var slotsViewPagerAdapter: SlotsViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentVaccinationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabLayoutWithViewPager()
    }

    private fun setupTabLayoutWithViewPager() {
        slotsViewPagerAdapter = SlotsViewPagerAdapter(requireActivity())
        binding.pager.adapter = slotsViewPagerAdapter


        TabLayoutMediator(
            binding.tabLayout, binding.pager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.search_by_pin)
                }
                1 -> {
                    tab.text = getString(R.string.search_by_district)
                }
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}