package com.example.habittrackerapp.ui.dialog

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.habittrackerapp.R
import java.util.Calendar

class StartDateDialogFragment : BaseDialogFragment() {
    override val viewModel: StartDateDialogViewModel by viewModels()
    private val args: StartDateDialogFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mbCancel.setOnClickListener {
            dismiss()
        }
        setupStartDatePicker()
        val cal = Calendar.getInstance().apply {
            timeInMillis = args.startDate
        }
        binding.datePicker.updateDate(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
    }
    fun setupStartDatePicker() {
        binding.run {
            mbApply.setOnClickListener {
                val selectedDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, datePicker.year)
                    set(Calendar.MONTH, datePicker.month)
                    set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)
                }
                val startDate = selectedDate.timeInMillis
                findNavController().previousBackStackEntry?.savedStateHandle?.
                set(getString(R.string.startDate), startDate)
                dismiss()
            }
        }
    }
}