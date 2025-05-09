package com.example.recipebook;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.recipebook.model.Recipe;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    private TextView textViewShoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        textViewShoppingList = findViewById(R.id.textViewShoppingList);

        // Get the ingredients from selected recipes
        List<String> shoppingList = getShoppingListFromSelectedRecipes();
        String shoppingListText = String.join("\n", shoppingList);
        textViewShoppingList.setText(shoppingListText);
    }

    private List<String> getShoppingListFromSelectedRecipes() {
        // Example static data for shopping list
        List<String> shoppingList = new ArrayList<>();
        shoppingList.add("Spaghetti");
        shoppingList.add("Eggs");
        shoppingList.add("Bacon");
        return shoppingList;
    }
}

