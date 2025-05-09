package com.example.recipebook.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.recipebook.RecipeDatabase;
import com.example.recipebook.data.RecipeDao;
import com.example.recipebook.model.Recipe;

import java.util.List;

public class RecipeRepository {

    private RecipeDao recipeDao;
    private LiveData<List<Recipe>> allRecipes;

    public RecipeRepository(Application application) {
        RecipeDatabase db = RecipeDatabase.getInstance(application);
        recipeDao = db.recipeDao();
        allRecipes = recipeDao.getAllRecipes();
    }

    // Insert a new recipe into the database
    public void insert(Recipe recipe) {
        new Thread(() -> recipeDao.insert(recipe)).start();
    }

    // Get all recipes
    public LiveData<List<Recipe>> getAllRecipes() {
        return allRecipes;
    }

    // Search recipes by title
    public LiveData<List<Recipe>> searchRecipes(String query) {
        return recipeDao.searchRecipes("%" + query + "%");
    }

    // Update the 'isFavorite' status of a recipe
    public void updateFavorite(int id, boolean isFav) {
        new Thread(() -> recipeDao.updateFavorite(id, isFav)).start();
    }

    // Get all favorite recipes
    public LiveData<List<Recipe>> getFavoriteRecipes() {
        return recipeDao.getFavoriteRecipes();
    }

    // Get recipes by category
    public LiveData<List<Recipe>> getRecipesByCategory(String category) {
        return recipeDao.getRecipesByCategory(category);
    }
}
