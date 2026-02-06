package com.nadin.yummy_planner.presentation.meal_details.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nadin.yummy_planner.R;

import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {

    private List<String> steps = new ArrayList<>();

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_step, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(steps.get(position), position);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void setSteps(List<String> steps) {
        this.steps.clear();
        this.steps.addAll(steps);
        notifyDataSetChanged();
    }

    static class StepViewHolder extends RecyclerView.ViewHolder {
        TextView stepText;
        TextView stepNumber;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            stepText = itemView.findViewById(R.id.step_text);
            stepNumber = itemView.findViewById(R.id.step_number);
        }

        void bind(String step, int position) {
            stepNumber.setText(String.valueOf(position + 1));
            stepText.setText(step);
        }
    }
}
