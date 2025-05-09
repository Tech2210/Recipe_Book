package com.example.recipebook.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.recipebook.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert
    void insert(Recipe recipe);

    @Query("SELECT * FROM recipes")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM recipes WHERE title LIKE :searchQuery")
    LiveData<List<Recipe>> searchRecipes(String searchQuery);

    @Query("UPDATE recipes SET isFavorite = :isFav WHERE id = :id")
    void updateFavorite(int id, boolean isFav);

    @Query("SELECT * FROM recipes WHERE isFavorite = 1")
    LiveData<List<Recipe>> getFavoriteRecipes();

    @Query("SELECT * FROM recipes WHERE category = :category")
    LiveData<List<Recipe>> getRecipesByCategory(String category);
}
