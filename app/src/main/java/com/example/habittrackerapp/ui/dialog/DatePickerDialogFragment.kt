package com.example.habittrackerapp.ui.dialog

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.habittrackerapp.R
import com.example.habittrackerapp.databinding.FragmentDatePickerDialogBinding
import java.util.Calendar

class DatePickerDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentDatePickerDialogBinding
    private val viewModel: DatePickerDialogViewModel by viewModels()
    private val args: DatePickerDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDatePickerDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mbCancel.setOnClickListener {
            dismiss()
        }
        binding.run {
            mbApply.setOnClickListener {
                val selectedDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, datePicker.year)
                    set(Calendar.MONTH, datePicker.month)
                    set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)
                }
                val startDate = selectedDate.timeInMillis
                findNavController().previousBackStackEntry?.savedStateHandle?.set("startDate", startDate)
                dismiss()
            }
        }
        val cal = Calendar.getInstance().apply {
            timeInMillis = args.startDate
        }
        binding.datePicker.updateDate(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
    }
}