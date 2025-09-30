package com.example.habittrackerapp.ui.myHabit

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.habittrackerapp.data.model.Frequency
import kotlinx.coroutines.launch

class DailyFragment : BaseFrequencyFragment() {
    override val viewModel: DailyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDaily.text = "${Frequency.DAILY}"
        setupAdapter()
        viewModel.getHabits()
        lifecycleScope.launch {
            viewModel.habits.collect {
                adapter.setHabits(it)
            }
        }
    }
}