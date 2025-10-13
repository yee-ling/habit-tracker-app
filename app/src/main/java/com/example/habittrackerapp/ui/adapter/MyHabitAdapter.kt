package com.example.habittrackerapp.ui.adapter

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habittrackerapp.ui.myHabit.DailyFragment
import com.example.habittrackerapp.ui.myHabit.MonthlyFragment
import com.example.habittrackerapp.ui.myHabit.WeeklyFragment
import com.example.habittrackerapp.ui.myHabit.YearlyFragment

class MyHabitAdapter(fragment: androidx.fragment.app.Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4
    override fun createFragment(position: Int): androidx.fragment.app.Fragment {
        val fragment = when(position) {
            0 -> DailyFragment()
            1 -> WeeklyFragment()
            2 -> MonthlyFragment()
            3 -> YearlyFragment()
            else -> DailyFragment()
        }
        return fragment
    }
}