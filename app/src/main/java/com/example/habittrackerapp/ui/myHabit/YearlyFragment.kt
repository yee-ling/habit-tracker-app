package com.example.habittrackerapp.ui.myHabit

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class YearlyFragment : BaseFrequencyFragment() {
    override val viewModel: YearlyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        viewModel.getHabits()
        lifecycleScope.launch {
            viewModel.habits.collect {
                adapter.setHabits(it)
            }
        }
    }
}