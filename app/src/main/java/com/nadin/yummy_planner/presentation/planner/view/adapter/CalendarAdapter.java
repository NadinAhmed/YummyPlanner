package com.nadin.yummy_planner.presentation.planner.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.nadin.yummy_planner.R;
import com.nadin.yummy_planner.presentation.planner.model.Day;
import com.nadin.yummy_planner.presentation.planner.view.OnClickListener;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private List<Day> days;
    private OnClickListener onClickListener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public CalendarAdapter(List<Day> days, OnClickListener onClickListener) {
        this.days = days;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Day day = days.get(position);
        holder.dayOfWeek.setText(day.getDayOfWeek());
        holder.dayOfMonth.setText(day.getDayOfMonth());

        if (position == selectedPosition) {
            ((MaterialCardView) holder.itemView)
                    .setCardBackgroundColor(
                            ContextCompat.getColor(holder.itemView.getContext(),
                                    R.color.primaryColor)
                    );
        } else {
            ((MaterialCardView) holder.itemView)
                    .setCardBackgroundColor(
                            ContextCompat.getColor(holder.itemView.getContext(),
                                    R.color.cardBackgroundColor)
                    );
        }

        holder.itemView.setOnClickListener(v -> {
            int previous = selectedPosition;
            selectedPosition = holder.getAdapterPosition();

            notifyItemChanged(previous);
            notifyItemChanged(selectedPosition);
            onClickListener.onDayClick(day.getDate());
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
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
