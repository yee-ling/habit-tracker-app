package com.example.habittrackerapp.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittrackerapp.databinding.FragmentHomeBinding
import com.example.habittrackerapp.ui.adapter.HabitsAdapter
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HabitsAdapter
//    private lateinit var calendarAdapter: CalendarAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToAddHabit()
        setupAdapter()
//        setupCalendarAdapter()
//        setFragmentResultListener("manage_habit") { _, _ ->
//            viewModel.getHabits()
//        }
        viewModel.getHabits()
        lifecycleScope.launch {
            viewModel.habits.collect {
                adapter.setHabits(it)
            }
        }
//        binding.mbMyHabit.setOnClickListener {
//            val action = HomeFragmentDirections.actionHomeFragmentToMyHabitFragment()
//            findNavController().navigate(action)
//        }
    }
    fun navigateToAddHabit() {
        binding.fabAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToAddHabit()
            findNavController().navigate(action)
        }
    }
    fun setupAdapter() {
        adapter = HabitsAdapter(habits = emptyList())
        binding.rvHabits.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHabits.adapter = adapter
    }

//    fun setupCalendarAdapter() {
//        val today = LocalDate.now()
//        val days = (-3..3).map { offset -> Calendar(date = today.plusDays(offset.toLong())) }
//        calendarAdapter = CalendarAdapter(days)
//        binding.rvCalendar.layoutManager = LinearLayoutManager(requireContext(),
//            LinearLayoutManager.HORIZONTAL, false)
//        binding.rvCalendar.adapter = calendarAdapter
//    }
}