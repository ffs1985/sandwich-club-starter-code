package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView alsoKnownAsTv;
    private TextView ingredientsTv;
    private TextView placeOfOriginTv;
    private TextView descriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAsTv = (TextView) findViewById(R.id.also_known_tv);
        ingredientsTv = (TextView) findViewById(R.id.ingredients_tv);
        placeOfOriginTv = (TextView) findViewById(R.id.origin_tv);
        descriptionTv = (TextView) findViewById(R.id.description_tv);

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

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        alsoKnownAsTv.setText(printList(sandwich.getAlsoKnownAs()));
        descriptionTv.setText(sandwich.getDescription());
        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        ingredientsTv.setText(printList(sandwich.getIngredients()));
    }

    private String printList(List<String> list) {
        if(list.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String value: list) {
            builder.append(value + ", ");
        }
        return builder.substring(0, builder.length()-2);
    }
}
