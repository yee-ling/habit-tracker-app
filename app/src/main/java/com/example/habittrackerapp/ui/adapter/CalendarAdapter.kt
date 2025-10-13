package com.example.habittrackerapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.R
import com.example.habittrackerapp.databinding.ItemLayoutCalendarBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarAdapter(
    private var dates: List<Long>,
    private val onClick: (Long) -> Unit
): RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    private var selectedDate: Long? = null

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
            val dayOfWeek = SimpleDateFormat("EEE", Locale.getDefault()).format(Date(date))
            val day = SimpleDateFormat("dd", Locale.getDefault()).format(Date(date))
            val month = SimpleDateFormat("MMM", Locale.getDefault()).format(Date(date))
            binding.tvDayOfWeek.text = dayOfWeek
            binding.tvDay.text = day
            binding.tvMonth.text = month
            val today = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            setCalendarCardColor(today = today, date = date)
            binding.llCalendar.setOnClickListener {
                selectedDate = date
                onClick(date)
                notifyDataSetChanged()
            }
        }
        fun setCalendarCardColor(today: Long, date: Long) {
            when (date) {
                today -> {
                    binding.cvCalendar.setCardBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.orange_500)
                    )
                }
                selectedDate -> {
                    binding.cvCalendar.setCardBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.orange_200)
                    )
                }
                else -> {
                    binding.cvCalendar.setCardBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.white)
                    )
                }
            }
        }
    }
}