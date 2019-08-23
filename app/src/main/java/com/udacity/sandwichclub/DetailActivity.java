package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView ingredientsIv;
    private TextView alsoKnownTv;
    private TextView originTv;
    private TextView ingredientsTv;
    private TextView descriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownTv = findViewById(R.id.also_known_tv);
        originTv = findViewById(R.id.origin_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        descriptionTv = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        // Set the data to the details layout
        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        setTitle(sandwich.getMainName());
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .into(ingredientsIv);

        Log.d("TEST", sandwich.getAlsoKnownAs().toString());
        Log.d("TEST", sandwich.getIngredients().toString());
        Log.d("TEST", sandwich.getDescription());
        Log.d("TEST", sandwich.getPlaceOfOrigin());

        // Display a string when the data fetched is empty
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownTv.setText(getString(R.string.string_no_data));
        } else {
            alsoKnownTv.setText(sandwich.getAlsoKnownAs().toString());
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            originTv.setText(getString(R.string.string_no_data));
        } else {
            originTv.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getIngredients().isEmpty()) {
            ingredientsTv.setText(getString(R.string.string_no_data));
        } else {
            ingredientsTv.setText(sandwich.getIngredients().toString());
        }

        if (sandwich.getDescription().isEmpty()) {
            descriptionTv.setText(getString(R.string.string_no_data));
        } else {
            descriptionTv.setText(sandwich.getDescription());
        }

    }
}
