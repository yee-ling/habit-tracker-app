package com.example.habittrackerapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.R
import com.example.habittrackerapp.databinding.ItemLayoutCalendarBinding
import java.sql.RowIdLifetime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarAdapter(
    private var dates: List<Long>,
    private val onClick: (Long) -> Unit
): RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarViewHolder {
        val binding = ItemLayoutCalendarBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CalendarViewHolder,
        position: Int
    ) {
        val date = dates[position]
        holder.bind(date)
    }

    override fun getItemCount() = dates.size

    inner class CalendarViewHolder(
        private val binding: ItemLayoutCalendarBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(date: Long) {
            val day = SimpleDateFormat("dd", Locale.getDefault()).format(Date(date))
            val month = SimpleDateFormat("MMM", Locale.getDefault()).format(Date(date))
            val year = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date(date))
            binding.tvDay.text = day
            binding.tvMonth.text = month
            binding.tvYear.text = year
            val today = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            if(date == today) {
                binding.cvCalendar.setCardBackgroundColor(
                    ContextCompat.getColor(binding.root.context, R.color.green)
                )
            }
        }
    }
}