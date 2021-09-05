package com.example.vaccinator.ui.vaccination

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


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
