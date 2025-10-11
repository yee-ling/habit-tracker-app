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
import com.example.habittrackerapp.data.model.Category
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
        viewModel.addRecord()
        navigateToAddHabit()
        setupAdapter()
        setupCalendarAdapter()
        viewModel.getHabits(selectedDate)
        lifecycleScope.launch {
            viewModel.habits.collect {
                adapter.setHabits(it)
                adapter.setSelectedDate(selectedDate)
                binding.llEmpty.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
            }
        }
        binding.cgCategory.setOnCheckedStateChangeListener { group, checkedIds ->
            val checkedId = checkedIds.firstOrNull()
            val categorySelected = when(checkedId) {
                binding.chipAll.id -> "All"
                binding.chipHealth.id -> Category.HEALTH
                binding.chipStudy.id -> Category.STUDY
                binding.chipWork.id -> Category.WORK
                binding.chipPersonal.id -> Category.PERSONAL
                else -> "All"
            }
            val filteredByCategory = if(categorySelected == "All") {
                viewModel.habits.value
            } else {
                viewModel.habits.value.filter {
                    it.habit.category == categorySelected
                }
            }
            adapter.setHabits(filteredByCategory)
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
                val action = HomeFragmentDirections.actionHomeToHabitDetails(it.id!!, selectedDate)
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
            binding.cgCategory.check(binding.chipAll.id)
        }
        binding.rvCalendarView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)
        binding.rvCalendarView.adapter = calendarAdapter
        val todayIndex = 1000
        binding.rvCalendarView.scrollToPosition(todayIndex)
        binding.tvHomeDate.setOnClickListener {
            binding.rvCalendarView.smoothScrollToPosition(todayIndex)
        }
    }
}