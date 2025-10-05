//package com.example.habittrackerapp.ui.manageHabit
//
//import androidx.fragment.app.viewModels
//import android.os.Bundle
//import android.view.View
//import androidx.lifecycle.lifecycleScope
//import androidx.navigation.fragment.findNavController
//import androidx.navigation.fragment.navArgs
//import com.example.habittrackerapp.R
//import com.example.habittrackerapp.data.model.Frequency
//import kotlinx.coroutines.launch
//import java.util.Calendar
//
//class EditHabitFragment() : BaseManageFragment() {
//
//    override val viewModel: EditHabitViewModel by viewModels()
//    private val args: EditHabitFragmentArgs by navArgs()
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewModel.getHabitById(args.habitId)
//        habitRepeatCounter()
//        lifecycleScope.launch {
//            viewModel.habit.collect {
//                binding.run {
//                    mbSubmit.text = getString(R.string.update)
//                    etName.setText(it?.name)
//                    val habitFrequency = it?.frequency
//                    val frequency = when(habitFrequency) {
//                        Frequency.DAILY -> rbDaily.id
//                        Frequency.WEEKLY -> rbWeekly.id
//                        Frequency.MONTHLY -> rbMonthly.id
//                        Frequency.YEARLY -> rbYearly.id
//                        else -> rbDaily.id
//                    }
//                    rgFrequency.check(frequency)
//                    val cal = Calendar.getInstance().apply { timeInMillis = it?.startDate ?: System.currentTimeMillis() }
//                    datePicker.updateDate(
//                        cal.get(Calendar.YEAR),
//                        cal.get(Calendar.MONTH),
//                        cal.get(Calendar.DAY_OF_MONTH)
//                    )
//                }
//            }
//        }
//        binding.run {
//            mbSubmit.setOnClickListener {
//                val name = etName.text.toString()
//                val frequency = when(rgFrequency.checkedRadioButtonId) {
//                    rbDaily.id -> Frequency.DAILY
//                    rbWeekly.id -> Frequency.WEEKLY
//                    rbMonthly.id -> Frequency.MONTHLY
//                    rbYearly.id -> Frequency.YEARLY
//                    else -> Frequency.DAILY
//                }
//                val count = tvCount.text.toString().toInt()
//                val selectedDate = Calendar.getInstance().apply {
//                    set(Calendar.YEAR, datePicker.year)
//                    set(Calendar.MONTH, datePicker.month)
//                    set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)
//                    set(Calendar.HOUR_OF_DAY, 0)
//                    set(Calendar.MINUTE, 0)
//                    set(Calendar.SECOND, 0)
//                    set(Calendar.MILLISECOND, 0)
//                }
//                val startDate = selectedDate.timeInMillis
//                viewModel.updateHabit(
//                    id = args.habitId,
//                    name = name,
//                    frequency = frequency,
//                    count = count,
//                    startDate = startDate,
//                )
//            }
//        }
//        lifecycleScope.launch {
//            viewModel.finish.collect {
//                findNavController().popBackStack()
//            }
//        }
//    }
//}