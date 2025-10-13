package com.example.habittrackerapp.ui.manageHabit

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.habittrackerapp.R
import com.example.habittrackerapp.data.model.Category
import com.example.habittrackerapp.data.model.Frequency
import com.example.habittrackerapp.data.model.Habit
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Calendar
import java.util.Date

class EditHabitFragment() : BaseManageFragment() {

    override val viewModel: EditHabitViewModel by viewModels()
    private val args: EditHabitFragmentArgs by navArgs()
    private var startDate: Long = Calendar.getInstance().timeInMillis
    private var endDate: Long? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHabitById(args.habitId)
        lifecycleScope.launch {
            viewModel.count.collect {
                binding.tvCount.text = it.toString()
            }
        }
        observeStartDateResult()
        observeEndDateResult()
        showErrorMessage()
        submit()
        observeHabitDetails()
        setupEndDateSwitch()
        navigateToStartDateDialog()
        navigateToEndDateDialog()
        lifecycleScope.launch {
            viewModel.finish.collect {
                findNavController().popBackStack()
            }
        }
    }
    fun observeHabitDetails() {
        lifecycleScope.launch {
            viewModel.habit.collect {
                binding.run {
                    tvPlaceholderTitle.text = getString(R.string.edit_habit)
                    mbSubmit.text = getString(R.string.update)
                    etName.setText(it?.name)
                    setupFrequencySelection(it)
                    setupCategorySelection(it)
                    setupDatePickers(it)
                }
            }
        }
    }
    fun setupDatePickers(habit: Habit?) {
        binding.run {
            val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
                .format(Date(habit?.startDate?:System.currentTimeMillis()))
            mbStartDatePicker.text = dateFormat
            startDate = habit?.startDate!!
            val endDateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
            endDate = habit.endDate
            if(endDate != null) {
                materialSwitch.isChecked = true
                binding.mbEndDatePicker.visibility = View.VISIBLE
                binding.mbEndDatePicker.text = endDateFormat.format(Date(endDate!!))
            } else {
                materialSwitch.isChecked = false
            }
        }
    }
    fun setupCategorySelection(habit: Habit?) {
        binding.run {
            val category = when(habit?.category) {
                Category.STUDY -> rbStudy.id
                Category.PERSONAL -> rbPersonal.id
                Category.HEALTH -> rbHealth.id
                Category.WORK -> rbWork.id
                else -> rbPersonal.id
            }
            rgCategory.check(category)
        }
    }
    fun setupFrequencySelection(habit: Habit?) {
        binding.run {
            val frequency = when(habit?.frequency) {
                Frequency.DAILY -> rbDaily.id
                Frequency.WEEKLY -> rbWeekly.id
                Frequency.MONTHLY -> rbMonthly.id
                Frequency.YEARLY -> rbYearly.id
                else -> rbDaily.id
            }
            rgFrequency.check(frequency)
        }
    }
    fun setupEndDateSwitch() {
        binding.materialSwitch.setOnCheckedChangeListener { materialSwitch, isCheck ->
            if(materialSwitch.isChecked) {
                if (endDate == null) endDate = Calendar.getInstance().timeInMillis
                val endDateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
                    .format(Date(endDate!!))
                binding.mbEndDatePicker.text = endDateFormat.toString()
                binding.mbEndDatePicker.visibility = View.VISIBLE
            } else {
                endDate = null
                binding.mbEndDatePicker.visibility = View.INVISIBLE
            }
        }
    }
    fun navigateToStartDateDialog() {
        binding.mbStartDatePicker.setOnClickListener {
            val action = EditHabitFragmentDirections.actionEditHabitToStartDateDialog(startDate)
            findNavController().navigate(action)
        }
    }
    fun navigateToEndDateDialog() {
        binding.mbEndDatePicker.setOnClickListener {
            val action = EditHabitFragmentDirections.actionEditHabitToEndDateDialog(endDate!!)
            findNavController().navigate(action)
        }
    }
    fun observeStartDateResult() {
        findNavController().currentBackStackEntry?.savedStateHandle?.
        getLiveData<Long>(getString(R.string.startDate))?.observe(
            viewLifecycleOwner
        ) { result ->
            startDate = result
            val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
                .format(Date(result))
            binding.mbStartDatePicker.text = dateFormat.toString()
        }
    }
    fun observeEndDateResult() {
        findNavController().currentBackStackEntry?.savedStateHandle?.
        getLiveData<Long>(getString(R.string.endDate))?.observe(
            viewLifecycleOwner
        ) { result ->
            endDate = result
            val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
                .format(Date(result))
            binding.mbEndDatePicker.text = dateFormat.toString()
        }
    }
    override fun submit() {
        binding.run {
            mbSubmit.setOnClickListener {
                val name = etName.text.toString()
                val frequency = when(rgFrequency.checkedRadioButtonId) {
                    rbDaily.id -> Frequency.DAILY
                    rbWeekly.id -> Frequency.WEEKLY
                    rbMonthly.id -> Frequency.MONTHLY
                    rbYearly.id -> Frequency.YEARLY
                    else -> Frequency.DAILY
                }
                val category = when(rgCategory.checkedRadioButtonId) {
                    rbPersonal.id -> Category.PERSONAL
                    rbStudy.id -> Category.STUDY
                    rbWork.id -> Category.WORK
                    rbHealth.id -> Category.HEALTH
                    else -> Category.PERSONAL
                }
                val count = tvCount.text.toString().toInt()
                viewModel.updateHabit(args.habitId, name, frequency, category, count, startDate, endDate)
            }
        }
    }
}