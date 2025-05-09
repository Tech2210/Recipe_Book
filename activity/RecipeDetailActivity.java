package com.example.recipebook;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.recipebook.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView titleView, descriptionView, ingredientsView, instructionsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        imageView = findViewById(R.id.imageViewDetail);
        titleView = findViewById(R.id.textViewTitleDetail);
        descriptionView = findViewById(R.id.textViewDescriptionDetail);
        ingredientsView = findViewById(R.id.textViewIngredientsDetail);
        instructionsView = findViewById(R.id.textViewInstructionsDetail);

        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        if (recipe != null) {
            titleView.setText(recipe.getTitle());
            descriptionView.setText(recipe.getDescription());
            ingredientsView.setText(recipe.getIngredients());
            instructionsView.setText(recipe.getInstructions());

            Glide.with(this)
                    .load(recipe.getImageUrl())
                    .placeholder(R.drawable.ic_placeholder)
                    .into(imageView);
        }
    }
}
