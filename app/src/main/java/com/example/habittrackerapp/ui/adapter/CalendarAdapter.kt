package com.example.habittrackerapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.data.model.Calendar
import com.example.habittrackerapp.databinding.ItemLayoutCalendarBinding

class CalendarAdapter(
    private var views: List<Calendar>
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
        val view = views[position]
        holder.bind(view)
    }

    override fun getItemCount() = views.size

    inner class CalendarViewHolder(
        private val binding: ItemLayoutCalendarBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(calendarView: Calendar) {
            binding.run {
                tvDay.text = calendarView.date.toString()
            }
        }
    }
}