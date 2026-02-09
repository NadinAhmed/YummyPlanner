package com.nadin.yummy_planner.presentation.planner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nadin.yummy_planner.R;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private List<String> daysOfWeek;
    private List<String> daysOfMonth;

    public CalendarAdapter(List<String> daysOfWeek, List<String> daysOfMonth) {
        this.daysOfWeek = daysOfWeek;
        this.daysOfMonth = daysOfMonth;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dayOfWeek.setText(daysOfWeek.get(position));
        holder.dayOfMonth.setText(daysOfMonth.get(position));
    }

    @Override
    public int getItemCount() {
        return daysOfWeek.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayOfWeek;
        TextView dayOfMonth;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayOfWeek = itemView.findViewById(R.id.dayOfWeek);
            dayOfMonth = itemView.findViewById(R.id.dayOfMonth);
        }
    }
}