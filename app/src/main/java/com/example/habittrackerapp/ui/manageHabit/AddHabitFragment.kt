package com.example.habittrackerapp.ui.manageHabit

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.habittrackerapp.data.model.Category
import com.example.habittrackerapp.data.model.Frequency
import com.example.habittrackerapp.R
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Calendar
import java.util.Date

class AddHabitFragment : BaseManageFragment() {
    override val viewModel: AddHabitViewModel by viewModels {
        AddHabitViewModel.Factory
    }
    private var startDate: Long = Calendar.getInstance().timeInMillis
    private var endDate: Long? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.finish.collect {
                findNavController().popBackStack()
            }
        }
        habitRepeatCounter()
        showErrorMessage()
        submit()
//        UPDATE THE START DATE BUTTON TO THE SELECTED DATE
        setupStartDateSelection()
        observeStartDateResult()
//        UPDATE THE END DATE BUTTON TO THE SELECTED DATE
        setupEndDateSelection()
        observeEndDateResult()
    }
    fun setupStartDateSelection() {
        val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
            .format(Date(startDate))
        binding.mbStartDatePicker.text = dateFormat.toString()
        binding.mbStartDatePicker.setOnClickListener {
            val action = AddHabitFragmentDirections.actionAddHabitToStartDateDialog(startDate)
            findNavController().navigate(action)
        }
    }
    fun setupEndDateSelection() {
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
        binding.mbEndDatePicker.setOnClickListener {
            val action = AddHabitFragmentDirections.actionAddHabitToEndDateDialog(endDate!!)
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
                viewModel.addHabit(name, frequency, category, count, startDate, endDate)
            }
        }
    }
}