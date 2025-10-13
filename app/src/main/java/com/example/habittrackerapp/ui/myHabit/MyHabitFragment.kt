package com.example.habittrackerapp.ui.myHabit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.habittrackerapp.data.model.Frequency
import com.example.habittrackerapp.databinding.FragmentMyHabitBinding
import com.example.habittrackerapp.ui.adapter.MyHabitAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MyHabitFragment : Fragment() {
    private lateinit var binding: FragmentMyHabitBinding
    private lateinit var myHabitAdapter: MyHabitAdapter
    private lateinit var viewPager2: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFrequencyTab()
    }
    fun setupFrequencyTab() {
        myHabitAdapter = MyHabitAdapter(this)
        viewPager2 = binding.viewPager2
        viewPager2.adapter = myHabitAdapter
        val tabLayout = binding.tabLayout
        val tabTitles = listOf("${Frequency.DAILY}", "${Frequency.WEEKLY}", "${Frequency.MONTHLY}", "${Frequency.YEARLY}" )
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}