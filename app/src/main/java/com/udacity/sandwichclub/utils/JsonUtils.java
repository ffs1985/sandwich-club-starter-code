package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
            JSONObject name = (JSONObject)jsonObject.get("name");
            sandwich = new Sandwich();
            sandwich.setMainName((String)name.get("mainName"));
            JSONArray alsoKnownAs = (JSONArray)name.get("alsoKnownAs");
            List<String> alsoKnowAsList = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                alsoKnowAsList.add((String)alsoKnownAs.get(i));
            }
            sandwich.setAlsoKnownAs(alsoKnowAsList);
            sandwich.setPlaceOfOrigin((String)jsonObject.get("placeOfOrigin"));
            sandwich.setDescription((String)jsonObject.get("description"));
            sandwich.setImage((String)jsonObject.get("image"));
            JSONArray ingredients = (JSONArray)jsonObject.get("ingredients");
            List<String> ingredientList = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientList.add((String)ingredients.get(i));
            }
            sandwich.setIngredients(ingredientList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
