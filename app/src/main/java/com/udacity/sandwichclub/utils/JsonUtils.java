package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            // Get the root object
            JSONObject rootObject = new JSONObject(json);

            // Get the mainName & alsoKnownAs from the nameObject
            JSONObject nameObject = rootObject.getJSONObject("name");
            String mainName = nameObject.getString("mainName");
            List<String> alsoKnownAs = new ArrayList<>();
            JSONArray alsoKnownArray = nameObject.getJSONArray("alsoKnownAs");
            for (int i = 0; i < alsoKnownArray.length(); i++) {
                alsoKnownAs.add(alsoKnownArray.getString(i));
            }

            // Get place of origin & description & image url
            // Use optString() if string isn't provided in JSON
            String placeOfOrigin = rootObject.optString("placeOfOrigin", "sandwich place of origin");
            String description = rootObject.optString("description", "sandwich description");
            String image = rootObject.optString("image", "sandwich image");

            // Get the ingredients array
            List<String> ingredients = new ArrayList<>();
            JSONArray ingredientsArray = rootObject.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.getString(i));
            }

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
