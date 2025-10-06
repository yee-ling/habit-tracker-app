package com.example.habittrackerapp.ui.manageHabit

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.habittrackerapp.R
import com.example.habittrackerapp.data.model.Frequency
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
        habitRepeatCounter()
        observeStartDateResult()
        observeEndDateResult()
        submit()
        lifecycleScope.launch {
            viewModel.habit.collect {
                binding.run {
                    mbSubmit.text = getString(R.string.update)
                    etName.setText(it?.name)
                    val habitFrequency = it?.frequency
                    val frequency = when(habitFrequency) {
                        Frequency.DAILY -> rbDaily.id
                        Frequency.WEEKLY -> rbWeekly.id
                        Frequency.MONTHLY -> rbMonthly.id
                        Frequency.YEARLY -> rbYearly.id
                        else -> rbDaily.id
                    }
                    rgFrequency.check(frequency)

                    val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
                        .format(Date(it?.startDate?:System.currentTimeMillis()))
                    mbStartDatePicker.text = dateFormat
                    startDate = it?.startDate!!

                    val endDateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)

                    endDate = it.endDate
                    if(endDate != null) {
                        materialSwitch.isChecked = true
                        binding.mbEndDatePicker.visibility = View.VISIBLE
                        binding.mbEndDatePicker.text = endDateFormat.format(Date(endDate!!))
                    } else {
                        materialSwitch.isChecked = false
                    }
                }
                //        UPDATE THE END DATE BUTTON TO THE SELECTED DATE
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
        }

        //navigate to startDate Dialog
        binding.mbStartDatePicker.setOnClickListener {
            val action = EditHabitFragmentDirections.actionEditHabitToStartDateDialog(startDate)
            findNavController().navigate(action)
        }

        //navigate to endDate Dialog
        binding.mbEndDatePicker.setOnClickListener {
            val action = EditHabitFragmentDirections.actionEditHabitToEndDateDialog(endDate!!)
            findNavController().navigate(action)
        }

        lifecycleScope.launch {
            viewModel.finish.collect {
                findNavController().popBackStack()
            }
        }
    }
    fun observeStartDateResult() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>("startDate")?.observe(
            viewLifecycleOwner
        ) { result ->
            startDate = result
            val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
                .format(Date(result))
            binding.mbStartDatePicker.text = dateFormat.toString()
        }
    }
    fun observeEndDateResult() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>("endDate")?.observe(
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
                val count = tvCount.text.toString().toInt()
                viewModel.updateHabit(
                    id = args.habitId,
                    name = name,
                    frequency = frequency,
                    count = count,
                    startDate = startDate,
                    endDate = endDate
                )
            }
        }
    }
}