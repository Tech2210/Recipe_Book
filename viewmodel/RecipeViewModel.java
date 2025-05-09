package com.example.recipebook.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recipebook.data.RecipeDao;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.respository.RecipeRepository;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private RecipeRepository repository;
    private LiveData<List<Recipe>> allRecipes;
    private RecipeRepository recipeRepository;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        repository = new RecipeRepository(application);
        allRecipes = repository.getAllRecipes();
    }

    // Insert a new recipe
    public void insert(Recipe recipe) {
        repository.insert(recipe);
    }

    // Get all recipes
    public LiveData<List<Recipe>> getAllRecipes() {
        return allRecipes;
    }

    // Search recipes by title
    public LiveData<List<Recipe>> searchRecipes(String query) {
        return repository.searchRecipes(query);
    }

    // Update the 'isFavorite' status of a recipe
    public void updateFavorite(int id, boolean isFav) {
        repository.updateFavorite(id, isFav);
    }

    // Get all favorite recipes
    public LiveData<List<Recipe>> getFavoriteRecipes() {
        return repository.getFavoriteRecipes();
    }

    public LiveData<List<Recipe>> filterByCategory(String category) {
        return recipeRepository.getRecipesByCategory(category);
    }

}
