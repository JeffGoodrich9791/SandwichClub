package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

    ImageView ingredientsIv;
    TextView knownAs;
    TextView originTv;
    TextView descriptionTv;
    TextView indgredientsTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        knownAs = findViewById(R.id.also_known_tv);
        originTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        indgredientsTv = findViewById(R.id.ingredients_tv);

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

        if (sandwich.getPlaceOfOrigin() != null){

            originTv.setText(R.string.detail_error_message);
        }else {
            originTv.setText(sandwich.getPlaceOfOrigin());
        }

        if(sandwich.getAlsoKnownAs() == null){
            knownAs.setText(R.string.detail_error_message);

            originTv.setText(R.string.detail_error_message);
        }else {
            originTv.setText(sandwich.getPlaceOfOrigin());
        }
        if (sandwich.getAlsoKnownAs() != null){
            knownAs.setText(R.string.detail_error_message);

        }else {
            knownAs.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        }


        descriptionTv.setText(sandwich.getDescription());
        indgredientsTv.setText(listModel(sandwich.getIngredients()));
        originTv.setText(sandwich.getPlaceOfOrigin());


    }

    public StringBuilder listModel(List<String> list){
        StringBuilder stringBuilder= new StringBuilder();
        for (int i=0;i<list.size();i++){
            stringBuilder.append(list.get(i)).append("\n");
        }
        return stringBuilder;
    }
}
