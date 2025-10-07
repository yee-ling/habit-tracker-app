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
    private var selectedDate: Long = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

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
        viewModel.getHabits(selectedDate)
        lifecycleScope.launch {
            viewModel.habits.collect {
                adapter.setHabits(it)
                adapter.setSelectedDate(selectedDate)
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
        adapter = HabitsAdapter(
            habits = emptyList(),
            onClick = {
                val action = HomeFragmentDirections.actionHomeToHabitDetails(it.id!!)
                findNavController().navigate(action)
            },
            onCheckboxClick = { it ->
                val habitProgressForDate = it.progress.find { it.date == selectedDate }
                val newCount = habitProgressForDate?.progress?.plus(1) ?: 1
                if (newCount <= it.habit.repeatsPerDay)
                    viewModel.updateProgress(it.habit.id!!, selectedDate, newCount)
            },
            selectedDate = selectedDate
        )
        binding.rvHabits.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHabits.adapter = adapter
    }
    fun setupCalendarAdapter() {
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val days = (-1000..1000).map { offset ->
            Calendar.getInstance().apply {
                timeInMillis = today
                add(Calendar.DAY_OF_YEAR, offset)
            }.timeInMillis
        }
        calendarAdapter = CalendarAdapter(days) { date ->
            selectedDate = date
            viewModel.getHabits(selectedDate = date)
            adapter.setHabits(viewModel.habits.value)
            adapter.setSelectedDate(selectedDate)
        }
        binding.rvCalendarView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)
        binding.rvCalendarView.adapter = calendarAdapter
        val todayIndex = 1000
        binding.rvCalendarView.scrollToPosition(todayIndex)
    }
}