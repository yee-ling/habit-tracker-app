package com.example.habittrackerapp.ui.myHabit

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittrackerapp.R
import com.example.habittrackerapp.data.model.Frequency
import com.example.habittrackerapp.databinding.FragmentDailyBinding
import com.example.habittrackerapp.ui.adapter.FrequencyAdapter
import com.example.habittrackerapp.ui.adapter.HabitsAdapter
import kotlinx.coroutines.launch

class DailyFragment : Fragment() {
    private lateinit var binding: FragmentDailyBinding
    private lateinit var adapter: FrequencyAdapter
    private val viewModel: DailyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyBinding.inflate(inflater, container, false)
        return binding.root
    }

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
//        setFragmentResultListener("manage_habit") { _, _ ->
//            viewModel.getHabits()
//        }
    }

    fun setupAdapter() {
        adapter = FrequencyAdapter(habits = emptyList())
        binding.rvMyHabit.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMyHabit.adapter = adapter
    }
}