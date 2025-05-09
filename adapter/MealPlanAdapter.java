package com.example.recipebook.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recipebook.R;
import com.example.recipebook.model.MealPlan;
import java.util.List;

public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanAdapter.MealPlanViewHolder> {

    private List<MealPlan> mealPlans;

    public void setMealPlans(List<MealPlan> mealPlans) {
        this.mealPlans = mealPlans;
        notifyDataSetChanged();
    }

    @Override
    public MealPlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_plan, parent, false);
        return new MealPlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MealPlanViewHolder holder, int position) {
        MealPlan mealPlan = mealPlans.get(position);
        holder.textViewDay.setText(mealPlan.getDay());
        holder.textViewRecipes.setText(mealPlan.getRecipes().toString());
    }

    @Override
    public int getItemCount() {
        return mealPlans == null ? 0 : mealPlans.size();
    }

    public static class MealPlanViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDay, textViewRecipes;

        public MealPlanViewHolder(View itemView) {
            super(itemView);
            textViewDay = itemView.findViewById(R.id.textViewDay);
            textViewRecipes = itemView.findViewById(R.id.textViewRecipes);
        }
    }
}


