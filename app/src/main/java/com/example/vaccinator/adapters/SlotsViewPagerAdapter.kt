package com.example.vaccinator.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vaccinator.ui.vaccination.DistrictFragment
import com.example.vaccinator.ui.vaccination.PinFragment


class SlotsViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return PinFragment()
            1 -> return DistrictFragment()

        }
        return PinFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}
