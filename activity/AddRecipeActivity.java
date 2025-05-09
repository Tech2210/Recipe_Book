package com.example.recipebook;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.recipebook.model.Recipe;
import com.example.recipebook.viewmodel.RecipeViewModel;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDesc, editTextImage, editTextIngredients, editTextInstructions;
    private Button buttonSave;
    private RecipeViewModel recipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDesc = findViewById(R.id.editTextDescription);
        editTextImage = findViewById(R.id.editTextImageUrl);
        editTextIngredients = findViewById(R.id.editTextIngredients);
        editTextInstructions = findViewById(R.id.editTextInstructions);
        buttonSave = findViewById(R.id.buttonSave);

        recipeViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(RecipeViewModel.class);

        buttonSave.setOnClickListener(v -> {
            saveRecipe();
        });
    }

    private void saveRecipe() {
        String title = editTextTitle.getText().toString();
        String desc = editTextDesc.getText().toString();
        String image = editTextImage.getText().toString();
        String ingredients = editTextIngredients.getText().toString();
        String instructions = editTextInstructions.getText().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(desc)) {
            Toast.makeText(this, "Title and Description are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setDescription(desc);
        recipe.setImageUrl(image);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);

        recipeViewModel.insert(recipe);
        Toast.makeText(this, "Recipe Added!", Toast.LENGTH_SHORT).show();
        finish(); // close activity
    }
}

