package com.example.recipebook;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recipebook.adapter.MealPlanAdapter;
import com.example.recipebook.model.MealPlan;
import com.example.recipebook.model.Recipe;
import java.util.ArrayList;
import java.util.List;

public class MealPlannerActivity extends AppCompatActivity {

    private Spinner spinnerDay;
    private RecyclerView recyclerView;
    private MealPlanAdapter mealPlanAdapter;
    private List<Recipe> selectedRecipes;
    private List<MealPlan> mealPlans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_planner);

        spinnerDay = findViewById(R.id.spinnerDay);
        recyclerView = findViewById(R.id.recyclerViewMealPlan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample data
        selectedRecipes = new ArrayList<>();
        mealPlans = new ArrayList<>();

        // Setup Spinner with days of the week
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapter);

        mealPlanAdapter = new MealPlanAdapter();
        recyclerView.setAdapter(mealPlanAdapter);

        // Add a recipe to the day
        findViewById(R.id.btnAddRecipeToDay).setOnClickListener(v -> {
            String selectedDay = spinnerDay.getSelectedItem().toString();
            MealPlan mealPlan = new MealPlan(selectedDay, selectedRecipes);
            mealPlans.add(mealPlan);
            mealPlanAdapter.setMealPlans(mealPlans);
            Toast.makeText(this, "Meal Plan added for " + selectedDay, Toast.LENGTH_SHORT).show();
        });
    }
}

