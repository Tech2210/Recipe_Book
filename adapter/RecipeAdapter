package com.example.recipebook.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipebook.R;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.RecipeDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList = new ArrayList<>();
    private List<Recipe> allRecipes = new ArrayList<>();
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(Recipe recipe);
        void onFavoriteToggle(int recipeId, boolean isFavorite);
    }

    private OnItemClickListener listener;

    public RecipeAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipeList = recipes;
        this.allRecipes = new ArrayList<>(recipes); // Store full list for filtering
        notifyDataSetChanged();
    }

    public void filterRecipes(String query) {
        List<Recipe> filtered = new ArrayList<>();
        for (Recipe r : allRecipes) {
            if (r.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(r);
            }
        }
        recipeList = filtered;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.textViewTitle.setText(recipe.getTitle());
        holder.textViewDescription.setText(recipe.getDescription());

        Glide.with(context)
                .load(recipe.getImageUrl())
                .placeholder(R.drawable.ic_placeholder)
                .into(holder.imageViewRecipe);

        if (recipe.isFavorite()) {
            holder.imageViewFavorite.setImageResource(R.drawable.ic_recipe_image);
        } else {
            holder.imageViewFavorite.setImageResource(R.drawable.ic_favorite_border);
        }

        holder.imageViewFavorite.setOnClickListener(v -> {
            boolean newStatus = !recipe.isFavorite();
            recipe.setFavorite(newStatus);
            notifyItemChanged(position);
            if (listener != null) {
                listener.onFavoriteToggle(recipe.getId(), newStatus);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(recipe);
            }
            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra("recipe", (Parcelable) recipe);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewRecipe;
        ImageView imageViewFavorite;
        TextView textViewTitle, textViewDescription;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewRecipe = itemView.findViewById(R.id.imageViewRecipe);
            imageViewFavorite = itemView.findViewById(R.id.imageViewFavorite);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }
    }
}
