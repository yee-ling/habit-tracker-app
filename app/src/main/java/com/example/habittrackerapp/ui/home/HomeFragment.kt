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
import com.example.habittrackerapp.ui.adapter.CalendarAdapter
import com.example.habittrackerapp.ui.adapter.HabitsAdapter
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HabitsAdapter
    private lateinit var calendarAdapter: CalendarAdapter
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
        setupCalendarAdapter()
//        setFragmentResultListener("manage_habit") { _, _ ->
//            viewModel.getHabits()
//        }
        viewModel.getHabits()
        lifecycleScope.launch {
            viewModel.habits.collect {
                adapter.setHabits(it)
            }
        }
    }
    fun navigateToAddHabit() {
        binding.fabAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToAddHabit()
            findNavController().navigate(action)
        }
    }
    fun setupAdapter() {
        adapter = HabitsAdapter(habits = emptyList()) {
            val action = HomeFragmentDirections.actionHomeToHabitDetails(it.id!!)
            findNavController().navigate(action)
        }
        binding.rvHabits.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHabits.adapter = adapter
    }
    fun setupCalendarAdapter() {
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val days = (-1000..1000).map { offset ->
            Calendar.getInstance().apply {
                timeInMillis = today.timeInMillis
                add(Calendar.DAY_OF_YEAR, offset)
            }.timeInMillis
        }
//        TODO onclick
        calendarAdapter = CalendarAdapter(days) {}
        binding.rvCalendarView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)
        binding.rvCalendarView.adapter = calendarAdapter
        val todayIndex = 1000
        binding.rvCalendarView.scrollToPosition(todayIndex)
    }
}