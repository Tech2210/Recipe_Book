package com.example.recipebook;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.adapter.RecipeAdapter;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.viewmodel.RecipeViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecipeViewModel recipeViewModel;
    private RecipeAdapter recipeAdapter;
    private EditText editTextSearch;
    private Spinner spinnerCategory;
    private Button btnAddRecipe, btnSubmenu;
    private ProgressBar progressBar;
    private List<Recipe> allRecipes;

    private final String[] categories = {"All", "Dessert", "Breakfast", "Lunch", "Dinner"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI References
        RecyclerView recyclerView = findViewById(R.id.recyclerViewRecipes);
        editTextSearch = findViewById(R.id.editTextSearch);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnAddRecipe = findViewById(R.id.btnAddRecipe);
        btnSubmenu = findViewById(R.id.btnSubmenu);
        progressBar = findViewById(R.id.progressBar);

        // RecyclerView setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(this, new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe recipe) {
                Intent intent = new Intent(MainActivity.this, RecipeDetailActivity.class);
                intent.putExtra("recipe", recipe);
                startActivity(intent);
            }

            @Override
            public void onFavoriteToggle(int recipeId, boolean isFav) {
                recipeViewModel.updateFavorite(recipeId, isFav);
            }
        });
        recyclerView.setAdapter(recipeAdapter);

        // ViewModel setup
        recipeViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(RecipeViewModel.class);
        loadRecipes();

        // Spinner setup
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                filterByCategory(selectedCategory);
                handleCategorySelection(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Search filtering
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recipeViewModel.searchRecipes(s.toString()).observe((LifecycleOwner) MainActivity.this, recipes -> {
                    recipeAdapter.setRecipes(recipes);
                });
            }
        });

        // Add recipe button
        btnAddRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
            startActivityForResult(intent, 100);
        });

        // Optional: Insert a sample recipe for testing
        insertSampleData();
    }

    private void loadRecipes() {
        progressBar.setVisibility(View.VISIBLE);
        recipeViewModel.getAllRecipes().observe(this, recipes -> {
            progressBar.setVisibility(View.GONE);
            allRecipes = recipes;
            recipeAdapter.setRecipes(allRecipes);
        });
    }

    private void filterByCategory(String category) {
        if (category.equals("All")) {
            recipeViewModel.getAllRecipes().observe(this, recipes -> {
                recipeAdapter.setRecipes(recipes);
            });
        } else {
            recipeViewModel.filterByCategory(category).observe(this, recipes -> {
                recipeAdapter.setRecipes(recipes);
            });
        }
    }

    private void filterRecipes(String query) {
        recipeViewModel.searchRecipes(query).observe(this, recipes -> {
            recipeAdapter.setRecipes(recipes);
        });
    }

    private void handleCategorySelection(String category) {
        if (category.equals("Dessert") || category.equals("Breakfast") || category.equals("Lunch") || category.equals("Dinner")) {
            btnSubmenu.setVisibility(View.VISIBLE);
            btnSubmenu.setOnClickListener(v -> showSubmenu(category));
        } else {
            btnSubmenu.setVisibility(View.GONE);
            Toast.makeText(this, "Selected: " + category, Toast.LENGTH_SHORT).show();
        }
    }

    private void showSubmenu(String category) {
        String[] options;

        switch (category) {
            case "Dessert":
                options = new String[]{"Cake", "Ice Cream", "Pie", "Cookies"};
                break;
            case "Breakfast":
                options = new String[]{"Pancakes", "Omelette", "Toast", "Smoothie"};
                break;
            case "Lunch":
                options = new String[]{"Grilled Sandwich", "Salad", "Biryani", "Burger"};
                break;
            case "Dinner":
                options = new String[]{"Pasta", "Dal Rice", "Noodles", "Paneer Curry"};
                break;
            default:
                options = new String[]{"No subitems"};
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a " + category + " item");
        builder.setItems(options, (dialog, which) -> {
            String selected = options[which];
            Toast.makeText(this, "You selected: " + selected, Toast.LENGTH_SHORT).show();
            // Optional: Add filtering logic for subcategories here
        });
        builder.show();
    }

    private void insertSampleData() {
        Recipe recipe = new Recipe();
        recipe.setTitle("Spaghetti Carbonara");
        recipe.setDescription("Classic Italian pasta with eggs, cheese, and bacon.");
        recipe.setImageUrl("https://example.com/spaghetti.jpg");
        recipe.setIngredients("Spaghetti,Eggs,Cheese,Bacon,Black Pepper");
        recipe.setInstructions("Boil pasta. Cook bacon. Mix with eggs and cheese. Combine.");
        recipe.setCategory("Dinner");
        recipeViewModel.insert(recipe);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Recipe newRecipe = (Recipe) data.getSerializableExtra("new_recipe");
            if (newRecipe != null) {
                recipeViewModel.insert(newRecipe);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                filterRecipes(query);
                return true;
            }

            @Override public boolean onQueryTextChange(String newText) {
                filterRecipes(newText);
                return true;
            }
        });

        return true;
    }
}
